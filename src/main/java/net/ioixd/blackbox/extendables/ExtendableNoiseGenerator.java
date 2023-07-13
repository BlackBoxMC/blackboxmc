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

    @Override
    public double noise(double x) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "NoiseGenerator", "noise1d",
                    new Object[] { x }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.noise(x);
        } else {
            return (double) result;
        }
    }

    @Override
    public double noise(double x, double y) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "NoiseGenerator", "noise2d",
                    new Object[] { x, y }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.noise(x, y);
        } else {
            return (double) result;
        }
    }

    @Override
    public double noise(double x, int octaves, double frequency, double amplitude) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "NoiseGenerator", "noise1d_extra",
                    new Object[] { x, octaves, frequency, amplitude }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.noise(x, octaves, frequency, amplitude);
        } else {
            return (double) result;
        }
    }

    @Override
    public double noise(double x, int octaves, double frequency, double amplitude, boolean normalized) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "NoiseGenerator", "noise1d_extra_extra",
                    new Object[] { x, octaves, frequency, amplitude, normalized }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.noise(x, octaves, frequency, amplitude, normalized);
        } else {
            return (double) result;
        }
    }

    @Override
    public double noise(double x, double y, int octaves, double frequency, double amplitude) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "NoiseGenerator", "noise2d_extra",
                    new Object[] { x, y, octaves, frequency, amplitude }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.noise(x, y, octaves, frequency, amplitude);
        } else {
            return (double) result;
        }
    }

    @Override
    public double noise(double x, double y, int octaves, double frequency, double amplitude, boolean normalized) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "NoiseGenerator", "noise2d_extra_extra",
                    new Object[] { x, y, octaves, frequency, amplitude, normalized }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.noise(x, y, octaves, frequency, amplitude, normalized);
        } else {
            return (double) result;
        }
    }

    public double noise(double x, double y, double z) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "NoiseGenerator", "noise",
                    new Object[] {
                            x, y, z
                    }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (double) result;
    }

    @Override
    public double noise(double x, double y, double z, int octaves, double frequency, double amplitude) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "NoiseGenerator", "noise_extra",
                    new Object[] { x, y, z, octaves, frequency, amplitude }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.noise(x, y, z, octaves, frequency, amplitude);
        } else {
            return (double) result;
        }
    }

    @Override
    public double noise(double x, double y, double z, int octaves, double frequency, double amplitude,
            boolean normalized) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "NoiseGenerator", "noise_extra_extra",
                    new Object[] { x, y, z, octaves, frequency, amplitude }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.noise(x, y, z, octaves, frequency, amplitude);
        } else {
            return (double) result;
        }
    }

}