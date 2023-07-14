package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.Native;
import org.bukkit.conversations.Prompt;

public class ExtendablePrompt implements Prompt {
    public String name;
    public String inLibName;

    ExtendablePrompt(String name, String inLibName) {
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public java.lang.String getPromptText(org.bukkit.conversations.ConversationContext arg0) {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__Prompt__" + this.name + "__getPromptText",
                    new Object[] {
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
                    new Object[] {
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
            result = Native.execute(this.inLibName, "__extends__Prompt__" + this.name + "__acceptInput", new Object[] {
                    arg0, arg1
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.conversations.Prompt) result;
    }
}