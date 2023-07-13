package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.BlackBox;

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
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "NoiseGenerator", "noise",
                    new Object[] {
                            arg0, arg1, arg2
                    }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (double) result;
    }
}