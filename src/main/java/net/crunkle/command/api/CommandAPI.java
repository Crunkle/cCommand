package net.crunkle.command.api;

import net.crunkle.command.util.ReflectionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * This class represents the main handle for plugins
 * to hook on. It handles the registration of commands
 * on demand using the {@link ReflectionUtil} methods.
 *
 * @author Crunkle
 */
public class CommandAPI {
    private final JavaPlugin plugin;

    public CommandAPI(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers all defined commands in an array of classes
     * by looking for the {@link DefinedCommand} annotation.
     *
     * @param classes the classes containing defined commands
     */
    public void registerAll(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            Object classInstance;

            try {
                classInstance = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();

                continue;
            }

            for (Method method : clazz.getMethods()) {
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation.annotationType().isAssignableFrom(DefinedCommand.class)) {
                        if (method.getParameterCount() != 3) {
                            continue;
                        }

                        DefinedCommand commandAnnotation = (DefinedCommand) annotation;

                        String usage = "/" + commandAnnotation.name() + " " + commandAnnotation.usage();

                        ArrayList<String> aliases = new ArrayList<>();

                        for (String alias : commandAnnotation.aliases()) {
                            aliases.add(alias);
                        }

                        FluidCommand fluidCommand = new FluidCommand(this, classInstance, method, commandAnnotation.name(),
                                commandAnnotation.description(), usage, aliases);

                        ReflectionUtil.getCommandMap().register(this.plugin.getName(), fluidCommand);
                    }
                }
            }
        }
    }

    /**
     * Executes a dynamic command which has been previously
     * registered.
     *
     * @param sender  the entity who executed the command
     * @param command the command which is being executed
     * @param args    the arguments which have been sent
     * @throws InvocationTargetException if there is an issue with invoking the method
     * @throws IllegalAccessException    if we are unable to access to required method
     */
    protected void execute(CommandSender sender, FluidCommand command, String[] args)
            throws InvocationTargetException, IllegalAccessException {
        command.getMethod().invoke(command.getParent(), sender, command, args);
    }
}
