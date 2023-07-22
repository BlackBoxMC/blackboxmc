package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.Native;
import org.bukkit.conversations.Prompt;
import org.bukkit.plugin.Plugin;

public class ExtendablePrompt implements Prompt {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;

    ExtendablePrompt(Plugin plugin, String name, String inLibName) {
        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public java.lang.String getPromptText(org.bukkit.conversations.ConversationContext arg0) {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__Prompt__" + this.name + "__getPromptText",
                    address, plugin, new Object[] {
                            arg0
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.String) result;
    }

    public boolean blocksForInput(org.bukkit.conversations.ConversationContext arg0) {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__Prompt__" + this.name + "__blocksForInput",
                    address, plugin, new Object[] {
                            arg0
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }

    public org.bukkit.conversations.Prompt acceptInput(org.bukkit.conversations.ConversationContext arg0,
            java.lang.String arg1) {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__Prompt__" + this.name + "__acceptInput",
                    address, plugin, new Object[] {
                            arg0, arg1
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.conversations.Prompt) result;
    }
}