package net.ioixd.blackbox.extendables;

import org.bukkit.command.CommandExecutor;

public class ExtendableCommandExecutor implements CommandExecutor {
    public String name;
    public String inLibName;

    ExtendableCommandExecutor(String name, String inLibName) {

        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public boolean onCommand(org.bukkit.command.CommandSender arg0, org.bukkit.command.Command arg1,
            java.lang.String arg2, java.lang.String[] arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "CommandExecutor", "onCommand",
                    new Object[] {
                            arg0, arg1, arg2, arg3
                    }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }

}