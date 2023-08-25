package net.ioixd.blackbox.extendables;

import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class ExtendableMetadataValue implements MetadataValue {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;

    public ExtendableMetadataValue(int address, Plugin plugin, String name, String inLibName) {

        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public java.lang.String asString() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "MetaDataValue", "asString",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.String) result;
    }

    public int asInt() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "MetaDataValue", "asInt",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (int) result;
    }

    public org.bukkit.plugin.Plugin getOwningPlugin() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "MetaDataValue", "getOwningPlugin",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.plugin.Plugin) result;
    }

    public void invalidate() {
        try {
            Misc.tryExecute(this.inLibName, this.name, "invalidate", "asString",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public float asFloat() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "MetaDataValue", "asFloat",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (float) result;
    }

    public double asDouble() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "asDouble", "asString",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (double) result;
    }

    public long asLong() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "MetaDataValue", "asLong",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (long) result;
    }

    public short asShort() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "MetaDataValue", "asShort",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (short) result;
    }

    public byte asByte() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "MetaDataValue", "asByte",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (byte) result;
    }

    public boolean asBoolean() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "MetaDataValue", "asBoolean",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }

    public java.lang.Object value() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "MetaDataValue", "value",
                    address, plugin, new Object[] {}, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.Object) result;
    }
}