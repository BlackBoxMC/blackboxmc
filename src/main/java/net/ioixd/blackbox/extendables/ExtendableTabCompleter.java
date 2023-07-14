package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.Native;
import org.bukkit.command.TabCompleter;

public class ExtendableTabCompleter implements TabCompleter {
    public String name;
    public String inLibName;

    ExtendableTabCompleter(String name, String inLibName) {
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public java.util.List<java.lang.String> onTabComplete(org.bukkit.command.CommandSender arg0,
            org.bukkit.command.Command arg1, java.lang.String arg2, java.lang.String[] arg3) {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__TabCompleter__" + this.name + "__onTabComplete",
                    new Object[] {
                            arg0, arg1, arg2, arg3
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.List<java.lang.String>) result;
    }
}