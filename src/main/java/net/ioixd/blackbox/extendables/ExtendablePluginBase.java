package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;

import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.Plugin;

public class ExtendablePluginBase extends PluginBase {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;
    public boolean wasm;

    public BlackBox blackBox;

    public ExtendablePluginBase(int address, Plugin plugin, String name, String inLibName, boolean wasm) {

        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        this.wasm = wasm;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass(), this.wasm);
    }

    public org.bukkit.Server getServer() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "getServer",
                    address, plugin, new Object[] {}, true, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.Server) result;
    }

    public org.bukkit.plugin.PluginDescriptionFile getDescription() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "getDescription",
                    address, plugin, new Object[] {}, true, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.plugin.PluginDescriptionFile) result;
    }

    public org.bukkit.generator.BiomeProvider getDefaultBiomeProvider(java.lang.String arg0, java.lang.String arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName,
                    this.name, "PluginBase", "getDefaultBiomeProvider",
                    address, plugin, new Object[] {
                            arg0, arg1
                    }, true, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.generator.BiomeProvider) result;
    }

    public boolean isEnabled() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "isEnabled",
                    address, plugin, new Object[] {}, true, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }

    public org.bukkit.configuration.file.FileConfiguration getConfig() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "getConfig",
                    address, plugin, new Object[] {}, true, this.wasm);
        } catch (Exception ex) {
        }
        return (org.bukkit.configuration.file.FileConfiguration) result;
    }

    public java.io.File getDataFolder() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "getDataFolder",
                    address, plugin, new Object[] {}, true, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.io.File) result;
    }

    public void saveConfig() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "saveConfig",
                    address, plugin, new Object[] {}, true, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "saveDefaultConfig",
                    address, plugin, new Object[] {}, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
    }

    public void saveResource(java.lang.String arg0, boolean arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "saveResource",
                    address, plugin, new Object[] {
                            arg0, arg1
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
    }

    public void reloadConfig() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "reloadConfig",
                    address, plugin, new Object[] {}, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
    }

    public org.bukkit.plugin.PluginLoader getPluginLoader() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "getPluginLoader",
                    address, plugin, new Object[] {}, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
        return (org.bukkit.plugin.PluginLoader) result;
    }

    public void onDisable() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "onDisable",
                    address, plugin, new Object[] {}, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
    }

    public void onLoad() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "onLoad",
                    address, plugin, new Object[] {}, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
    }

    public void onEnable() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "onEnable",
                    address, plugin, new Object[] {}, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
    }

    public boolean isNaggable() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "isNaggable",
                    address, plugin, new Object[] {}, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
        return (boolean) result;
    }

    public void setNaggable(boolean arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "setNaggable",
                    address, plugin, new Object[] {
                            arg0
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
    }

    public org.bukkit.generator.ChunkGenerator getDefaultWorldGenerator(java.lang.String arg0, java.lang.String arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName,
                    this.name, "PluginBase", "getDefaultWorldGenerator", address, plugin,
                    new Object[] {
                            arg0, arg1
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
        return (org.bukkit.generator.ChunkGenerator) result;
    }

    public java.io.InputStream getResource(java.lang.String arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "getResource",
                    address, plugin, new Object[] {
                            arg0
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
        return (java.io.InputStream) result;
    }

    public java.util.logging.Logger getLogger() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "getLogger",
                    address, plugin, new Object[] {}, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
        return (java.util.logging.Logger) result;
    }

    public java.util.List<java.lang.String> onTabComplete(org.bukkit.command.CommandSender arg0,
            org.bukkit.command.Command arg1, java.lang.String arg2, java.lang.String[] arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "onTabComplete",
                    address, plugin, new Object[] {
                            arg0, arg1, arg2, arg3
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
        return (java.util.List<java.lang.String>) result;
    }

    public boolean onCommand(org.bukkit.command.CommandSender arg0, org.bukkit.command.Command arg1,
            java.lang.String arg2, java.lang.String[] arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PluginBase", "onCommand",
                    address, plugin, new Object[] {
                            arg0, arg1, arg2, arg3
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
        return (boolean) result;
    }
}