pub mod loader;
pub mod shared;
pub mod wasm;

use std::{error::Error, fmt::Display};

use jni::{
    objects::{JObject, JString},
    sys::{jboolean, jint},
    JNIEnv,
};
use loader::Loader;
use shared::SharedLoader;
use wasm::WASMLoader;

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_loadPlugin<'a>(
    mut env: JNIEnv<'a>,
    _obj: JObject,
    filename: JString,
    wasm: jboolean,
) {
    let file = env
        .get_string(&filename)
        .unwrap()
        .to_string_lossy()
        .to_string();
    if wasm == 1 {
        WASMLoader::load_plugin(env, file)
    } else {
        SharedLoader::load_plugin(env, file)
    }
}

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_enablePlugin<'a>(
    mut env: JNIEnv<'a>,
    _obj: JObject,
    filename: JString,
    wasm: jboolean,
) {
    let file = env
        .get_string(&filename)
        .unwrap()
        .to_string_lossy()
        .to_string();
    if wasm == 1 {
        WASMLoader::enable_plugin(file)
    } else {
        SharedLoader::enable_plugin(file)
    }
}

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_disablePlugin<'a>(
    mut env: JNIEnv<'a>,
    _obj: JObject,
    filename: JString,
    wasm: jboolean,
) {
    let file = env
        .get_string(&filename)
        .unwrap()
        .to_string_lossy()
        .to_string();
    if wasm == 1 {
        WASMLoader::disable_plugin(file)
    } else {
        SharedLoader::disable_plugin(file)
    }
}

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_libraryNames<'a>(
    env: JNIEnv<'a>,
    _obj: JObject,
    wasm: jboolean,
) -> JString<'a> {
    if wasm == 1 {
        WASMLoader::library_names(env)
    } else {
        SharedLoader::library_names(env)
    }
}

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_libraryHasFunction<'a>(
    mut env: JNIEnv<'a>,
    _obj: JObject,
    libname_raw: JString,
    funcname_raw: JString,
    wasm: jboolean,
) -> jboolean {
    let libname_raw = env.get_string(&libname_raw);
    let libname = unwrap_result_or_java_error(&mut env, "unknown library", libname_raw)
        .to_string_lossy()
        .to_string();
    let funcname_raw = env.get_string(&funcname_raw);
    let funcname = unwrap_result_or_java_error(&mut env, &libname, funcname_raw)
        .to_string_lossy()
        .to_string();

    if wasm == 1 {
        WASMLoader::library_has_function(env, libname, funcname).into()
    } else {
        SharedLoader::library_has_function(env, libname, funcname).into()
    }
}

#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_Native_sendEvent<'a>(
    mut env: JNIEnv<'a>,
    _obj: JObject,
    libname_raw: JString,
    funcname_raw: JString,
    ev: JObject,
    wasm: jboolean,
) -> jboolean {
    let libname_raw = env.get_string(&libname_raw);
    let libname = unwrap_result_or_java_error(&mut env, "unknown library", libname_raw)
        .to_string_lossy()
        .to_string();
    let funcname_raw = env.get_string(&funcname_raw);
    let funcname = unwrap_result_or_java_error(&mut env, &libname, funcname_raw)
        .to_string_lossy()
        .to_string();
    if wasm == 1 {
        WASMLoader::send_event(env, libname, funcname, ev).into()
    } else {
        SharedLoader::send_event(env, libname, funcname, ev).into()
    }
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
    wasm: jboolean,
) -> JObject<'a> {
    let libname_raw = env.get_string(&libname_raw);
    let libname = unwrap_result_or_java_error(&mut env, "unknown library", libname_raw)
        .to_string_lossy()
        .to_string();
    let funcname_raw = env.get_string(&funcname_raw);
    let funcname = unwrap_result_or_java_error(&mut env, &libname, funcname_raw)
        .to_string_lossy()
        .to_string();

    if wasm == 1 {
        WASMLoader::execute(env, libname, funcname, address, plugin, ev)
    } else {
        SharedLoader::execute(env, libname, funcname, address, plugin, ev)
    }
}

type BiConsumer = unsafe extern "system" fn(t: JObject, u: JObject);
#[no_mangle]
pub extern "system" fn Java_net_ioixd_blackbox_BiConsumerLink_acceptNative<'a>(
    mut env: JNIEnv<'a>,
    this: JObject,
    obj1: JObject,
    obj2: JObject,
    wasm: jboolean,
) {
    if wasm == 1 {
        throw_with_error(
            &mut env,
            "java/lang/UnsupportedOperationException",
            "BiConsumerLinks aren't supported in WASM libraries yet",
        );
    } else {
        || -> Result<(), Box<dyn Error>> {
            let field = env.get_field(this, "address", "J");
            let address_raw = unwrap_result_or_java_error(&mut env, "", field);
            let address = address_raw.j()? as u64;
            unsafe {
                let func: BiConsumer = std::mem::transmute(address);
                func(obj1, obj2);
            }
            Ok(())
        }()
        .unwrap();
    }
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

pub fn throw_with_error(
    env: &mut JNIEnv,
    exception_name: impl Into<String>,
    exception_message: impl Into<String>,
) {
    let er = env.throw((exception_name.into().as_str(), exception_message.into()));
    if let Err(_) = er {
        env.exception_describe().unwrap();
    }
}
