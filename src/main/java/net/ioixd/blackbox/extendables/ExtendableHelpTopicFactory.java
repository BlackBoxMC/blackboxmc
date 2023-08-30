package net.ioixd.blackbox.extendables;

import org.bukkit.help.HelpTopicFactory;
import org.bukkit.plugin.Plugin;

public class ExtendableHelpTopicFactory implements HelpTopicFactory {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;
    public boolean wasm;

    public ExtendableHelpTopicFactory(int address, Plugin plugin, String name, String inLibName, boolean wasm) {

        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        this.wasm = wasm;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass(), this.wasm);
    }

    public org.bukkit.help.HelpTopic createTopic(org.bukkit.command.Command arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "HelpTopicFactory", "createTopic",
                    address, plugin, new Object[] { arg0 }, true, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.help.HelpTopic) result;
    }

}