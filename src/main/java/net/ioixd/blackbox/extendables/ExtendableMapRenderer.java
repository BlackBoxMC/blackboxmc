package net.ioixd.blackbox.extendables;
import net.ioixd.blackbox.BlackBox;
import net.ioixd.blackbox.Native;
import org.bukkit.map.MapRenderer;
import org.bukkit.plugin.Plugin;
public class ExtendableMapRenderer extends MapRenderer {
    public String name;
    public String inLibName;
    public BlackBox blackBox;

    ExtendableMapRenderer(Plugin blackBox, String name, String inLibName) {
        this.blackBox = (BlackBox) blackBox;
        this.name = name;
        this.inLibName = inLibName;
        Misc.throwIfFuncsNotBound(this.inLibName, this.name, this.getClass());
    }

	public void render(org.bukkit.map.MapView arg0, org.bukkit.map.MapCanvas arg1, org.bukkit.entity.Player arg2) {
       try {
           Native.execute(this.inLibName, "__extends__MapRenderer__"+this.name+"__render", this.blackBox, new Object[] {
               arg0, arg1, arg2
           });
       } catch(Exception ex) {
           ex.printStackTrace();
           this.blackBox.getLogger().severe("The fact that an error was thrown at this stage indicates a severe error. Assuming the plugin can no longer run safely, it is being shut off.");
           this.blackBox.disable();
       }
    }
}