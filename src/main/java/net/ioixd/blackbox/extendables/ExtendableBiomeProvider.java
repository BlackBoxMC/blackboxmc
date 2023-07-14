package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.exceptions.MissingFunctionException;

import org.bukkit.generator.BiomeProvider;

public class ExtendableBiomeProvider extends BiomeProvider {
    public String name;
    public String inLibName;

    ExtendableBiomeProvider(String name, String inLibName) throws MissingFunctionException {

        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

    public org.bukkit.block.Biome getBiome(org.bukkit.generator.WorldInfo arg0, int arg1, int arg2, int arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "BiomeProvider", "getBiome",
                    new Object[] {
                            arg0, arg1, arg2, arg3
                    }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (org.bukkit.block.Biome) result;
    }

    public java.util.List<org.bukkit.block.Biome> getBiomes(org.bukkit.generator.WorldInfo arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "BiomeProvider", "getBiomes",
                    new Object[] {
                            arg0
                    }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (java.util.List<org.bukkit.block.Biome>) result;
    }
}