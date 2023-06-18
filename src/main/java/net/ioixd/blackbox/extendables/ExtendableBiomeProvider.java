package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;

import org.bukkit.generator.BiomeProvider;
import org.bukkit.plugin.Plugin;

import net.ioixd.blackbox.exceptions.MissingFunctionException;


public class ExtendableBiomeProvider extends BiomeProvider {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableBiomeProvider(Plugin blackBox, String name, String inLibName) throws MissingFunctionException {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

	public org.bukkit.block.Biome getBiome(org.bukkit.generator.WorldInfo arg0, int arg1, int arg2, int arg3) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__BiomeProvider__"+this.name+"__getBiome", this.blackBox, new Object[] {
               arg0, arg1, arg2, arg3
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (org.bukkit.block.Biome) result;
    }
	public java.util.List<org.bukkit.block.Biome> getBiomes(org.bukkit.generator.WorldInfo arg0) {
       Object result = null;
       try {
           result = Native.execute(this.inLibName, "__extends__BiomeProvider__"+this.name+"__getBiomes", this.blackBox, new Object[] {
               arg0
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
       return (java.util.List<org.bukkit.block.Biome>) result;
    }
}