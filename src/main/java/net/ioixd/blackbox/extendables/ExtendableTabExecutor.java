package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.Native;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;

public class ExtendableTabExecutor implements TabExecutor {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;

    public ExtendableTabExecutor(int address, Plugin plugin, String name, String inLibName) {
        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public java.util.List<java.lang.String> onTabComplete(org.bukkit.command.CommandSender arg0,
            org.bukkit.command.Command arg1, java.lang.String arg2, java.lang.String[] arg3) {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__TabExecutor__" + this.name + "__onTabComplete",
                    address, plugin, new Object[] {
                            arg0, arg1, arg2, arg3
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.List<java.lang.String>) result;
    }

    public boolean onCommand(org.bukkit.command.CommandSender arg0, org.bukkit.command.Command arg1,
            java.lang.String arg2, java.lang.String[] arg3) {
        Object result = null;
        try {
            result = Native.execute(this.inLibName, "__extends__TabExecutor__" + this.name + "__onCommand",
                    address, plugin, new Object[] {
                            arg0, arg1, arg2, arg3
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }
}