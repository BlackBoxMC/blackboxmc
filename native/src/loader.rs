use jni::{
    objects::{JObject, JString},
    sys::jint,
    JNIEnv,
};

pub trait Loader {
    fn execute<'a>(
        env: JNIEnv<'a>,
        libname: String,
        funcname: String,
        address: jint,
        plugin: JObject,
        ev: JObject,
    ) -> JObject<'a>;
    fn library_has_function<'a>(env: JNIEnv<'a>, libname: String, funcname: String) -> bool;
    fn send_event<'a>(env: JNIEnv<'a>, libname: String, funcname: String, ev: JObject) -> bool;
    fn load_plugin<'a>(env: JNIEnv<'a>, file: String);
    fn enable_plugin<'a>(file: String);
    fn disable_plugin<'a>(file: String);
    fn library_names<'a>(env: JNIEnv) -> JString;
}
