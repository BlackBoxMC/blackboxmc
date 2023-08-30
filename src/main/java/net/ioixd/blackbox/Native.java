package net.ioixd.blackbox;

import org.bukkit.plugin.Plugin;

public class Native {
    public static native String loadPlugin(String filename, boolean wasm);

    public static native void enablePlugin(String libname, boolean wasm);

    public static native void disablePlugin(String libname, boolean wasm);

    public static native String libraryNames();

    public static native boolean libraryHasFunction(String libName, String functionName, boolean wasm);

    public static native boolean sendEvent(String libName, String functionName, Object object, boolean wasm);

    public static native Object execute(String libName, String functionName, int address, Plugin plugin,
            Object[] objects, boolean wasm) throws Exception;
}
