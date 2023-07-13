package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.BlackBox;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class ExtendableMetadataValue implements MetadataValue {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableMetadataValue(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public java.lang.String asString() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "MetaDataValue", "asString",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.String) result;
    }

    public int asInt() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "MetaDataValue", "asInt",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (int) result;
    }

    public org.bukkit.plugin.Plugin getOwningPlugin() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "MetaDataValue", "getOwningPlugin",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.plugin.Plugin) result;
    }

    public void invalidate() {
        try {
            Misc.tryExecute(this.blackBox, this.inLibName, this.name, "invalidate", "asString",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public float asFloat() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "MetaDataValue", "asFloat",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (float) result;
    }

    public double asDouble() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "asDouble", "asString",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (double) result;
    }

    public long asLong() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "MetaDataValue", "asLong",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (long) result;
    }

    public short asShort() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "MetaDataValue", "asShort",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (short) result;
    }

    public byte asByte() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "MetaDataValue", "asByte",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (byte) result;
    }

    public boolean asBoolean() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "MetaDataValue", "asBoolean",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }

    public java.lang.Object value() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "MetaDataValue", "value",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.Object) result;
    }
}