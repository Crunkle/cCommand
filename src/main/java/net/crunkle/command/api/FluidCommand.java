package net.crunkle.command.api;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * This class is used to store information about dynamic
 * commands which have been added by the API. The class
 * is simply used to hold the command information in
 * memory for later use, such as execution.
 *
 * @author Jared Tiala
 */
public class FluidCommand extends Command {
    private final CommandAPI handle;
    private final Object parent;
    private final Method method;

    protected FluidCommand(CommandAPI handle, Object parent, Method method, String name,
                           String description, String usage, List<String> aliases) {
        super(name, description, usage, aliases);

        this.handle = handle;
        this.parent = parent;
        this.method = method;
    }

    /**
     * Executes this command which has been registered
     * inside of the API handle.
     *
     * @param sender the entity who executed the command
     * @param label  the command label which was used
     * @param args   the arguments which have been sent
     * @return whether the command was successfully executed
     */
    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        try {
            this.handle.execute(sender, this, args);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * @return the parent of the command
     */
    public Object getParent() {
        return this.parent;
    }

    /**
     * @return the command method defined in the parent
     */
    public Method getMethod() {
        return this.method;
    }
}
