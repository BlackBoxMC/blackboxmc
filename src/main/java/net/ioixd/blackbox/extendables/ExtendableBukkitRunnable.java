package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;

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
            Native.execute(this.inLibName, "__extends__BukkitRunnable__"+this.name+"__run", this.blackBox, new Object[] {});
        } catch(Exception ex) {
            ex.printStackTrace();
            this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
            this.blackBox.disable();
        }
    }
}