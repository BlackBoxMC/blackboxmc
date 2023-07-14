package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.Native;

import org.bukkit.plugin.PluginLoader;

public class ExtendablePluginLoader implements PluginLoader {
    public String name;
    public String inLibName;

    ExtendablePluginLoader(String name, String inLibName) {

        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public org.bukkit.plugin.Plugin loadPlugin(java.io.File arg0) {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__PluginLoader__" + this.name + "__loadPlugin",
                    new Object[] {
                            arg0
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.plugin.Plugin) result;
    }

    public org.bukkit.plugin.PluginDescriptionFile getPluginDescription(java.io.File arg0) {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__PluginLoader__" + this.name + "__getPluginDescription",
                    new Object[] {
                            arg0
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.plugin.PluginDescriptionFile) result;
    }

    public java.util.regex.Pattern[] getPluginFileFilters() {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__PluginLoader__" + this.name + "__getPluginFileFilters",
                    new Object[] {});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.regex.Pattern[]) result;
    }

    public java.util.Map<java.lang.Class<? extends org.bukkit.event.Event>, java.util.Set<org.bukkit.plugin.RegisteredListener>> createRegisteredListeners(
            org.bukkit.event.Listener arg0, org.bukkit.plugin.Plugin arg1) {
        Object result = null;
        try {
            result = Native.execute(this.inLibName,
                    "__extends__PluginLoader__" + this.name + "__createRegisteredListeners", new Object[] {
                            arg0, arg1
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.Map<java.lang.Class<? extends org.bukkit.event.Event>, java.util.Set<org.bukkit.plugin.RegisteredListener>>) result;
    }

    public void enablePlugin(org.bukkit.plugin.Plugin arg0) {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__PluginLoader__" + this.name + "__enablePlugin",
                    new Object[] {
                            arg0
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void disablePlugin(org.bukkit.plugin.Plugin arg0) {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__PluginLoader__" + this.name + "__disablePlugin",
                    new Object[] {
                            arg0
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}