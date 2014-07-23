package net.crunkle.command.example;

import net.crunkle.command.api.DefinedCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @author Jared Tiala
 */
public class TestCommand {
    @DefinedCommand(
            name = "example",
            description = "This is an example command.",
            usage = "[message]",
            aliases = {"test", "demo"}
    )
    public void example(CommandSender sender, Command command, String[] args) {
        sender.sendMessage("It works!");

        if (args.length >= 1) {
            sender.sendMessage(args[0]);
        }
    }
}
