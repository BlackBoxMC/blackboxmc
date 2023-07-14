package net.ioixd.blackbox.extendables;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class ExtendableConfigurationSerializable implements ConfigurationSerializable {
    public String name;
    public String inLibName;

    ExtendableConfigurationSerializable(String name, String inLibName) {

        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public java.util.Map<java.lang.String, java.lang.Object> serialize() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ConfigurationSerializable", "serialize",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.Map<java.lang.String, java.lang.Object>) result;
    }

}