package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.BlackBox;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ExtendableBukkitRunnable extends BukkitRunnable {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableBukkitRunnable(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public void run() {
        try {
            Misc.tryExecute(this.blackBox, this.inLibName, this.name, "BukkitRunnable", "run",
                    new Object[] {}, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}