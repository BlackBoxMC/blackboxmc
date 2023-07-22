package net.ioixd.blackbox.extendables;

import org.bukkit.conversations.ConversationCanceller;
import org.bukkit.plugin.Plugin;

public class ExtendableConversationCanceller implements ConversationCanceller {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;

    public ExtendableConversationCanceller(int address, Plugin plugin, String name, String inLibName) {

        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public void setConversation(org.bukkit.conversations.Conversation arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ConversationCanceller",
                    "setConversation",
                    address, plugin, new Object[] {
                            arg0
                    }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean cancelBasedOnInput(org.bukkit.conversations.ConversationContext arg0, java.lang.String arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ConversationCanceller",
                    "cancelBasedOnInput",
                    address, plugin, new Object[] {
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
            result = Misc.tryExecute(this.inLibName, this.name, "ConversationCanceller", "clone",
                    address, plugin, new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.conversations.ConversationCanceller) result;
    }
}