package net.ioixd.blackbox.extendables;

import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.plugin.Plugin;

public class ExtendableNoiseGenerator extends NoiseGenerator {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;

    public ExtendableNoiseGenerator(int address, Plugin plugin, String name, String inLibName) {

        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    @Override
    public double noise(double x) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "NoiseGenerator", "noise1d",
                    address, plugin, new Object[] { x }, false, true);
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
            result = Misc.tryExecute(this.inLibName, this.name, "NoiseGenerator", "noise2d",
                    address, plugin, new Object[] { x, y }, false, true);
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
            result = Misc.tryExecute(this.inLibName, this.name, "NoiseGenerator", "noise1d_extra",
                    address, plugin, new Object[] { x, octaves, frequency, amplitude }, false, true);
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
            result = Misc.tryExecute(this.inLibName, this.name, "NoiseGenerator", "noise1d_extra_extra",
                    address, plugin, new Object[] { x, octaves, frequency, amplitude, normalized }, false, true);
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
            result = Misc.tryExecute(this.inLibName, this.name, "NoiseGenerator", "noise2d_extra",
                    address, plugin, new Object[] { x, y, octaves, frequency, amplitude }, false, true);
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
            result = Misc.tryExecute(this.inLibName, this.name, "NoiseGenerator", "noise2d_extra_extra",
                    address, plugin, new Object[] { x, y, octaves, frequency, amplitude, normalized }, false, true);
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
            result = Misc.tryExecute(this.inLibName, this.name, "NoiseGenerator", "noise",
                    address, plugin, new Object[] {
                            x, y, z
                    }, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (double) result;
    }

    @Override
    public double noise(double x, double y, double z, int octaves, double frequency, double amplitude) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "NoiseGenerator", "noise_extra",
                    address, plugin, new Object[] { x, y, z, octaves, frequency, amplitude }, false, true);
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
            result = Misc.tryExecute(this.inLibName, this.name, "NoiseGenerator", "noise_extra_extra",
                    address, plugin, new Object[] { x, y, z, octaves, frequency, amplitude }, false, true);
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