/*
    Ahead lies lots of unused code, and code that I intended to replace later.
*/

use std::{
    collections::HashMap,
    error::Error,
    ffi::{c_char, c_void},
    ops::Deref,
};

use crate::{
    loader::Loader, unwrap_option_or_java_error, unwrap_result_or_java_error,
    unwrap_wasm_result_or_java_error, wasm,
};
use jni::{
    objects::{JObject, JString},
    sys::*,
    JNIEnv,
};
use once_cell::sync::Lazy;
use parking_lot::{RwLock, RwLockReadGuard, RwLockWriteGuard};
use wasmtime::*;

struct MyState {
    name: String,
    count: usize,
}

struct SwitchableInstance {
    instance: RwLock<Instance>,
    enabled: RwLock<bool>,
    store: RwLock<Store<MyState>>,
}
impl SwitchableInstance {
    pub fn new(instance: Instance, store: Store<MyState>, enabled: bool) -> Self {
        Self {
            instance: RwLock::new(instance),
            enabled: RwLock::new(enabled),
            store: RwLock::new(store),
        }
    }
    pub fn instance(&self) -> RwLockReadGuard<Instance> {
        self.instance.read()
    }
    pub fn set_enabled(&self, enabled: bool) {
        *self.enabled.write() = enabled;
    }
    pub fn get_func(&self, name: &str) -> Option<Func> {
        let mut st = self.store.write();
        self.instance.read().get_func(st.as_context_mut(), &name)
    }
    pub fn call_func_if_exists<Params, Results>(
        &self,
        mut env: &mut JNIEnv,
        libname: &str,
        funcname: &str,
        args: Params,
    ) -> bool
    where
        Params: WasmParams,
        Results: WasmResults,
    {
        let mut ok = unsafe { env.unsafe_clone() };
        let mut cl = || -> Result<bool> {
            let mut st = self.store.write();
            let lib = self.instance();
            if let Some(_) = lib.get_func(st.as_context_mut(), &funcname) {
                let func_res = lib.get_typed_func::<(i32, i32), ()>(st.as_context_mut(), &funcname);
                let func = unwrap_wasm_result_or_java_error(env, libname, func_res);
                let func = lib.get_typed_func::<(i32, i32), ()>(st.as_context_mut(), &funcname)?;
                func.call(st.as_context_mut(), (-1, -2))?;
                Ok(true)
            } else {
                Ok(false)
            }
        };
        unwrap_wasm_result_or_java_error(&mut ok, libname, cl())
    }
    pub fn store(&self) -> RwLockWriteGuard<Store<MyState>> {
        self.store.write()
    }
}

struct WASMRuntime {
    engine: RwLock<Engine>,
    instances: RwLock<HashMap<String, SwitchableInstance>>,
}

