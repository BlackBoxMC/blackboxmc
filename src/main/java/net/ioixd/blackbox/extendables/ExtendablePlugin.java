package net.ioixd.blackbox.extendables;

import org.bukkit.plugin.Plugin;

public class ExtendablePlugin implements Plugin {
    public String name;
    public String inLibName;

    ExtendablePlugin(String name, String inLibName) {

        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public org.bukkit.Server getServer() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getServer",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.Server) result;
    }

    public org.bukkit.plugin.PluginDescriptionFile getDescription() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getDescription",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.plugin.PluginDescriptionFile) result;
    }

    public org.bukkit.generator.BiomeProvider getDefaultBiomeProvider(java.lang.String arg0, java.lang.String arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getDefaultBiomeProvider",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.generator.BiomeProvider) result;
    }

    public boolean isEnabled() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "isEnabled",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }

    public org.bukkit.configuration.file.FileConfiguration getConfig() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getConfig",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.configuration.file.FileConfiguration) result;
    }

    public java.io.File getDataFolder() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getDataFolder",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.io.File) result;
    }

    public void saveConfig() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "saveConfig",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "saveDefaultConfig",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveResource(java.lang.String arg0, boolean arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "saveResource",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void reloadConfig() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "reloadConfig",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public org.bukkit.plugin.PluginLoader getPluginLoader() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getPluginLoader",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.plugin.PluginLoader) result;
    }

    public void onDisable() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "onDisable",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onLoad() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "onLoad",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onEnable() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "onEnable",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean isNaggable() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "isNaggable",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }

    public void setNaggable(boolean arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "setNaggable",
                    new Object[] { arg0 }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public org.bukkit.generator.ChunkGenerator getDefaultWorldGenerator(java.lang.String arg0, java.lang.String arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getDefaultWorldGenerator",
                    new Object[] { arg0, arg1 }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.generator.ChunkGenerator) result;
    }

    public java.lang.String getName() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getName",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.String) result;
    }

    public java.io.InputStream getResource(java.lang.String arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getResource",
                    new Object[] { arg0 }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.io.InputStream) result;
    }

    public java.util.logging.Logger getLogger() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getLogger",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.logging.Logger) result;
    }

    public java.util.List<java.lang.String> onTabComplete(org.bukkit.command.CommandSender arg0,
            org.bukkit.command.Command arg1, java.lang.String arg2, java.lang.String[] arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "onTabComplete",
                    new Object[] { arg0, arg1, arg2, arg3 }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.List<java.lang.String>) result;
    }

    public boolean onCommand(org.bukkit.command.CommandSender arg0, org.bukkit.command.Command arg1,
            java.lang.String arg2, java.lang.String[] arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "onCommand", "onCommand",
                    new Object[] { arg0, arg1, arg2, arg3 }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }
}