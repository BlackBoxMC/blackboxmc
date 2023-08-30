package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.Native;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;

public class ExtendableTabExecutor implements TabExecutor {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;
    public boolean wasm;

    public ExtendableTabExecutor(int address, Plugin plugin, String name, String inLibName, boolean wasm) {
        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        this.wasm = wasm;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass(), this.wasm);
    }

    public java.util.List<java.lang.String> onTabComplete(org.bukkit.command.CommandSender arg0,
            org.bukkit.command.Command arg1, java.lang.String arg2, java.lang.String[] arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "TabExecutor", "onTabComplete",
                    address, plugin, new Object[] {
                            arg0, arg1, arg2, arg3
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.List<java.lang.String>) result;
    }

    public boolean onCommand(org.bukkit.command.CommandSender arg0, org.bukkit.command.Command arg1,
            java.lang.String arg2, java.lang.String[] arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "TabExecutor", "onCommand",
                    address, plugin, new Object[] {
                            arg0, arg1, arg2, arg3
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (boolean) result;
    }
}