package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
public class ExtendablePluginLoader implements PluginLoader {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendablePluginLoader(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

	public org.bukkit.plugin.Plugin loadPlugin(java.io.File arg0) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__PluginLoader__"+this.name+"__loadPlugin", this.blackBox, new Object[] {
               arg0
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (org.bukkit.plugin.Plugin) result;
    }
	public org.bukkit.plugin.PluginDescriptionFile getPluginDescription(java.io.File arg0) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__PluginLoader__"+this.name+"__getPluginDescription", this.blackBox, new Object[] {
               arg0
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (org.bukkit.plugin.PluginDescriptionFile) result;
    }
	public java.util.regex.Pattern[] getPluginFileFilters() {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__PluginLoader__"+this.name+"__getPluginFileFilters", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (java.util.regex.Pattern[]) result;
    }
	public java.util.Map<java.lang.Class<? extends org.bukkit.event.Event>, java.util.Set<org.bukkit.plugin.RegisteredListener>> createRegisteredListeners(org.bukkit.event.Listener arg0, org.bukkit.plugin.Plugin arg1) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__PluginLoader__"+this.name+"__createRegisteredListeners", this.blackBox, new Object[] {
               arg0, arg1
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (java.util.Map<java.lang.Class<? extends org.bukkit.event.Event>, java.util.Set<org.bukkit.plugin.RegisteredListener>>) result;
    }
	public void enablePlugin(org.bukkit.plugin.Plugin arg0) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__PluginLoader__"+this.name+"__enablePlugin", this.blackBox, new Object[] {
               arg0
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
    }
	public void disablePlugin(org.bukkit.plugin.Plugin arg0) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__PluginLoader__"+this.name+"__disablePlugin", this.blackBox, new Object[] {
               arg0
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
    }
}