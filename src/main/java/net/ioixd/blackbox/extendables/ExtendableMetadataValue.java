package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;
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
           result = Native.execute(this.inLibName, "__extends__MetadataValue__"+this.name+"__asString", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (java.lang.String) result;
    }
	public int asInt() {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__MetadataValue__"+this.name+"__asInt", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (int) result;
    }
	public org.bukkit.plugin.Plugin getOwningPlugin() {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__MetadataValue__"+this.name+"__getOwningPlugin", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (org.bukkit.plugin.Plugin) result;
    }
	public void invalidate() {
       try {
           Native.execute(this.inLibName, "__extends__MetadataValue__"+this.name+"__invalidate", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
    }
	public float asFloat() {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__MetadataValue__"+this.name+"__asFloat", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (float) result;
    }
	public double asDouble() {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__MetadataValue__"+this.name+"__asDouble", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (double) result;
    }
	public long asLong() {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__MetadataValue__"+this.name+"__asLong", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (long) result;
    }
	public short asShort() {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__MetadataValue__"+this.name+"__asShort", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (short) result;
    }
	public byte asByte() {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__MetadataValue__"+this.name+"__asByte", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (byte) result;
    }
	public boolean asBoolean() {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__MetadataValue__"+this.name+"__asBoolean", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (boolean) result;
    }
	public java.lang.Object value() {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__MetadataValue__"+this.name+"__value", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (java.lang.Object) result;
    }
}