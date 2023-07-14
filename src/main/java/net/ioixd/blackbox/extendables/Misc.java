package net.ioixd.blackbox.extendables;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import net.ioixd.blackbox.Native;
import net.ioixd.blackbox.exceptions.MissingFunctionException;

public class Misc {
    public static void throwIfFuncsNotBound(String libName, String className, Class<?> extendsClass)
            throws MissingFunctionException {
        String extendsName = extendsClass.getName().replace("Extendable", "");
        Method[] methods = extendsClass.getMethods();
        for (Method method : methods) {
            if ((method.getModifiers() & Modifier.ABSTRACT) != Modifier.ABSTRACT) {
                continue;
            }
            if (!Native.libraryHasFunction(libName,
                    "__extends__" + extendsName + "__" + className + "__" + method.getName())) {
                throw new MissingFunctionException(libName + " does not have a function titled __extends__"
                        + extendsName + "__" + className + "__" + method.getName());
            }
        }
    }

    public static Object tryExecute(String libName, String className, String extendsName,
            String funcName,
            Object[] objects, boolean mustExecute) throws Exception {
        String fullFuncName = "__extends__" + extendsName + "__" + className + "__" + funcName;
        boolean has = Native.libraryHasFunction(libName, fullFuncName);
        if (has == false) {
            if (mustExecute) {
                throw new MissingFunctionException(libName + " does not have a function titled " + fullFuncName);
            } else {
                return null;
            }
        } else {
            return Native.execute(libName, fullFuncName, objects);
        }
    }
}
