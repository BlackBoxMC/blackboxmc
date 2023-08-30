package net.ioixd.blackbox.extendables;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import net.ioixd.blackbox.Native;
import net.ioixd.blackbox.exceptions.MissingFunctionException;

public class Misc {
    public static void throwIfFuncsNotBound(String libName, String className, Class<?> extendsClass, boolean wasm)
            throws MissingFunctionException {
        String extendsName = extendsClass.getName().replace("Extendable", "");
        Method[] methods = extendsClass.getMethods();
        for (Method method : methods) {
            if ((method.getModifiers() & Modifier.ABSTRACT) != Modifier.ABSTRACT) {
                continue;
            }
            if (!Native.libraryHasFunction(libName,
                    "__extends__" + extendsName + "__" + className + "__" + method.getName(), wasm)) {
                throw new MissingFunctionException(libName + " does not have a function titled __extends__"
                        + extendsName + "__" + className + "__" + method.getName());
            }
        }
    }

    public static Object tryExecute(String libName, String className, String extendsName,
            String funcName,
            int address, Plugin plugin, Object[] objects, boolean mustExecute, boolean wasm) throws Exception {
        String fullFuncName = "__extends__" + extendsName + "__" + className + "__" + funcName;
        boolean has = Native.libraryHasFunction(libName, fullFuncName, wasm);
        if (has == false) {
            if (mustExecute) {
                throw new MissingFunctionException(libName + " does not have a function titled " + fullFuncName);
            } else {
                return null;
            }
        } else {
            return Native.execute(libName, fullFuncName, address, plugin, objects, wasm);
        }
    }

    public static Object newExtendable(int address, Plugin plugin, String className, String name, String inLibName,
            boolean wasm)
            throws Exception {
        switch (className) {
            case "BiomeProvider":
                return (Object) new ExtendableBiomeProvider(address, plugin, name, inLibName, wasm);
            case "BlockPopulator":
                return (Object) new ExtendableBlockPopulator(address, plugin, name, inLibName, wasm);
            case "BukkitRunnable":
                return (Object) new ExtendableBukkitRunnable(address, plugin, name, inLibName, wasm);
            case "ChunkGenerator":
                return (Object) new ExtendableChunkGenerator(address, plugin, name, inLibName, wasm);
            case "CommandExecutor":
                return (Object) new ExtendableCommandExecutor(address, plugin, name, inLibName, wasm);
            case "ConfigurationSerializable":
                return (Object) new ExtendableConfigurationSerializable(address, plugin, name, inLibName, wasm);
            case "Consumer":
                return (Object) new ExtendableConsumer(address, plugin, name, inLibName, wasm);
            case "ConversationCanceller":
                return (Object) new ExtendableConversationCanceller(address, plugin, name, inLibName, wasm);
            case "ConversationPrefix":
                return (Object) new ExtendableConversationPrefix(address, plugin, name, inLibName, wasm);
            case "HelpTopic":
                return (Object) new ExtendableHelpTopic(address, plugin, name, inLibName, wasm);
            case "HelpTopicFactory":
                return (Object) new ExtendableHelpTopicFactory(address, plugin, name, inLibName, wasm);
            case "MapRenderer":
                return (Object) new ExtendableMapRenderer(address, plugin, name, inLibName, wasm);
            case "MetadataValue":
                return (Object) new ExtendableMetadataValue(address, plugin, name, inLibName, wasm);
            case "NoiseGenerator":
                return (Object) new ExtendableNoiseGenerator(address, plugin, name, inLibName, wasm);
            case "PersistentDataType":
                return (Object) new ExtendablePersistentDataType(address, plugin, name, inLibName, wasm);
            case "Plugin":
                return (Object) new ExtendablePlugin(address, plugin, name, inLibName, wasm);
            case "PluginBase":
                return (Object) new ExtendablePluginBase(address, plugin, name, inLibName, wasm);
            case "PluginLoader":
                return (Object) new ExtendablePluginLoader(address, plugin, name, inLibName, wasm);
            case "TabCompleter":
                return (Object) new ExtendableTabCompleter(address, plugin, name, inLibName, wasm);
            case "TabExecutor":
                return (Object) new ExtendableTabExecutor(address, plugin, name, inLibName, wasm);
            default:
                throw new Exception("Non-extendable object given");
        }
    }
}
