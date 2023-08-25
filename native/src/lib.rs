struct SwitchableLibrary {
    library: RwLock<ManuallyDrop<Library>>,
    enabled: RwLock<bool>,
}

impl SwitchableLibrary {
    fn new(library: ManuallyDrop<Library>, enabled: bool) -> Self {
        Self {
            library: RwLock::new(library),
            enabled: RwLock::new(enabled),
        }
    }
    fn set_enabled(&self, enabled: bool) {
        *self.enabled.write() = enabled;
    }
}

struct LibraryManager {
    loaded_libraries: RwLock<HashMap<String, SwitchableLibrary>>,
}

impl LibraryManager {
    fn new() -> Self {
        Self {
            loaded_libraries: RwLock::new(HashMap::new()),
        }
    }
    fn push_lib(&self, libname: String, lib: (ManuallyDrop<Library>, bool)) {
        let libs = &mut self.loaded_libraries.write();
        libs.insert(libname, SwitchableLibrary::new(lib.0, lib.1));
    }
    fn libraries(&self) -> RwLockReadGuard<HashMap<String, SwitchableLibrary>> {
        self.loaded_libraries.read()
    }
    fn set_enabled(&self, file: String, enabled: bool) {
        self.libraries().get(&file).unwrap().set_enabled(enabled);
    }
    fn library_names(&self) -> String {
        self.libraries()
            .iter()
            .map(|f| f.0.clone())
            .collect::<Vec<String>>()
            .join(",")
    }
}

static LIBRARY_MANAGER: Lazy<LibraryManager> = Lazy::new(|| LibraryManager::new());

use jni::{
    objects::{JClass, JObject, JString},
    sys::{jboolean, jint},
    JNIEnv,
};
use libloading::{Library, Symbol};
use once_cell::sync::Lazy;
use parking_lot::{Mutex, RwLock, RwLockReadGuard};

use std::{collections::HashMap, error::Error, fmt::Display, mem::ManuallyDrop};

fn load_library(path: String) -> Result<Library, libloading::Error> {
    unsafe {
        // Load and initialize library
        #[cfg(target_os = "linux")]
        let library: Library = {
            // Load library with `RTLD_NOW | RTLD_NODELETE` to fix a SIGSEGV
            ::libloading::os::unix::Library::open(Some(&path), 0x2 | 0x1000)?.into()
        };
        #[cfg(not(target_os = "linux"))]
        let library = Library::new(&path)?;
        Ok(library)
    }
}

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
    let lib = load_library(file.clone());
    if let Err(e) = &lib {
        throw_libloading_error(&mut env, &file, e);
        return;
    }
    let lib = lib.unwrap();
    let parts = file
        .split(std::path::MAIN_SEPARATOR)
        .map(|f| f.to_string())
        .collect::<Vec<String>>();
    let mut name = parts.get(parts.len() - 1).unwrap().clone();
    #[cfg(target_os = "windows")]
    {
        name = name.replace(".dll", "");
    }
    #[cfg(target_os = "macos")]
    {
        name = name.replace(".dylib", "");
    }
    #[cfg(target_os = "linux")]
    {
        name = name.replace(".so", "");
    }
    LIBRARY_MANAGER.push_lib(name, (ManuallyDrop::new(lib), true));
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
    LIBRARY_MANAGER.set_enabled(file, true);
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
    LIBRARY_MANAGER.set_enabled(file, false);
}

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_libraryNames<'a>(
    env: JNIEnv<'a>,
    _obj: JObject,
) -> JString<'a> {
    env.new_string(LIBRARY_MANAGER.library_names()).unwrap()
}

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_libraryHasFunction<'a>(
    mut env: JNIEnv<'a>,
    _obj: JObject,
    libname_raw: JString,
    funcname_raw: JString,
) -> jboolean {
    let libname_raw = env.get_string(&libname_raw);
    let libname = unwrap_result_or_java_error(&mut env, "unknown library", libname_raw)
        .to_string_lossy()
        .to_string();
    let funcname_raw = env.get_string(&funcname_raw);
    let funcname = unwrap_result_or_java_error(&mut env, &libname, funcname_raw)
        .to_string_lossy()
        .to_string();

    let manager = LIBRARY_MANAGER.libraries();
    let lib = manager.get(&libname).unwrap().library.read();
    let func: Result<
        libloading::Symbol<unsafe extern "C" fn(JNIEnv<'_>, JObject<'_>)>,
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
                throw_libloading_error(&mut env, &libname, &e);
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

    let manager = LIBRARY_MANAGER.libraries();
    let lib = unwrap_option_or_java_error(
        &mut env,
        &libname,
        &"library".to_string(),
        manager.get(&libname),
    )
    .library
    .read();
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
                throw_libloading_error(&mut env, &libname, &e);
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
    address: jint,
    plugin: JObject,
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

    let manager = LIBRARY_MANAGER.libraries();
    let lib = unwrap_option_or_java_error(
        &mut env,
        &libname,
        &format!("manager.get({})", &libname),
        manager.get(&libname),
    )
    .library
    .write();
    let func: Result<
        libloading::Symbol<
            extern "C" fn(JNIEnv<'_>, jint, JObject<'_>, JObject<'_>) -> JObject<'a>,
        >,
        libloading::Error,
    > = unsafe { lib.get(funcname.as_bytes()) };
    if let Err(e) = &func {
        match e {
            _ => {
                throw_libloading_error(&mut env, &libname, &e);
            }
        }
    }
    let func = func.unwrap();
    if !plugin.is_null() && !ev.is_null() {
        return func(env, address, plugin, ev);
    } else {
        println!("NULL");
        return JObject::null();
    }
}

type BiConsumer = unsafe extern "system" fn(t: JObject, u: JObject);
#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_BiConsumerLink_acceptNative<'a>(
    mut env: JNIEnv<'a>,
    this: JObject,
    obj1: JObject,
    obj2: JObject,
) {
    || -> Result<(), Box<dyn Error>> {
        let field = env.get_field(this, "address", "J");
        let address_raw = unwrap_result_or_java_error(&mut env, "", field);
        let address = address_raw.j()? as u64;
        println!("{:#x}", address);
        unsafe {
            let func: BiConsumer = std::mem::transmute(address);
            func(obj1, obj2);
        }
        Ok(())
    }()
    .unwrap();
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
                format!("error with {}: {:?}", &libname, err),
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
                format!("error with {}: {} is None", &libname, valname),
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
}
