struct SwitchableLibrary {
    library: Mutex<Library>,
    enabled: Mutex<bool>,
}

impl SwitchableLibrary {
    fn new(library: Library, enabled: bool) -> Self {
        Self {
            library: Mutex::new(library),
            enabled: Mutex::new(enabled),
        }
    }
    fn enabled(&self) -> bool {
        self.enabled.lock().clone()
    }
    fn library(&self) -> MutexGuard<Library> {
        self.library.lock()
    }
    fn set_enabled(&self, enabled: bool) {
        *self.enabled.lock() = enabled;
    }
}

struct LibraryManager {
    loaded_libraries: Mutex<HashMap<String, SwitchableLibrary>>,
}

impl LibraryManager {
    fn new() -> Self {
        Self {
            loaded_libraries: Mutex::new(HashMap::new()),
        }
    }
    fn push_lib(&mut self, libname: String, lib: (Library, bool)) {
        let libs = &mut self.loaded_libraries.lock();
        libs.insert(libname, SwitchableLibrary::new(lib.0, lib.1));
    }
    fn set_enabled(&mut self, file: String, enabled: bool) {
        let libs = &mut self.loaded_libraries.lock();
        libs.get(&file).unwrap().set_enabled(enabled);
    }
}

static mut LIBRARY_MANAGER: Lazy<LibraryManager> = Lazy::new(|| LibraryManager::new());

use jni::{
    objects::{JObject, JString},
    sys::jboolean,
    JNIEnv,
};
use libloading::Library;
use once_cell::sync::Lazy;
use parking_lot::{Mutex, MutexGuard};

use std::{collections::HashMap, error::Error, fmt::Display, fs, path::Path};

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_loadPlugin<'a>(
    mut env: JNIEnv<'a>,
    _obj: JObject,
    filename: JString,
) {
    let file = env
        .get_string(&filename)
        .unwrap()
        .to_string_lossy()
        .to_string();
    unsafe {
        let lib = libloading::Library::new(&file);
        if let Err(e) = &lib {
            throw_libloading_error(&mut env, &file, e);
            return;
        }
        let lib = lib.unwrap();
        LIBRARY_MANAGER.push_lib(file, (lib, true));
    }
}

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_enablePlugin<'a>(
    mut env: JNIEnv<'a>,
    _obj: JObject,
    filename: JString,
) {
    let file = env
        .get_string(&filename)
        .unwrap()
        .to_string_lossy()
        .to_string();
    unsafe {
        let map = LIBRARY_MANAGER
            .loaded_libraries
            .lock()
            .get(&file)
            .unwrap()
            .set_enabled(true);
    }
}

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_disablePlugin<'a>(
    mut env: JNIEnv<'a>,
    _obj: JObject,
    filename: JString,
) {
    let file = env
        .get_string(&filename)
        .unwrap()
        .to_string_lossy()
        .to_string();
    unsafe {
        let map = LIBRARY_MANAGER
            .loaded_libraries
            .lock()
            .get(&file)
            .unwrap()
            .set_enabled(false);
    }
}

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_libraryNames<'a>(
    env: JNIEnv<'a>,
    _obj: JObject,
) -> JString<'a> {
    unsafe {
        env.new_string(
            LIBRARY_MANAGER
                .loaded_libraries
                .lock()
                .iter()
                .map(|f| f.0.clone())
                .collect::<Vec<String>>()
                .join(","),
        )
        .unwrap()
    }
}

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_libraryHasFunction<'a>(
    mut env: JNIEnv<'a>,
    _obj: JObject,
    libname_raw: JString,
    funcname_raw: JString,
) -> jboolean {
    let libname = &env
        .get_string(&libname_raw)
        .unwrap()
        .to_string_lossy()
        .to_string();
    let funcname = &env
        .get_string(&funcname_raw)
        .unwrap()
        .to_string_lossy()
        .to_string();

    let manager = unsafe { LIBRARY_MANAGER.loaded_libraries.lock() };
    let lib = manager.get(libname).unwrap().library();
    let func: Result<
        libloading::Symbol<unsafe extern "C" fn(JNIEnv<'_>, JObject<'_>)>,
        libloading::Error,
    > = unsafe { lib.get(funcname.as_bytes()) };
    if let Err(e) = &func {
        match e {
            libloading::Error::DlSym { desc: d } => {
                return false.into();
            }
            libloading::Error::GetProcAddress { source: s } => {
                return false.into();
            }
            _ => {
                throw_libloading_error(&mut env, &libname, e);
                return false.into();
            }
        }
    }
    return true.into();
}

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_sendEvent<'a>(
    mut env: JNIEnv<'a>,
    _obj: JObject,
    libname_raw: JString,
    funcname_raw: JString,
    ev: JObject,
) -> jboolean {
    let libname_raw = env.get_string(&libname_raw);
    let libname = unwrap_result_or_java_error(&mut env, "unknown library", libname_raw)
        .to_string_lossy()
        .to_string();
    let funcname_raw = env.get_string(&funcname_raw);
    let funcname = unwrap_result_or_java_error(&mut env, &libname, funcname_raw)
        .to_string_lossy()
        .to_string();

    let manager = unsafe { LIBRARY_MANAGER.loaded_libraries.lock() };
    let lib = unwrap_option_or_java_error(
        &mut env,
        &libname,
        &"library".to_string(),
        manager.get(&libname),
    )
    .library();
    let func: Result<
        libloading::Symbol<extern "C" fn(JNIEnv<'_>, JObject<'_>)>,
        libloading::Error,
    > = unsafe { lib.get(funcname.as_bytes()) };
    if let Err(e) = &func {
        match e {
            libloading::Error::DlSym { desc: _ } => {
                return false.into();
            }
            libloading::Error::GetProcAddress { source: _ } => {
                return false.into();
            }
            _ => {
                throw_libloading_error(&mut env, &libname, e);
                return false.into();
            }
        }
    }
    let func = func.unwrap();
    func(env, ev);
    return true.into();
}

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_execute<'a>(
    mut env: JNIEnv<'a>,
    _obj: JObject,
    libname_raw: JString,
    funcname_raw: JString,
    _plugin: JObject,
    ev: JObject,
) -> JObject<'a> {
    let libname_raw = env.get_string(&libname_raw);
    let libname = unwrap_result_or_java_error(&mut env, "unknown library", libname_raw)
        .to_string_lossy()
        .to_string();
    let funcname_raw = env.get_string(&funcname_raw);
    let funcname = unwrap_result_or_java_error(&mut env, &libname, funcname_raw)
        .to_string_lossy()
        .to_string();

    let manager = unsafe { LIBRARY_MANAGER.loaded_libraries.lock() };
    let lib =
        unwrap_option_or_java_error(&mut env, &libname, &libname, manager.get(&libname)).library();
    let func: Result<
        libloading::Symbol<extern "C" fn(JNIEnv<'_>, JObject<'_>) -> JObject<'a>>,
        libloading::Error,
    > = unsafe { lib.get(funcname.as_bytes()) };
    if let Err(e) = &func {
        match e {
            _ => {
                throw_libloading_error(&mut env, &libname, e);
            }
        }
    }
    let func = func.unwrap();
    return func(env, ev);
}

