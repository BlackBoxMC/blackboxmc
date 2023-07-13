package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.BlackBox;

import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;

public class ExtendableConsumer implements Consumer {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableConsumer(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public void accept(java.lang.Object arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "Consumer", "accept",
                    new Object[] {
                            arg0
                    }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}