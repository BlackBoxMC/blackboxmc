package net.ioixd.blackbox.extendables;

import org.bukkit.persistence.PersistentDataType;

public class ExtendablePersistentDataType implements PersistentDataType {
    public String name;
    public String inLibName;

    ExtendablePersistentDataType(String name, String inLibName) {

        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public java.lang.Class<?> getPrimitiveType() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PersistentDataType", "getPrimitiveType",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.Class<?>) result;
    }

    public java.lang.Class<?> getComplexType() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PersistentDataType", "getComplexType",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.Class<?>) result;
    }

    public java.lang.Object toPrimitive(java.lang.Object arg0,
            org.bukkit.persistence.PersistentDataAdapterContext arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PersistentDataType", "toPrimtive",
                    new Object[] { arg0, arg1 }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.Object) result;
    }

    public java.lang.Object fromPrimitive(java.lang.Object arg0,
            org.bukkit.persistence.PersistentDataAdapterContext arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "PersistentDataType", "fromPrimitive",
                    new Object[] { arg0, arg1 }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.Object) result;
    }
}