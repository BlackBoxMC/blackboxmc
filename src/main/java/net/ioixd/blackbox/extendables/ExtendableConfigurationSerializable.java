package net.ioixd.blackbox.extendables;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.plugin.Plugin;

public class ExtendableConfigurationSerializable implements ConfigurationSerializable {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;

    public ExtendableConfigurationSerializable(int address, Plugin plugin, String name, String inLibName) {

        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public java.util.Map<java.lang.String, java.lang.Object> serialize() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ConfigurationSerializable", "serialize",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.Map<java.lang.String, java.lang.Object>) result;
    }

}