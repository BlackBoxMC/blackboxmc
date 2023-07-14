package net.ioixd.blackbox.extendables;

import java.util.List;
import java.util.Random;

import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

public class ExtendableChunkGenerator extends ChunkGenerator {

    String inLibName;
    String name;

    @Override
    public boolean canSpawn(World arg0, int arg1, int arg2) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator", "canSpawn",
                    new Object[] {
                            arg0, arg1, arg2
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.canSpawn(arg0, arg1, arg2);
        } else {
            return (boolean) result;
        }
    }

    @Override
    public void generateBedrock(WorldInfo arg0, Random arg1, int arg2, int arg3, ChunkData arg4) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator", "generateBedrock",
                    new Object[] {
                            arg0, arg1, arg2, arg3, arg4
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            super.generateBedrock(arg0, arg1, arg2, arg3, arg4);
        }
    }

    @Override
    public void generateCaves(WorldInfo arg0, Random arg1, int arg2, int arg3, ChunkData arg4) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator", "generateCaves",
                    new Object[] {
                            arg0, arg1, arg2, arg3, arg4
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            super.generateCaves(arg0, arg1, arg2, arg3, arg4);
        }
    }

    @Override
    public ChunkData generateChunkData(World arg1, Random arg2, int arg3, int arg4, BiomeGrid arg5) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator", "generateChunkData",
                    new Object[] {
                            arg1, arg2, arg3, arg4, arg5
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.generateChunkData(arg1, arg2, arg3, arg4, arg5);
        } else {
            return (ChunkData) result;
        }
    }

    @Override
    public void generateNoise(WorldInfo arg1, Random arg2, int arg3, int arg4, ChunkData arg5) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator", "generateNoise",
                    new Object[] {
                            arg1, arg2, arg3, arg4, arg5
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            super.generateNoise(arg1, arg2, arg3, arg4, arg5);
        }
    }

    @Override
    public void generateSurface(WorldInfo arg1, Random arg2, int arg3, int arg4, ChunkData arg5) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator", "generateSurface",
                    new Object[] {
                            arg1, arg2, arg3, arg4, arg5
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            super.generateSurface(arg1, arg2, arg3, arg4, arg5);
        }
    }

    @Override
    public int getBaseHeight(WorldInfo arg1, Random arg2, int arg3, int arg4, HeightMap arg5) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator", "getBaseHeight",
                    new Object[] {
                            arg1, arg2, arg3, arg4, arg5
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.getBaseHeight(arg1, arg2, arg3, arg4, arg5);
        } else {
            return (int) result;
        }
    }

    @Override
    public BiomeProvider getDefaultBiomeProvider(WorldInfo arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator",
                    "getDefaultBiomeProvider",
                    new Object[] {
                            arg1
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.getDefaultBiomeProvider(arg1);
        } else {
            return (BiomeProvider) result;
        }
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World arg1) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator", "getDefaultPopulators",
                    new Object[] {
                            arg1
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.getDefaultPopulators(arg1);
        } else {
            return (List<BlockPopulator>) result;
        }
    }

    @Override
    public Location getFixedSpawnLocation(World arg1, Random arg2) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator",
                    "getFixedSpawnLocation",
                    new Object[] {
                            arg1, arg2
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.getFixedSpawnLocation(arg1, arg2);
        } else {
            return (Location) result;
        }
    }

    @Override
    public boolean isParallelCapable() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator", "isParallelCapable",
                    new Object[] {}, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.isParallelCapable();
        } else {
            return (boolean) result;
        }
    }

    @Override
    public boolean shouldGenerateBedrock() {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator",
                    "shouldGenerateBedrock",
                    new Object[] {}, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.shouldGenerateBedrock();
        } else {
            return (boolean) result;
        }
    }

    @Override
    public boolean shouldGenerateCaves(WorldInfo arg1, Random arg2, int arg3, int arg4) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator", "shouldGenerateCaves",
                    new Object[] {
                            arg1, arg2, arg3, arg4
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.shouldGenerateCaves(arg1, arg2, arg3, arg4);
        } else {
            return (boolean) result;
        }
    }

    @Override
    public boolean shouldGenerateDecorations(WorldInfo arg0, Random arg1, int arg2, int arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator",
                    "shouldGenerateDecorations",
                    new Object[] {
                            arg0, arg1, arg2, arg3
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.shouldGenerateDecorations(arg0, arg1, arg2, arg3);
        } else {
            return (boolean) result;
        }
    }

    @Override
    public boolean shouldGenerateMobs(WorldInfo arg0, Random arg1, int arg2, int arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator", "shouldGenerateMobs",
                    new Object[] {
                            arg0, arg1, arg2, arg3
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.shouldGenerateMobs(arg0, arg1, arg2, arg3);
        } else {
            return (boolean) result;
        }
    }

    @Override
    public boolean shouldGenerateNoise(WorldInfo arg0, Random arg1, int arg2, int arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator", "shouldGenerateNoise",
                    new Object[] {
                            arg0, arg1, arg2, arg3
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.shouldGenerateNoise(arg0, arg1, arg2, arg3);
        } else {
            return (boolean) result;
        }
    }

    @Override
    public boolean shouldGenerateStructures(WorldInfo arg0, Random arg1, int arg2, int arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator",
                    "shouldGenerateStructures",
                    new Object[] {
                            arg0, arg1, arg2, arg3
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.shouldGenerateStructures(arg0, arg1, arg2, arg3);
        } else {
            return (boolean) result;
        }
    }

    @Override
    public boolean shouldGenerateSurface(WorldInfo arg0, Random arg1, int arg2, int arg3) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "ChunkGenerator", "runTaskTimer",
                    new Object[] {
                            arg0, arg1, arg2, arg3
                    }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            return super.shouldGenerateSurface(arg0, arg1, arg2, arg3);
        } else {
            return (boolean) result;
        }
    }

    ExtendableChunkGenerator(String name, String inLibName) {

        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }
}
