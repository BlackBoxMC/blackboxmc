package net.ioixd.blackbox;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.UnknownDependencyException;

public class FallbackPluginLoader implements Listener {
    BlackBoxPluginLoader pluginLoader;
    ArrayList<BlackBoxPlugin> plugins = new ArrayList<>();
    RegisteredListener registeredListener;

    FallbackPluginLoader(BlackBox plugin) {
        this.pluginLoader = new BlackBoxPluginLoader(plugin.getServer());

        for (File f : plugin.getDataFolder().getParentFile().listFiles()) {
            for (Pattern filter : BlackBoxPluginLoader.fileFilters) {
                Matcher match = filter.matcher(f.getAbsolutePath());
                if (!match.find()) {
                    continue;
                }
                try {
                    BlackBoxPlugin p = (BlackBoxPlugin) this.pluginLoader.loadPlugin(f);
                    if (p != null) {
                        plugins.add(p);
                    }
                } catch (InvalidPluginException e) {
                    e.printStackTrace();
                } catch (UnknownDependencyException e) {
                    e.printStackTrace();
                }
            }
        }
        this.registeredListener = new RegisteredListener(this, this::onEvent, EventPriority.NORMAL, plugin, false);
        for (HandlerList handler : HandlerList.getHandlerLists()) {
            handler.register(this.registeredListener);
        }
    }

    public void onEvent(Listener listener, Event event) {
        for (BlackBoxPlugin plugin : plugins) {
            plugin.listener.onEvent(listener, event);
        }
    }
}
