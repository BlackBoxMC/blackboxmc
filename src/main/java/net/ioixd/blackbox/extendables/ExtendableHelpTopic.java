package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.Plugin;
public class ExtendableHelpTopic extends HelpTopic {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableHelpTopic(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

	public boolean canSee(org.bukkit.command.CommandSender arg0) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__HelpTopic__"+this.name+"__canSee", this.blackBox, new Object[] {
               arg0
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (boolean) result;
    }
}