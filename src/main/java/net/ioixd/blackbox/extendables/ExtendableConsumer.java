package net.ioixd.blackbox.extendables;

import org.bukkit.util.Consumer;

public class ExtendableConsumer implements Consumer {
    public String name;
    public String inLibName;

    ExtendableConsumer(String name, String inLibName) {

        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public void accept(java.lang.Object arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "Consumer", "accept",
                    new Object[] {
                            arg0
                    }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}