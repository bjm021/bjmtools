package de.bjm.mc.bjmtools;

import de.bjm.mc.bjmtools.commands.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class BJMTools extends JavaPlugin {

    public static final String PREFIX = "§7[§dBJMTools§7] §r";

    private CommandHandler commandHandler;

    @Override
    public void onEnable() {
        System.out.println("[BJMTools] Loading...");
        System.out.println("[BJMTools] Registering commands...");
        commandHandler = new CommandHandler(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandHandler.onCommand(sender, command, label, args);
    }
}
