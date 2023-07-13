package net.ioixd.blackbox.extendables;

import net.ioixd.blackbox.BlackBox;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
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
            Misc.tryExecute(this.blackBox, this.inLibName, this.name, "MapRenderer", "render",
                    new Object[] { arg0, arg1, arg2 }, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(MapView arg0) {
        Object result = null;
        try {
            result = Misc.tryExecute(this.blackBox, this.inLibName, this.name, "MapRenderer", "initialize",
                    new Object[] { arg0 }, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (result == null) {
            super.initialize(arg0);
        }
    }

}