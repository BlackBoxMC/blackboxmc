package net.ioixd.blackbox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;

import com.google.common.base.Charsets;

import net.ioixd.blackbox.extendables.*;

public class BlackBoxPlugin implements Plugin {
    String library;
    BlackBox parent;

    PluginLoader loader;

    private boolean isEnabled = false;
    private boolean naggable = true;

    private File file = null;
    private PluginDescriptionFile description = null;
    private File dataFolder = null;
    private FileConfiguration newConfig = null;
    private File configFile = null;

    public BlackBoxPluginListener listener;

    public class BlackBoxPluginListener implements Listener {
        BlackBoxPlugin plugin;
        RegisteredListener registeredListener;
        String library;

        public BlackBoxPluginListener(BlackBoxPlugin plugin, String library) {
            this.plugin = plugin;
            this.library = library;
            this.registeredListener = new RegisteredListener(this, this::onEvent, EventPriority.NORMAL, plugin, false);
            for (HandlerList handler : HandlerList.getHandlerLists()) {
                handler.unregister(this);
            }
            for (HandlerList handler : HandlerList.getHandlerLists()) {
                handler.register(this.registeredListener);
            }
        }

        public void onEvent(Listener listener, Event event) {
            // get the event name
            String name = event.getEventName();
            String hookName = "__on__" + name;
            try {
                boolean result = Native.libraryHasFunction(library, hookName);
                if (result == false) {
                    HandlerList handler = event.getHandlers();
                    handler.unregister(registeredListener);
                } else {
                    Native.sendEvent(library, hookName, event);
                }
            } catch (Exception ex) {
                this.plugin.getLogger().severe(ex.toString());
            }
        }
    }

    BlackBoxPlugin(String library, BlackBox parent, PluginLoader loader) {
        this.library = library;
        this.listener = new BlackBoxPluginListener(this, this.library);
        this.isEnabled = true;
        this.naggable = true;
        this.parent = parent;
        File pluginsFolder = parent.getDataFolder().getParentFile();
        if (!Paths.get(pluginsFolder.getPath(), this.library).toFile().exists()) {
            pluginsFolder.mkdir();
            Paths.get(pluginsFolder.getPath(), this.library).toFile().mkdir();
        }
        this.dataFolder = Paths.get(pluginsFolder.getPath(), this.library).toFile();
        this.loader = loader;
        this.file = new File(this.library);
    }

    public Object newExtendable(int address, String className, String name, String inLibName) throws Exception {
        return Misc.newExtendable(address, (Plugin) this, className, name, inLibName);
    }

    public void updateEventListeners() {
        this.listener.registeredListener = null;
        this.listener = null;
        this.listener = new BlackBoxPluginListener(this, this.library);
    }

    public String getInnerLibraryName() {
        return library;
    }

    @Override
    public String getName() {
        return library;
    }

    Object execNative(String functionName, Object[] objects) {
        try {
            boolean hasFunc = Native.libraryHasFunction(library, functionName);
            if (hasFunc) {
                return Native.execute(this.library, functionName, 0, this, objects);
            } else {
                // default
                return null;
            }
        } catch (Exception ex) {
            this.getLogger().severe(ex.toString());
            // this.getLogger().severe("Disabling self");
            // this.disable();
        }
        return null;
    }

    @Override
    public FileConfiguration getConfig() {
        Object func = execNative("getConfig", new Object[] { this, });
        if (func == null) {
            if (newConfig == null) {
                this.reloadConfig();
            }
            return newConfig;
        } else {
            return (FileConfiguration) func;
        }
    }

    @Override
    public BiomeProvider getDefaultBiomeProvider(String worldName, String id) {
        Object func = execNative("getDefaultBiomeProvider", new Object[] { this, worldName, id });
        if (func == null) {
            return parent.getDefaultBiomeProvider(worldName, id);
        } else {
            return (BiomeProvider) func;
        }
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        Object func = execNative("getDefaultWorldGenerator", new Object[] { this, worldName, id });
        if (func == null) {
            return parent.getDefaultWorldGenerator(worldName, id);
        } else {
            return (ChunkGenerator) func;
        }
    }

    @Override
    public Logger getLogger() {
        Object func = execNative("getLogger", new Object[] { this, });
        if (func == null) {
            return parent.getLogger();
        } else {
            return (Logger) func;
        }
    }

