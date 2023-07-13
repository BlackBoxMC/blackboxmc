package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.BlackBox;
import org.bukkit.help.HelpTopicFactory;
import org.bukkit.plugin.Plugin;

public class ExtendableHelpTopicFactory implements HelpTopicFactory {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableHelpTopicFactory(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public org.bukkit.help.HelpTopic createTopic(org.bukkit.command.Command arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "HelpTopicFactory", "createTopic",
                    new Object[] { arg0 }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.help.HelpTopic) result;
    }

}