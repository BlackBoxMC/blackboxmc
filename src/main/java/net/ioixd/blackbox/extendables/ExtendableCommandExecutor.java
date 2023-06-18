package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
public class ExtendableCommandExecutor implements CommandExecutor {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableCommandExecutor(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

	public boolean onCommand(org.bukkit.command.CommandSender arg0, org.bukkit.command.Command arg1, java.lang.String arg2, java.lang.String[] arg3) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__CommandExecutor__"+this.name+"__onCommand", this.blackBox, new Object[] {
               arg0, arg1, arg2, arg3
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (boolean) result;
    }
}