package net.ioixd.blackbox.extendables;

import org.bukkit.conversations.ConversationPrefix;

public class ExtendableConversationPrefix implements ConversationPrefix {
    public String name;
    public String inLibName;

    ExtendableConversationPrefix(String name, String inLibName) {

        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public java.lang.String getPrefix(org.bukkit.conversations.ConversationContext arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ConversationPrefix", "getPrefix",
                    new Object[] {
                            arg0
                    }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.String) result;
    }
}