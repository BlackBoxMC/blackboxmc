package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.BlackBox;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ExtendableFileConfiguration extends FileConfiguration {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableFileConfiguration(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public java.lang.String saveToString() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "FileConfiguration", "saveToString",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.String) result;
    }

    public void loadFromString(java.lang.String arg0) {
        try {
            Misc.tryExecute(this.blackBox, this.inLibName, this.name, "FileConfiguration", "loadToString",
                    new Object[] { arg0 }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}