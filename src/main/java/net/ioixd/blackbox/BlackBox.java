package net.ioixd.blackbox;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;

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

    private ArrayList<BlackBoxPlugin> plugins = new ArrayList<>();

    @Override
    public void onLoad() {
        pm = Bukkit.getServer().getPluginManager();
        pm.registerInterface(BlackBoxPluginLoader.class);

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

        for (File f : this.getFile().getParentFile().listFiles()) {
            for (Pattern filter : BlackBoxPluginLoader.fileFilters) {
                Matcher match = filter.matcher(f.getAbsolutePath());
                if (!match.find()) {
                    continue;
                }
                try {
                    BlackBoxPlugin p = (BlackBoxPlugin) pm.loadPlugin(f);
                    if (p != null) {
                        plugins.add(p);
                    }
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

    @Override
    public void onEnable() {
        // Make sure everything has been loaded.
        ClassGraph clsgraph = new ClassGraph();
        clsgraph.acceptPackages("org.bukkit");
        clsgraph.initializeLoadedClasses();
        ClassInfoList info = clsgraph.scan().getSubclasses(Event.class.getName());
        info.forEach(cls -> {
            cls.loadClass(true);
        });
        for (BlackBoxPlugin p : plugins) {
            if (p == null) {
                continue;
            }
            p.updateEventListeners();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void disable() {
        this.setEnabled(false);
    }

}
