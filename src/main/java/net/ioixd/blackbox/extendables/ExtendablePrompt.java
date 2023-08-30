package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.Native;
import org.bukkit.conversations.Prompt;
import org.bukkit.plugin.Plugin;

public class ExtendablePrompt implements Prompt {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;
    public boolean wasm;

    ExtendablePrompt(Plugin plugin, String name, String inLibName, boolean wasm) {
        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        this.wasm = wasm;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass(), this.wasm);
    }

    public java.lang.String getPromptText(org.bukkit.conversations.ConversationContext arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Prompt", "getPromptText",
                    address, plugin, new Object[] {
                            arg0
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.lang.String) result;
    }

    public boolean blocksForInput(org.bukkit.conversations.ConversationContext arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Prompt", "blocksForInput",
                    address, plugin, new Object[] {
                            arg0
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }

    public org.bukkit.conversations.Prompt acceptInput(org.bukkit.conversations.ConversationContext arg0,
            java.lang.String arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Prompt", "acceptInput",
                    address, plugin, new Object[] {
                            arg0, arg1
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.conversations.Prompt) result;
    }
}