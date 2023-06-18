package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;
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
           result = Native.execute(this.inLibName, "__extends__ConfigurationSerializable__"+this.name+"__serialize", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (java.util.Map<java.lang.String, java.lang.Object>) result;
    }
}