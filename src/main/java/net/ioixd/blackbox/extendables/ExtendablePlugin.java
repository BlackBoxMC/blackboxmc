package net.ioixd.blackbox.extendables;

import org.bukkit.plugin.Plugin;

public class ExtendablePlugin implements Plugin {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;

    public ExtendablePlugin(int address, Plugin plugin, String name, String inLibName) {

        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public org.bukkit.Server getServer() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getServer",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.Server) result;
    }

    public org.bukkit.plugin.PluginDescriptionFile getDescription() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getDescription",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.plugin.PluginDescriptionFile) result;
    }

    public org.bukkit.generator.BiomeProvider getDefaultBiomeProvider(java.lang.String arg0, java.lang.String arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getDefaultBiomeProvider",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.generator.BiomeProvider) result;
    }

    public boolean isEnabled() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "isEnabled",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }

    public org.bukkit.configuration.file.FileConfiguration getConfig() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getConfig",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.configuration.file.FileConfiguration) result;
    }

    public java.io.File getDataFolder() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getDataFolder",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.io.File) result;
    }

    public void saveConfig() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "saveConfig",
                    address, plugin, new Object[] {}, true, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "saveDefaultConfig",
                    address, plugin, new Object[] {}, true, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveResource(java.lang.String arg0, boolean arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "saveResource",
                    address, plugin, new Object[] {}, true, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void reloadConfig() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "reloadConfig",
                    address, plugin, new Object[] {}, true, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public org.bukkit.plugin.PluginLoader getPluginLoader() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getPluginLoader",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.plugin.PluginLoader) result;
    }

    public void onDisable() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "onDisable",
                    address, plugin, new Object[] {}, true, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onLoad() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "onLoad",
                    address, plugin, new Object[] {}, true, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onEnable() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "onEnable",
                    address, plugin, new Object[] {}, true, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean isNaggable() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "isNaggable",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }

    public void setNaggable(boolean arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "setNaggable",
                    address, plugin, new Object[] { arg0 }, true, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public org.bukkit.generator.ChunkGenerator getDefaultWorldGenerator(java.lang.String arg0, java.lang.String arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getDefaultWorldGenerator",
                    address, plugin, new Object[] { arg0, arg1 }, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.generator.ChunkGenerator) result;
    }

    public java.lang.String getName() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getName",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.String) result;
    }

    public java.io.InputStream getResource(java.lang.String arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getResource",
                    address, plugin, new Object[] { arg0 }, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.io.InputStream) result;
    }

    public java.util.logging.Logger getLogger() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Plugin", "getLogger",
                    address, plugin, new Object[] {}, true, true);
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
                    address, plugin, new Object[] { arg0, arg1, arg2, arg3 }, true, true);
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
                    address, plugin, new Object[] { arg0, arg1, arg2, arg3 }, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }
}