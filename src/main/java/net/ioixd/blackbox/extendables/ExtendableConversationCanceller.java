package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.BlackBox;
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
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "ConversationCanceller",
                    "setConversation",
                    new Object[] {
                            arg0
                    }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean cancelBasedOnInput(org.bukkit.conversations.ConversationContext arg0, java.lang.String arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "ConversationCanceller",
                    "cancelBasedOnInput",
                    new Object[] {
                            arg0, arg1
                    }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }

    public org.bukkit.conversations.ConversationCanceller clone() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "ConversationCanceller", "clone",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.conversations.ConversationCanceller) result;
    }
}