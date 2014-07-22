package net.crunkle.command.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Crunkle
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefinedCommand {
    /**
     * @return the label of the command
     */
    String name();

    /**
     * @return the detailed description of the command
     */
    String description() default "";

    /**
     * @return the detailed usage of the command
     */
    String usage() default "";

    /**
     * @return the various aliases of the command
     */
    String[] aliases() default {};
}
