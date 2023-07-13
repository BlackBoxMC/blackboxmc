package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.BlackBox;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.Plugin;

public class ExtendableHelpTopic extends HelpTopic {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableHelpTopic(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public boolean canSee(org.bukkit.command.CommandSender arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "HelpTopic", "canSee",
                    new Object[] { arg0 }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }
}