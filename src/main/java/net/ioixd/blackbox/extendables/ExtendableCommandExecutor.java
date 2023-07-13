package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.BlackBox;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;

public class ExtendableCommandExecutor implements CommandExecutor {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableCommandExecutor(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public boolean onCommand(org.bukkit.command.CommandSender arg0, org.bukkit.command.Command arg1,
            java.lang.String arg2, java.lang.String[] arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "CommandExecutor", "onCommand",
                    new Object[] {
                            arg0, arg1, arg2, arg3
                    }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }

}