package net.ioixd.blackbox.extendables;

import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.plugin.Plugin;

public class ExtendableConversationPrefix implements ConversationPrefix {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;

    public ExtendableConversationPrefix(int address, Plugin plugin, String name, String inLibName) {

        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public java.lang.String getPrefix(org.bukkit.conversations.ConversationContext arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ConversationPrefix", "getPrefix",
                    address, plugin, new Object[] {
                            arg0
                    }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.String) result;
    }
}