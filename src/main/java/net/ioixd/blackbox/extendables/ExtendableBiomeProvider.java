package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.exceptions.MissingFunctionException;

import org.bukkit.generator.BiomeProvider;
import org.bukkit.plugin.Plugin;

public class ExtendableBiomeProvider extends BiomeProvider {
    public String name;
    public String inLibName;
    public Plugin plugin;
    public int address;

    public ExtendableBiomeProvider(int address, Plugin plugin, String name, String inLibName)
            throws MissingFunctionException {
        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public org.bukkit.block.Biome getBiome(org.bukkit.generator.WorldInfo arg0, int arg1, int arg2, int arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "BiomeProvider", "getBiome",
                    address, plugin, new Object[] {
                            arg0, arg1, arg2, arg3
                    }, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.block.Biome) result;
    }

    public java.util.List<org.bukkit.block.Biome> getBiomes(org.bukkit.generator.WorldInfo arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "BiomeProvider", "getBiomes",
                    address, plugin, new Object[] {
                            arg0
                    }, true, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.List<org.bukkit.block.Biome>) result;
    }
}