package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.plugin.Plugin;
public class ExtendableConversationPrefix implements ConversationPrefix {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableConversationPrefix(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }


	public java.lang.String getPrefix(org.bukkit.conversations.ConversationContext arg0) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__ConversationPrefix__"+this.name+"__getPrefix", this.blackBox, new Object[] {
               arg0
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (java.lang.String) result;
    }
}