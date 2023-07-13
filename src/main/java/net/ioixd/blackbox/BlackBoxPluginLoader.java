package net.ioixd.blackbox;

import com.google.common.base.Preconditions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.RegisteredListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a BlackBox plugin loader, allowing plugins in the form of .dll and
 * .so
 */
public final class BlackBoxPluginLoader implements PluginLoader {
    final Server server;
    private HashMap<Plugin, BlackBoxPlugin> libNameMap = new HashMap<>();

    public static final Pattern[] fileFilters = new Pattern[] { Pattern.compile("^(.*)\\" + getFileExtension() + "$") };

    public BlackBoxPluginLoader(@NotNull Server instance) {
        Preconditions.checkArgument(instance != null, "Server cannot be null");
        server = instance;
        server.getLogger().info("BlackBoxPluginLoader initialized");
    }

    @Override
    @NotNull
    public Plugin loadPlugin(@NotNull final File file) throws InvalidPluginException {
        String library = file.getAbsolutePath();

        String[] parts = library.split(File.separator);
        String libraryName = parts[parts.length - 1].replace(BlackBoxPluginLoader.getFileExtension(), "").replace("-",
                "_");
        ;

        Preconditions.checkArgument(file != null, "File cannot be null");

        if (!file.exists()) {
            server.getLogger().severe(file.getAbsolutePath() + " does not exist");
            return null;
        }

        Native.loadPlugin(library);

        server.getLogger().info("Loading native plugin " + libraryName);

        BlackBoxPlugin plugin = new BlackBoxPlugin(libraryName,
                (BlackBox) server.getPluginManager().getPlugin("BlackBox"), this);
        libNameMap.put(plugin, plugin);
        return plugin;
    }

    @Override
    public void enablePlugin(@NotNull final Plugin plugin) {
        if (libNameMap.containsKey(plugin)) {
            Native.enablePlugin(libNameMap.get(plugin).getInnerLibraryName());
        } else {
            server.getLogger().severe("BlackBoxPluginLoader tried to enable plugin that isn't registered in it.");
        }
    }

    @Override
    public void disablePlugin(@NotNull Plugin plugin) {
        if (libNameMap.containsKey(plugin)) {
            Native.disablePlugin(libNameMap.get(plugin).getInnerLibraryName());
        } else {
            server.getLogger().severe("BlackBoxPluginLoader tried to disable plugin that isn't registered in it.");
        }
    }

    @Override
    @NotNull
    public PluginDescriptionFile getPluginDescription(@NotNull File file) {
        if (file == null) {
            this.server.getLogger().severe("null passed to getPluginDescription");
            return null;
        }
        String str = "name: " + file.getName().replace(getFileExtension(), "") + "\r\n" + //
                "version: '0.0.0'\r\n" + //
                "main: net.ioixd.blackbox.BlackBox\r\n" + //
                "api-version: 1.13\r\n";
        try {
            return new PluginDescriptionFile(new ByteArrayInputStream(str.getBytes()));
        } catch (InvalidDescriptionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @NotNull
    public Pattern[] getPluginFileFilters() {
        return fileFilters;
    }

    public static String getFileExtension() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return ".so";
        }
        if (os.contains("mac")) {
            return ".dylib";
        }
        if (os.contains("linux") || os.contains("nix")) {
            return ".so";
        }
        throw new UnsupportedOperationException("Unknown OS \"" + os + "\"");
    }

    @Override
    @NotNull
    public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(@NotNull Listener listener,
            @NotNull final Plugin plugin) {
        Preconditions.checkArgument(plugin != null, "Plugin can not be null");
        Preconditions.checkArgument(listener != null, "Listener can not be null");

        boolean useTimings = server.getPluginManager().useTimings();

        Map<Class<? extends Event>, Set<RegisteredListener>> ret = new HashMap<Class<? extends Event>, Set<RegisteredListener>>();

        Set<Method> methods;
        try {
            Method[] publicMethods = listener.getClass().getMethods();
            Method[] privateMethods = listener.getClass().getDeclaredMethods();
            methods = new HashSet<Method>(publicMethods.length + privateMethods.length, 1.0f);
            for (Method method : publicMethods) {
                methods.add(method);
            }
            for (Method method : privateMethods) {
                methods.add(method);
            }
        } catch (NoClassDefFoundError e) {
            plugin.getLogger()
                    .severe("Plugin " + plugin.getDescription().getFullName() + " has failed to register events for "
                            + listener.getClass() + " because " + e.getMessage() + " does not exist.");
            return ret;
        }

        for (final Method method : methods) {
            final EventHandler eh = method.getAnnotation(EventHandler.class);
            if (eh == null)
                continue;
            // Do not register bridge or synthetic methods to avoid event duplication
            // Fixes SPIGOT-893
            if (method.isBridge() || method.isSynthetic()) {
                continue;
            }
            final Class<?> checkClass;
            if (method.getParameterTypes().length != 1
                    || !Event.class.isAssignableFrom(checkClass = method.getParameterTypes()[0])) {
                plugin.getLogger()
                        .severe(plugin.getDescription().getFullName()
                                + " attempted to register an invalid EventHandler method signature \""
                                + method.toGenericString() + "\" in " + listener.getClass());
                continue;
            }
            final Class<? extends Event> eventClass = checkClass.asSubclass(Event.class);
            method.setAccessible(true);
            Set<RegisteredListener> eventSet = ret.get(eventClass);
            if (eventSet == null) {
                eventSet = new HashSet<RegisteredListener>();
                ret.put(eventClass, eventSet);
            }

            if (libNameMap.get(plugin) == null) {
                server.getLogger().severe(
                        "BlackBoxPluginLoader tried to create listeners for a plugin that isn't registered in it.");
                continue;
            }
            ;

        }
        return ret;

    }
}