package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.BlackBox;

import org.bukkit.command.CommandSender;
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

    @Override
    public void amendCanSee(String arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "HelpTopic", "amendCanSee",
                    new Object[] {
                            arg0
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            super.amendCanSee(arg0);
        }
    }

    @Override
    public void amendTopic(String arg0, String arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "HelpTopic", "amendTopic",
                    new Object[] {
                            arg0, arg1
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            super.amendTopic(arg0, arg1);
        }
    }

    @Override
    protected String applyAmendment(String arg0, String arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "HelpTopic", "applyAmendment",
                    new Object[] {
                            arg0, arg1
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.applyAmendment(arg0, arg1);
        } else {
            return (String) result;
        }
    }

    @Override
    public String getFullText(CommandSender arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "HelpTopic", "getFullText",
                    new Object[] {
                            arg0
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.getFullText(arg0);
        } else {
            return (String) result;
        }
    }

    @Override
    public String getName() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "HelpTopic", "getName",
                    new Object[] {}, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.getName();
        } else {
            return (String) result;
        }
    }

    @Override
    public String getShortText() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "HelpTopic", "getShortText",
                    new Object[] {}, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.getShortText();
        } else {
            return (String) result;
        }
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