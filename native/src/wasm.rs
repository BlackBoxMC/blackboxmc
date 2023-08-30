use std::error::Error;

use once_cell::sync::Lazy;
use parking_lot::RwLock;
use wasmtime::*;

use crate::loader::Loader;

struct WASMRuntime {
    engine: RwLock<Engine>,
    files: RwLock<Vec<Instance>>,
}

impl WASMRuntime {
    pub fn new() -> Self {
        Self {
            engine: RwLock::new(Engine::default()),
            files: RwLock::new(Vec::new()),
        }
    }
    pub fn push_file(&self, contents: impl AsRef<[u8]>) -> Result<usize, Box<dyn Error>> {
        let engine = self.engine.write();
        let mut files = self.files.write();

        let module = Module::new(&engine, contents)?;

        // Create a `Linker` which will be later used to instantiate this module.
        // Host functionality is defined by name within the `Linker`.
        let mut linker = Linker::new(&engine);
        linker.func_wrap(
            "host",
            "host_func",
            |caller: Caller<'_, u32>, param: i32| {
                println!("Got {} from WebAssembly", param);
                println!("my host state is: {}", caller.data());
            },
        )?;

        // All wasm objects operate within the context of a "store". Each
        // `Store` has a type parameter to store host-specific data, which in
        // this case we're using `4` for.
        let mut store = Store::new(&engine, 4);
        let instance = linker.instantiate(&mut store, &module)?;
        files.push(instance);

        Ok(files.len())
    }
}

static WASM_LOADER: Lazy<WASMRuntime> = Lazy::new(|| WASMRuntime::new());

pub struct WASMLoader;

impl Loader for WASMLoader {
    fn execute<'a>(
        env: jni::JNIEnv<'a>,
        libname: String,
        funcname: String,
        address: jni::sys::jint,
        plugin: jni::objects::JObject,
        ev: jni::objects::JObject,
    ) -> jni::objects::JObject<'a> {
        todo!()
    }

    fn library_has_function<'a>(env: jni::JNIEnv<'a>, libname: String, funcname: String) -> bool {
        todo!()
    }

    fn send_event<'a>(
        env: jni::JNIEnv<'a>,
        libname: String,
        funcname: String,
        ev: jni::objects::JObject,
    ) -> bool {
        todo!()
    }

    fn load_plugin<'a>(env: jni::JNIEnv<'a>, file: String) {
        todo!()
    }

    fn enable_plugin<'a>(file: String) {
        todo!()
    }

    fn disable_plugin<'a>(file: String) {
        todo!()
    }

    fn library_names<'a>(env: jni::JNIEnv) -> jni::objects::JString {
        todo!()
    }
}
