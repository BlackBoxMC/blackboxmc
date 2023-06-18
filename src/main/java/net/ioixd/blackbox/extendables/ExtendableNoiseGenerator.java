package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;

import org.bukkit.plugin.Plugin;
import org.bukkit.util.noise.NoiseGenerator;
public class ExtendableNoiseGenerator extends NoiseGenerator {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableNoiseGenerator(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

	public double noise(double arg0, double arg1, double arg2) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__NoiseGenerator__"+this.name+"__noise", this.blackBox, new Object[] {
               arg0, arg1, arg2
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (double) result;
    }
}