    @Override
    public InputStream getResource(String filename) {
        Object func = execNative("getResource", new Object[] { this, filename });
        if (func == null) {
            throw new UnsupportedOperationException(
                    "BlackBox plugins do not have embedded resources; the plugin author may choose to add an abstraction for them, but if you're seeing this then they don't. (\"Embedded resources\" refer to resources that would normally come from a .jar; they are not to be confused with anything that might embed a file into a library, you have to handle those yourself).");
        } else {
            return (InputStream) func;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Object func = execNative("onCommand", new Object[] { this, sender, command, label, args });
        if (func == null) {
            return false;
        } else {
            return (boolean) func;
        }
    }

    @Override
    public void onDisable() {
        // if it doesn't exist, nothing happens
        execNative("onDisable", new Object[] { this, });
    }

    @Override
    public void onEnable() {
        // if it doesn't exist, nothing happens
        execNative("onEnable", new Object[] { this, });
    }

    @Override
    public void onLoad() {
        // if it doesn't exist, nothing happens
        execNative("onLoad", new Object[] { this, });
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Object func = execNative("onTabComplete", new Object[] { this, sender, command, alias, args });
        if (func == null) {
            return null;
        } else {
            return (List<String>) func;
        }
    }

    @Override
    public void reloadConfig() {
        Object func = execNative("reloadConfig", new Object[] { this, });
        if (func == null) {
            newConfig = YamlConfiguration.loadConfiguration(configFile);

            final InputStream defConfigStream = getResource("config.yml");
            if (defConfigStream == null) {
                return;
            }

            newConfig.setDefaults(
                    YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        }
    }

    @Override
    public void saveConfig() {
        Object func = execNative("saveConfig", new Object[] { this, });
        if (func == null) {
            try {
                getConfig().save(configFile);
            } catch (IOException ex) {
                this.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
            }
        }
    }

    @Override
    public void saveDefaultConfig() {
        Object func = execNative("saveDefaultConfig", new Object[] { this, });
        if (func == null) {
            if (!configFile.exists()) {
                saveResource("config.yml", false);
            }
        }
    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {
        Object func = execNative("saveResource", new Object[] { this, });
        if (func == null) {
            if (resourcePath == null || resourcePath.equals("")) {
                throw new IllegalArgumentException("ResourcePath cannot be null or empty");
            }

            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = getResource(resourcePath);
            if (in == null) {
                throw new IllegalArgumentException(
                        "The embedded resource '" + resourcePath + "' cannot be found in " + file);
            }

            File outFile = new File(dataFolder, resourcePath);
            int lastIndex = resourcePath.lastIndexOf('/');
            File outDir = new File(dataFolder, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));

            if (!outDir.exists()) {
                outDir.mkdirs();
            }

            try {
                if (!outFile.exists() || replace) {
                    OutputStream out = new FileOutputStream(outFile);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    out.close();
                    in.close();
                } else {
                    this.getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile
                            + " because " + outFile.getName() + " already exists.");
                }
            } catch (IOException ex) {
                this.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
            }
        }
    }

    @Override
    public String toString() {
        Object func = execNative("toString", new Object[] { this, });
        if (func == null) {
            return description.getFullName();
        } else {
            return (String) func;
        }
    }

    @Override
    public File getDataFolder() {
        Object func = execNative("getDataFolder", new Object[] { this, });
        if (func == null) {
            return dataFolder;
        } else {
            return (File) func;
        }
    }

    @Override
    public PluginDescriptionFile getDescription() {
        Object func = execNative("getDescription", new Object[] { this, });
        if (func == null) {
            try {
                return loader.getPluginDescription(file);
            } catch (InvalidDescriptionException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return (PluginDescriptionFile) func;
        }
    }

    @Override
    public PluginLoader getPluginLoader() {
        Object func = execNative("getPluginLoader", new Object[] { this, });
        if (func == null) {
            return loader;
        } else {
            return (PluginLoader) func;
        }
    }

    @Override
    public Server getServer() {
        Object func = execNative("getServer", new Object[] { this, });
        if (func == null) {
            return parent.getServer();
        } else {
            return (Server) func;
        }
    }

    @Override
    public boolean isEnabled() {
        Object func = execNative("isEnabled", new Object[] { this, });
        if (func == null) {
            return isEnabled;
        } else {
            return (boolean) func;
        }
    }

    @Override
    public boolean isNaggable() {
        Object func = execNative("isNaggable", new Object[] { this, });
        if (func == null) {
            return naggable;
        } else {
            return (boolean) func;
        }
    }

    @Override
    public void setNaggable(boolean canNag) {
        Object func = execNative("setNaggable", new Object[] { this, });
        if (func == null) {
            naggable = canNag;
        }
    }

}
