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

    public BlackBox blackBox;

    public ExtendablePluginBase(int address, Plugin plugin, String name, String inLibName) {

        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public org.bukkit.Server getServer() {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__getServer",
                    address, plugin, new Object[] {});
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
        return (org.bukkit.Server) result;
    }

    public org.bukkit.plugin.PluginDescriptionFile getDescription() {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__getDescription",
                    address, plugin, new Object[] {});
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
        return (org.bukkit.plugin.PluginDescriptionFile) result;
    }

    public org.bukkit.generator.BiomeProvider getDefaultBiomeProvider(java.lang.String arg0, java.lang.String arg1) {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__getDefaultBiomeProvider",
                    address, plugin, new Object[] {
                            arg0, arg1
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
        return (org.bukkit.generator.BiomeProvider) result;
    }

    public boolean isEnabled() {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__isEnabled",
                    address, plugin, new Object[] {});
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
        return (boolean) result;
    }

    public org.bukkit.configuration.file.FileConfiguration getConfig() {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__getConfig",
                    address, plugin, new Object[] {});
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
        return (org.bukkit.configuration.file.FileConfiguration) result;
    }

    public java.io.File getDataFolder() {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__getDataFolder",
                    address, plugin, new Object[] {});
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
        return (java.io.File) result;
    }

    public void saveConfig() {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__saveConfig",
                    address, plugin, new Object[] {});
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
    }

    public void saveDefaultConfig() {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__saveDefaultConfig",
                    address, plugin, new Object[] {});
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
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__saveResource",
                    address, plugin, new Object[] {
                            arg0, arg1
                    });
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
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__reloadConfig",
                    address, plugin, new Object[] {});
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
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__getPluginLoader",
                    address, plugin, new Object[] {});
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
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__onDisable",
                    address, plugin, new Object[] {});
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
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__onLoad",
                    address, plugin, new Object[] {});
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
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__onEnable",
                    address, plugin, new Object[] {});
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
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__isNaggable",
                    address, plugin, new Object[] {});
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
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__setNaggable",
                    address, plugin, new Object[] {
                            arg0
                    });
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
            result = Native.execute(this.inLibName,
                    "__extends__PluginBase__" + this.name + "__getDefaultWorldGenerator", address, plugin,
                    new Object[] {
                            arg0, arg1
                    });
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
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__getResource",
                    address, plugin, new Object[] {
                            arg0
                    });
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
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__getLogger",
                    address, plugin, new Object[] {});
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
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__onTabComplete",
                    address, plugin, new Object[] {
                            arg0, arg1, arg2, arg3
                    });
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
            result = Native.execute(this.inLibName, "__extends__PluginBase__" + this.name + "__onCommand",
                    address, plugin, new Object[] {
                            arg0, arg1, arg2, arg3
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe(
                    "The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
        return (boolean) result;
    }
}