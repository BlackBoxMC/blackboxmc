package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;
import org.bukkit.conversations.ConversationCanceller;
import org.bukkit.plugin.Plugin;
public class ExtendableConversationCanceller implements ConversationCanceller {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableConversationCanceller(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

	public void setConversation(org.bukkit.conversations.Conversation arg0) {
       try {
           Native.execute(this.inLibName, "__extends__ConversationCanceller__"+this.name+"__setConversation", this.blackBox, new Object[] {
               arg0
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
    }
	public boolean cancelBasedOnInput(org.bukkit.conversations.ConversationContext arg0, java.lang.String arg1) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__ConversationCanceller__"+this.name+"__cancelBasedOnInput", this.blackBox, new Object[] {
               arg0, arg1
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (boolean) result;
    }
	public org.bukkit.conversations.ConversationCanceller clone() {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__ConversationCanceller__"+this.name+"__clone", this.blackBox, new Object[] {});
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (org.bukkit.conversations.ConversationCanceller) result;
    }
}