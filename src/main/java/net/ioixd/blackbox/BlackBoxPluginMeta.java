package net.ioixd.blackbox;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginLoadOrder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.papermc.paper.plugin.configuration.PluginMeta;

public class BlackBoxPluginMeta implements PluginMeta {
    String library;

    BlackBoxPluginMeta(String library) {
        this.library = library;
    }

    @Override
    public @Nullable String getAPIVersion() {
        return "0.0.0";
    }

    @Override
    public @NotNull List<String> getAuthors() {
        return new ArrayList<String>();
    }

    @Override
    public @NotNull List<String> getContributors() {
        return new ArrayList<String>();
    }

    @Override
    public @Nullable String getDescription() {
        return "A native library loaded by BlackBox. No other information is avaliable.";
    }

    @Override
    public @NotNull List<String> getLoadBeforePlugins() {
        return new ArrayList<String>();
    }

    @Override
    public @NotNull PluginLoadOrder getLoadOrder() {
        return PluginLoadOrder.STARTUP;
    }

    @Override
    public @Nullable String getLoggerPrefix() {
        return library;
    }

    @Override
    public @NotNull String getMainClass() {
        return null;
    }

    @Override
    public @NotNull String getName() {
        return library;
    }

    @Override
    public @NotNull PermissionDefault getPermissionDefault() {
        return null;
    }

    @Override
    public @NotNull List<Permission> getPermissions() {
        return new ArrayList<Permission>();
    }

    @Override
    public @NotNull List<String> getPluginDependencies() {
        return new ArrayList<String>();
    }

    @Override
    public @NotNull List<String> getPluginSoftDependencies() {
        return new ArrayList<String>();
    }

    @Override
    public @NotNull List<String> getProvidedPlugins() {
        return new ArrayList<String>();
    }

    @Override
    public @NotNull String getVersion() {
        return "0.0.0";
    }

    @Override
    public @Nullable String getWebsite() {
        return "https://lemmykoopa.com";
    }

}
