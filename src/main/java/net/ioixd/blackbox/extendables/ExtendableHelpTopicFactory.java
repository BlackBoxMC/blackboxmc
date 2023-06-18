package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;
import org.bukkit.help.HelpTopicFactory;
import org.bukkit.plugin.Plugin;
public class ExtendableHelpTopicFactory implements HelpTopicFactory {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableHelpTopicFactory(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

	public org.bukkit.help.HelpTopic createTopic(org.bukkit.command.Command arg0) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__HelpTopicFactory__"+this.name+"__createTopic", this.blackBox, new Object[] {
               arg0
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (org.bukkit.help.HelpTopic) result;
    }
}