package net.crunkle.command.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

/**
 * This utility class is used in order to fetch
 * the {@link CommandMap} object from where it is
 * declared.
 *
 * @author Crunkle
 */
public class ReflectionUtil {
    private static CommandMap commandMap;

    /**
     * @return the instance of the {@link CommandMap}
     */
    public static CommandMap getCommandMap() {
        if (ReflectionUtil.commandMap == null) {
            ReflectionUtil.commandMap = ReflectionUtil.getField(Bukkit.getPluginManager(), "commandMap");
        }

        return ReflectionUtil.commandMap;
    }

    /**
     * Searches through an object, and each superclass, for a
     * specified field and returns the field if found.
     *
     * @param object    the object to fetch the field from
     * @param fieldName the name of the field to fetch
     * @param <T>       the object type of the field
     * @return the field defined in the object
     */
    private static <T> T getField(Object object, String fieldName) {
        Class<?> currentClass = object.getClass();

        do {
            try {
                Field field = currentClass.getDeclaredField(fieldName);

                field.setAccessible(true);

                return (T) field.get(object);
            } catch (NoSuchFieldException e) {
                ;
            } catch (IllegalAccessException e) {
                ;
            }
        } while (currentClass.getSuperclass() != Object.class && (currentClass = currentClass.getSuperclass()) != null);

        return null;
    }
}
