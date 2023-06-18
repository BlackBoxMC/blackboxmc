package net.ioixd.blackbox;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public final class BlackBox extends JavaPlugin {
    @Override
    public void onEnable() {
        // Make sure everything has been loaded.
        ClassGraph clsgraph = new ClassGraph();
        clsgraph.acceptPackages("org.bukkit");
        clsgraph.initializeLoadedClasses();
        ClassInfoList info = clsgraph.scan().getSubclasses(Event.class.getName());
        info.forEach(cls -> {
            cls.loadClass();
        });

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
        File file = new File(
                getDataFolder().getAbsolutePath() +
                        FileSystems.getDefault().getSeparator() +
                        filename);
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

        this.getServer().getPluginManager().registerInterface(BlackBoxPluginLoader.class);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void disable() {
        this.setEnabled(false);
    }

}
