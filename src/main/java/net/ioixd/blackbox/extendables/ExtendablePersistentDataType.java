package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
public class ExtendablePersistentDataType implements PersistentDataType {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendablePersistentDataType(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

	public java.lang.Class<?> getPrimitiveType() {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__PersistentDataType__"+this.name+"__getPrimitiveType", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (java.lang.Class<?>) result;
    }
	public java.lang.Class<?> getComplexType() {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__PersistentDataType__"+this.name+"__getComplexType", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (java.lang.Class<?>) result;
    }
	public java.lang.Object toPrimitive(java.lang.Object arg0, org.bukkit.persistence.PersistentDataAdapterContext arg1) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__PersistentDataType__"+this.name+"__toPrimitive", this.blackBox, new Object[] {
               arg0, arg1
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (java.lang.Object) result;
    }
	public java.lang.Object fromPrimitive(java.lang.Object arg0, org.bukkit.persistence.PersistentDataAdapterContext arg1) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__PersistentDataType__"+this.name+"__fromPrimitive", this.blackBox, new Object[] {
               arg0, arg1
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (java.lang.Object) result;
    }
}