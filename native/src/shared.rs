use jni::{
    objects::{JObject, JString},
    sys::jint,
    JNIEnv,
};
use libloading::Library;
use once_cell::sync::Lazy;
use parking_lot::{RwLock, RwLockReadGuard};

static LIBRARY_MANAGER: Lazy<LibraryManager> = Lazy::new(|| LibraryManager::new());

use std::{collections::HashMap, mem::ManuallyDrop};

use crate::{loader::Loader, throw_libloading_error, unwrap_option_or_java_error};

pub fn load_library(path: String) -> Result<Library, libloading::Error> {
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

struct SwitchableLibrary {
    pub library: RwLock<ManuallyDrop<Library>>,
    enabled: RwLock<bool>,
}

impl SwitchableLibrary {
    pub fn new(library: ManuallyDrop<Library>, enabled: bool) -> Self {
        Self {
            library: RwLock::new(library),
            enabled: RwLock::new(enabled),
        }
    }
    pub fn set_enabled(&self, enabled: bool) {
        *self.enabled.write() = enabled;
    }
}

struct LibraryManager {
    loaded_libraries: RwLock<HashMap<String, SwitchableLibrary>>,
}

impl LibraryManager {
    pub fn new() -> Self {
        Self {
            loaded_libraries: RwLock::new(HashMap::new()),
        }
    }
    pub fn push_lib(&self, libname: String, lib: (ManuallyDrop<Library>, bool)) {
        let libs = &mut self.loaded_libraries.write();
        libs.insert(libname, SwitchableLibrary::new(lib.0, lib.1));
    }
    pub fn libraries(&self) -> RwLockReadGuard<HashMap<String, SwitchableLibrary>> {
        self.loaded_libraries.read()
    }
    pub fn set_enabled(&self, file: String, enabled: bool) {
        self.libraries().get(&file).unwrap().set_enabled(enabled);
    }
    pub fn library_names(&self) -> String {
        self.libraries()
            .iter()
            .map(|f| f.0.clone())
            .collect::<Vec<String>>()
            .join(",")
    }
}

pub struct SharedLoader;

impl Loader for SharedLoader {
    fn execute<'a>(
        mut env: JNIEnv<'a>,
        libname: String,
        funcname: String,
        address: jint,
        plugin: JObject,
        ev: JObject,
    ) -> JObject<'a> {
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
            return JObject::null();
        }
    }

    fn library_has_function<'a>(mut env: JNIEnv<'a>, libname: String, funcname: String) -> bool {
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
    fn send_event<'a>(mut env: JNIEnv<'a>, libname: String, funcname: String, ev: JObject) -> bool {
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

    fn load_plugin<'a>(mut env: JNIEnv<'a>, file: String) {
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

    fn enable_plugin<'a>(file: String) {
        LIBRARY_MANAGER.set_enabled(file, true);
    }

    fn disable_plugin<'a>(file: String) {
        LIBRARY_MANAGER.set_enabled(file, false);
    }

    fn library_names<'a>(env: JNIEnv) -> JString {
        env.new_string(LIBRARY_MANAGER.library_names()).unwrap()
    }
}
