package net.ioixd.blackbox.extendables;

import org.bukkit.util.Consumer;
import org.bukkit.plugin.Plugin;

public class ExtendableConsumer implements Consumer {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;

    public ExtendableConsumer(int address, Plugin plugin, String name, String inLibName) {

        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public void accept(java.lang.Object arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Consumer", "accept",
                    address, plugin, new Object[] {
                            arg0
                    }, true, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}