pub fn unwrap_result_or_java_error<V, E, S>(
    mut env: &mut JNIEnv,
    libname: S,
    opt: Result<V, E>,
) -> V
where
    S: Into<String> + Display,
    E: Error,
{
    match opt {
        Ok(v) => v,
        Err(err) => {
            throw_with_error(
                &mut env,
                "net/ioixd/blackbox/exceptions/NativeLibrarySymbolLoadException",
                format!("error loading {}: {:?}", &libname, err),
            );
            unreachable!();
        }
    }
}

pub fn unwrap_option_or_java_error<V, S>(
    mut env: &mut JNIEnv,
    libname: S,
    valname: S,
    opt: Option<V>,
) -> V
where
    S: Into<String> + Display,
{
    match opt {
        Some(v) => v,
        None => {
            throw_with_error(
                &mut env,
                "net/ioixd/blackbox/exceptions/NativeLibrarySymbolLoadException",
                format!("error loading {}: {} is None", &libname, valname),
            );
            unreachable!();
        }
    }
}

pub fn throw_libloading_error(mut env: &mut JNIEnv, libname: &String, e: &libloading::Error) {
    let err_desc = e.to_string();
    match e {
        libloading::Error::DlSym { desc }
        | libloading::Error::DlOpen { desc }
        | libloading::Error::DlClose { desc } => {
            throw_with_error(
                &mut env,
                "net/ioixd/blackbox/exceptions/NativeLibrarySymbolLoadException",
                format!("error loading {}: {}. {:?}", &libname, err_desc, desc),
            );
        }
        libloading::Error::LoadLibraryExW { source }
        | libloading::Error::GetModuleHandleExW { source }
        | libloading::Error::FreeLibrary { source }
        | libloading::Error::GetProcAddress { source } => {
            throw_with_error(
                &mut env,
                "net/ioixd/blackbox/exceptions/NativeLibrarySymbolLoadException",
                format!("error loading {}: {}. {:?}", &libname, err_desc, source),
            );
        }
        libloading::Error::CreateCString { source } => {
            throw_with_error(
                &mut env,
                "net/ioixd/blackbox/exceptions/NativeLibrarySymbolLoadException",
                format!("error loading {}: {}. {:?}", &libname, err_desc, source),
            );
        }
        libloading::Error::CreateCStringWithTrailing { source } => {
            throw_with_error(
                &mut env,
                "net/ioixd/blackbox/exceptions/NativeLibrarySymbolLoadException",
                format!("error loading {}: {}. {:?}", &libname, err_desc, source),
            );
        }
        _ => {
            throw_with_error(
                &mut env,
                "net/ioixd/blackbox/exceptions/NativeLibrarySymbolLoadException",
                format!("{}; no other information given", err_desc),
            );
        }
    }
}

pub fn throw_with_error(env: &mut JNIEnv, exception_name: &str, exception_message: String) {
    let er = env.throw((exception_name, exception_message));
    if let Err(_) = er {
        env.exception_describe().unwrap();
    }
    // TEMPORARY: just describe the error
    env.exception_describe().unwrap();
}