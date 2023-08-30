package net.ioixd.blackbox.extendables;

import java.util.Random;

import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.bukkit.plugin.Plugin;

public class ExtendableBlockPopulator extends BlockPopulator {
    String inLibName;
    String name;
    Plugin plugin;
    public int address;
    public boolean wasm;

    @Override
    public void populate(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, LimitedRegion limitedRegion) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.inLibName, this.name, "BlockPopulator", "populate",
                    address, plugin, new Object[] {
                            worldInfo, random, chunkX, chunkZ, limitedRegion
                    }, false, this.wasm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            super.populate(worldInfo, random, chunkX, chunkZ, limitedRegion);
        }
    }

    public ExtendableBlockPopulator(int address, Plugin plugin, String name, String inLibName, boolean wasm) {

        this.plugin = plugin;
        this.address = address;
        this.name = name;
        this.inLibName = inLibName;
        this.wasm = wasm;
    }
}
