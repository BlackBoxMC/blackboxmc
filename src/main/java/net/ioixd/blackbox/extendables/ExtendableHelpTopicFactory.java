package net.ioixd.blackbox.extendables;

import org.bukkit.help.HelpTopicFactory;

public class ExtendableHelpTopicFactory implements HelpTopicFactory {
    public String name;
    public String inLibName;

    ExtendableHelpTopicFactory(String name, String inLibName) {

        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public org.bukkit.help.HelpTopic createTopic(org.bukkit.command.Command arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "HelpTopicFactory", "createTopic",
                    new Object[] { arg0 }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.help.HelpTopic) result;
    }

}