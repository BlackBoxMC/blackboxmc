package net.ioixd.blackbox.extendables;

import org.bukkit.util.Consumer;
import org.bukkit.plugin.Plugin;

public class ExtendableConsumer implements Consumer {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;
    public boolean wasm;

    public ExtendableConsumer(int address, Plugin plugin, String name, String inLibName, boolean wasm) {

        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        this.wasm = wasm;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass(), this.wasm);
    }

    public void accept(java.lang.Object arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Consumer", "accept",
                    address, plugin, new Object[] {
                            arg0
                    }, true, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}