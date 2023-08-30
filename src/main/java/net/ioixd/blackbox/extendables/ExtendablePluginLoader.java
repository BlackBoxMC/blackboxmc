package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.Native;

import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.Plugin;

public class ExtendablePluginLoader implements PluginLoader {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;
    public boolean wasm;

    public ExtendablePluginLoader(int address, Plugin plugin, String name, String inLibName, boolean wasm) {

        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        this.wasm = wasm;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass(), this.wasm);
    }

    public org.bukkit.plugin.Plugin loadPlugin(java.io.File arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginLoader", "loadPlugin",
                    address, plugin, new Object[] {
                            arg0
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.plugin.Plugin) result;
    }

    public org.bukkit.plugin.PluginDescriptionFile getPluginDescription(java.io.File arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginLoader", "getPluginDescription",
                    address, plugin, new Object[] {
                            arg0
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.plugin.PluginDescriptionFile) result;
    }

    public java.util.regex.Pattern[] getPluginFileFilters() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginLoader", "getPluginFileFilters",
                    address, plugin, new Object[] {}, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.regex.Pattern[]) result;
    }

    public java.util.Map<java.lang.Class<? extends org.bukkit.event.Event>, java.util.Set<org.bukkit.plugin.RegisteredListener>> createRegisteredListeners(
            org.bukkit.event.Listener arg0, org.bukkit.plugin.Plugin arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName,
                    this.name, "PluginLoader", "createRegisteredListeners", address, plugin,
                    new Object[] {
                            arg0, arg1
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.Map<java.lang.Class<? extends org.bukkit.event.Event>, java.util.Set<org.bukkit.plugin.RegisteredListener>>) result;
    }

    public void enablePlugin(org.bukkit.plugin.Plugin arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginLoader", "enablePlugin",
                    address, plugin, new Object[] {
                            arg0
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void disablePlugin(org.bukkit.plugin.Plugin arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginLoader", "disablePlugin",
                    address, plugin, new Object[] {
                            arg0
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}