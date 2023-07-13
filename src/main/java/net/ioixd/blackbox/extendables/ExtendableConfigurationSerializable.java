package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.BlackBox;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.plugin.Plugin;

public class ExtendableConfigurationSerializable implements ConfigurationSerializable {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableConfigurationSerializable(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public java.util.Map<java.lang.String, java.lang.Object> serialize() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "ConfigurationSerializable", "serialize",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.Map<java.lang.String, java.lang.Object>) result;
    }
}