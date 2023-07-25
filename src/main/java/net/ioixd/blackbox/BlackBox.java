package net.ioixd.blackbox;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import net.ioixd.blackbox.extendables.ExtendableBiomeProvider;
import net.ioixd.blackbox.extendables.ExtendableBlockPopulator;
import net.ioixd.blackbox.extendables.ExtendableBukkitRunnable;
import net.ioixd.blackbox.extendables.ExtendableChunkGenerator;
import net.ioixd.blackbox.extendables.ExtendableCommandExecutor;
import net.ioixd.blackbox.extendables.ExtendableConfigurationSerializable;
import net.ioixd.blackbox.extendables.ExtendableConsumer;
import net.ioixd.blackbox.extendables.ExtendableConversationCanceller;
import net.ioixd.blackbox.extendables.ExtendableConversationPrefix;
import net.ioixd.blackbox.extendables.ExtendableHelpTopic;
import net.ioixd.blackbox.extendables.ExtendableHelpTopicFactory;
import net.ioixd.blackbox.extendables.ExtendableMapRenderer;
import net.ioixd.blackbox.extendables.ExtendableMetadataValue;
import net.ioixd.blackbox.extendables.ExtendableNoiseGenerator;
import net.ioixd.blackbox.extendables.ExtendablePersistentDataType;
import net.ioixd.blackbox.extendables.ExtendablePlugin;
import net.ioixd.blackbox.extendables.ExtendablePluginBase;
import net.ioixd.blackbox.extendables.ExtendablePluginLoader;
import net.ioixd.blackbox.extendables.ExtendableTabCompleter;
import net.ioixd.blackbox.extendables.ExtendableTabExecutor;
import net.ioixd.blackbox.extendables.Misc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BlackBox extends JavaPlugin {
    PluginManager pm = null;
    boolean isPaper = false;
    FallbackPluginLoader fallbackLoader = null;

    private ArrayList<BlackBoxPlugin> plugins = new ArrayList<>();

    @Override
    public void onLoad() {
        // extract the .dll or .so from the .jar into the plugins folder and run it.
        // i do not care if this is the wrong way. i have spent so fucking long trying
        // to do it
        // ""the proper way"" and nobody is gonna use this plugin so i don't give a fuck
        String filename = "";
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            filename = "native.dll";
        } else if (os.contains("linux")) {
            filename = "libnative.so";
        } else {
            getLogger().severe("Unsupported operating system \"" + os + "\". Stopping now.");
            getServer().shutdown();
        }

        File dir = new File(getDataFolder().getAbsolutePath());
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(Paths.get(getDataFolder().getAbsolutePath(), filename).toString());
        if (file.exists()) {
            file.delete();
        }

        InputStream link = (BlackBox.class.getResourceAsStream("/" + filename));
        try {
            Files.copy(link, file.getAbsoluteFile().toPath());
        } catch (IOException e) {
            getLogger().severe(e.toString());
            getLogger().severe("Disabling self");
            setEnabled(false);
        }

        System.load(file.getAbsolutePath());

        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            isPaper = true;
        } catch (ClassNotFoundException ignored) {
        }

        if (!isPaper) {
            pm = Bukkit.getServer().getPluginManager();
            pm.registerInterface(BlackBoxPluginLoader.class);

            for (File f : this.getFile().getParentFile().listFiles()) {
                for (Pattern filter : BlackBoxPluginLoader.fileFilters) {
                    Matcher match = filter.matcher(f.getAbsolutePath());
                    if (!match.find()) {
                        continue;
                    }
                    try {
                        BlackBoxPlugin p = (BlackBoxPlugin) pm.loadPlugin(f);
                        plugins.add(p);
                    } catch (InvalidPluginException e) {
                        e.printStackTrace();
                    } catch (InvalidDescriptionException e) {
                        e.printStackTrace();
                    } catch (UnknownDependencyException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onEnable() {
        // Make sure everything has been loaded.
        ClassGraph clsgraph = new ClassGraph();
        clsgraph.acceptPackages("org.bukkit");
        clsgraph.initializeLoadedClasses();
        ClassInfoList info = clsgraph.scan().getSubclasses(Event.class.getName());
        info.forEach(cls -> {
            System.out.println(cls.getName());
            cls.loadClass(true);
        });
        if (isPaper) {
            getLogger().warning(
                    "\nYou are running under Paper. By doing so, you are locked out of many advanced functions, and as of writing, you are restricted from making custom commands. More info here: https://github.com/BlackBoxMC/blackbox/wiki/Paper-incompatibility-notes\n\n");
            fallbackLoader = new FallbackPluginLoader(this);
        } else {
            for (BlackBoxPlugin p : plugins) {
                if (p == null) {
                    continue;
                }
                p.updateEventListeners();
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void disable() {
        this.setEnabled(false);
    }

    public Object newExtendable(int address, String className, String name, String inLibName) throws Exception {
        return Misc.newExtendable(address, (Plugin) this, className, name, inLibName);
    }

}
