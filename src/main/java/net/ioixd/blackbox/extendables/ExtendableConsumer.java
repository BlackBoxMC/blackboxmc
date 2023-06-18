package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;

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
       try {
           Native.execute(this.inLibName, "__extends__Consumer__"+this.name+"__accept", this.blackBox, new Object[] {
               arg0
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
    }
}