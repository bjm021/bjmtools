package de.bjm.mc.bjmtools.commands;

import de.bjm.mc.bjmtools.BJMTools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CommandHandler {

    private static final String PREFIX = BJMTools.PREFIX;

    private BJMTools plugin;

    private Map<String, BJMCommand> commands;

    public CommandHandler(BJMTools plugin) {
        this.plugin = plugin;
        registerCommands();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player p = null;
        if (sender instanceof Player) {
            p = (Player) sender;
        }

        if (commands.containsKey(cmd.getName().toLowerCase())) {
            BJMCommand command = commands.get(cmd.getName());

            try {
                if (command.requiresPlayer()) {
                    if (p == null) {
                        sender.sendMessage("[BJMTools] This command can only be executed in-game!!!");
                    } else {
                        command.getCommandMethod().invoke(this, p, args);
                    }
                } else {
                    command.getCommandMethod().invoke(this, sender, args);
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                sender.sendMessage("[BJMTools] There was an error during command execution!");
            }
        }

        return true;
    }


    private void registerCommands() {
        for (Method method : this.getClass().getDeclaredMethods()) {
            BJMCommandHandler annotation = method.getAnnotation(BJMCommandHandler.class);
            if (annotation != null) {
                String name = annotation.name().isEmpty() ? method.getName().toLowerCase() : annotation.name();
                String usage = annotation.usage().isEmpty() ? null : annotation.usage();
                for (Class<?> parameterType : method.getParameterTypes()) {
                    if (!parameterType.getName().contains("Ljava.lang.String")) {
                        return;
                    }
                }
                boolean rp = annotation.requiresPlayer();


                commands.put(name.toLowerCase(), new BJMCommand(name, usage, rp, method));
            }
        }
    }

    @BJMCommandHandler(
            usage = "/superenchant",
            requiresPlayer = true,
            description = "Enchants item!!!"
    )
    private void superEnchant(Player p, String... args) {
        if (p.hasPermission("bjmtools.superenchant")) {
            ItemStack toEnchant = p.getInventory().getItemInMainHand();
            List<Enchantment> enchantments = Collections.unmodifiableList(Arrays.asList(Enchantment.values()));
            enchantments.forEach(e -> toEnchant.addUnsafeEnchantment(e, 1000));
            p.sendMessage(PREFIX + "§aSuperEnchant Activated!!");
        } else {
            p.sendMessage(noPermission);
        }
    }


    // standard replies
    private static final String noPermission = PREFIX + "§4You don't have permission to execute this command";

}
