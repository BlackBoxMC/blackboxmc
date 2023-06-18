package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;
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
           result = Native.execute(this.inLibName, "__extends__FileConfiguration__"+this.name+"__saveToString", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (java.lang.String) result;
    }
	public void loadFromString(java.lang.String arg0) {
       try {
           Native.execute(this.inLibName, "__extends__FileConfiguration__"+this.name+"__loadFromString", this.blackBox, new Object[] {
               arg0
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
    }
}