package net.ioixd.blackbox;

import org.bukkit.plugin.Plugin;

public class Native {
    public static native String loadPlugin(String filename);
    public static native void enablePlugin(String libname);
    public static native void disablePlugin(String libname);

    public static native String libraryNames();
    public static native boolean libraryHasFunction(String libName, String functionName);
    public static native boolean sendEvent(String libName, String functionName, Object object);
    public static native Object execute(String libName, String functionName, Plugin plugin, Object[] objects) throws Exception;
}
