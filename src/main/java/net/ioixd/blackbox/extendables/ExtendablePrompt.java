package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;
import org.bukkit.conversations.Prompt;
import org.bukkit.plugin.Plugin;
public class ExtendablePrompt implements Prompt {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendablePrompt(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

	public java.lang.String getPromptText(org.bukkit.conversations.ConversationContext arg0) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__Prompt__"+this.name+"__getPromptText", this.blackBox, new Object[] {
               arg0
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (java.lang.String) result;
    }
	public boolean blocksForInput(org.bukkit.conversations.ConversationContext arg0) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__Prompt__"+this.name+"__blocksForInput", this.blackBox, new Object[] {
               arg0
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (boolean) result;
    }
	public org.bukkit.conversations.Prompt acceptInput(org.bukkit.conversations.ConversationContext arg0, java.lang.String arg1) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__Prompt__"+this.name+"__acceptInput", this.blackBox, new Object[] {
               arg0, arg1
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (org.bukkit.conversations.Prompt) result;
    }
}