impl WASMRuntime {
    pub fn new() -> Self {
        Self {
            engine: RwLock::new(Engine::default()),
            instances: RwLock::new(HashMap::new()),
        }
    }
    pub fn push_file(&self, name: String, file: String) -> Result<usize, Box<dyn Error>> {
        let engine = self.engine.write();
        let mut instances = self.instances.write();

        let module = Module::from_file(&engine, file).unwrap();

        let mut store = Store::new(
            &engine,
            MyState {
                name: "what".to_string(),
                count: 0,
            },
        );

        // There's some functions that the compiled WASM module expects that we don't have...because this isn't a Javascript context...
        // and they shouldn't be there.
        // These are stub functions, and the plan was to eventually replace them with actual working functions
        let (stub_func_1, stub_func_2, stub_func_3) = (
            Func::wrap(
                &mut store,
                |mut _caller: Caller<'_, MyState>, a: i32, b: i32| -> i32 {
                    println!("stub_func_1, {}, {}", a, b);
                    0
                },
            ),
            Func::wrap(
                &mut store,
                |mut _caller: Caller<'_, MyState>, a: i32, b: i32, c: i32, d: i32| -> i32 {
                    println!("stub_func_2, {}, {}, {}, {}", a, b, c, d);
                    0
                },
            ),
            Func::wrap(
                &mut store,
                |mut _caller: Caller<'_, MyState>, _a: i32| -> () {
                    println!("stub_func_3");
                },
            ),
        );

        // All wasm objects operate within the context of a "store". Each
        // `Store` has a type parameter to store host-specific data, which in
        // this case we're using `4` for.

        let instance = Instance::new(
            &mut store,
            &module,
            &[
                stub_func_1.into(), // wasi_snapshot_preview1::random_get
                stub_func_2.into(), // wasi_snapshot_preview1::fd_write
                stub_func_1.into(), // wasi_snapshot_preview1::proc_exit
                stub_func_1.into(), // wasi_snapshot_preview1::environ_sizes_get
                stub_func_3.into(), // wasi_snapshot_preview1::environ_get
            ],
        )
        .unwrap();
        instances.insert(name, SwitchableInstance::new(instance, store, true));

        Ok(instances.len())
    }
    pub fn instances(&self) -> RwLockReadGuard<HashMap<String, SwitchableInstance>> {
        self.instances.read()
    }
    pub fn set_enabled(&self, file: String, enabled: bool) {
        self.instances().get(&file).unwrap().set_enabled(enabled);
    }
    pub fn instance_names(&self) -> String {
        self.instances()
            .iter()
            .map(|f| f.0.clone())
            .collect::<Vec<String>>()
            .join(",")
    }
}

static WASM_LOADER: Lazy<WASMRuntime> = Lazy::new(|| WASMRuntime::new());

pub struct WASMLoader;

impl Loader for WASMLoader {
    fn execute<'a>(
        mut env: jni::JNIEnv<'a>,
        libname: String,
        funcname: String,
        address: jni::sys::jint,
        plugin: jni::objects::JObject,
        ev: jni::objects::JObject,
    ) -> jni::objects::JObject<'a> {
        let manager = WASM_LOADER.instances();
        println!("{}::{}", libname, funcname);
        let lib = unwrap_option_or_java_error(
            &mut env,
            &libname,
            &"wasm library".to_string(),
            manager.get(&libname),
        );
        lib.call_func_if_exists::<(i32, i32), ()>(&mut env, &libname, &funcname, (0, 1));
        JObject::null()
    }

    fn library_has_function<'a>(
        mut env: jni::JNIEnv<'a>,
        libname: String,
        funcname: String,
    ) -> bool {
        let manager = WASM_LOADER.instances();
        println!("{}::{}", libname, funcname);
        let lib = unwrap_option_or_java_error(
            &mut env,
            &libname,
            &libname.to_string(),
            manager.get(&libname),
        );

        if let Some(_) = lib.get_func(&funcname) {
            true
        } else {
            false
        }
    }

    fn send_event<'a>(
        mut env: jni::JNIEnv<'a>,
        libname: String,
        funcname: String,
        ev: jni::objects::JObject,
    ) -> bool {
        let manager = WASM_LOADER.instances();
        println!("{}::{}", libname, funcname);
        let lib = unwrap_option_or_java_error(
            &mut env,
            &libname,
            &"wasm library".to_string(),
            manager.get(&libname),
        );

        lib.call_func_if_exists::<(i32, i32), ()>(&mut env, &libname, &funcname, (0, 0))
    }

    fn load_plugin<'a>(env: jni::JNIEnv<'a>, file: String) {
        let parts = file
            .split(std::path::MAIN_SEPARATOR)
            .map(|f| f.to_string())
            .collect::<Vec<String>>();
        let name = parts.get(parts.len() - 1).unwrap().clone();
        WASM_LOADER.push_file(name, file).unwrap();
    }

    fn enable_plugin<'a>(file: String) {
        WASM_LOADER.set_enabled(file, true);
    }

    fn disable_plugin<'a>(file: String) {
        WASM_LOADER.set_enabled(file, false);
    }

    fn library_names<'a>(env: JNIEnv) -> JString {
        env.new_string(WASM_LOADER.instance_names()).unwrap()
    }
}
