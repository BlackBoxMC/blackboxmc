package net.ioixd.blackbox.extendables;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.plugin.Plugin;

public class ExtendableConfigurationSerializable implements ConfigurationSerializable {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;
    public boolean wasm;

    public ExtendableConfigurationSerializable(int address, Plugin plugin, String name, String inLibName,
            boolean wasm) {

        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        this.wasm = wasm;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass(), this.wasm);
    }

    public java.util.Map<java.lang.String, java.lang.Object> serialize() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ConfigurationSerializable", "serialize",
                    address, plugin, new Object[] {}, true, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.Map<java.lang.String, java.lang.Object>) result;
    }

}