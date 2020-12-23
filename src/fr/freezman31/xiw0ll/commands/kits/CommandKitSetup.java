package fr.freezman31.xiw0ll.commands.kits;

import fr.freezman31.xiw0ll.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandKitSetup implements CommandExecutor {
    public static String name;
    public static String permission;
    public static int cooldown;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0 || args.length == 1) {
            sender.sendMessage(ChatColor.GREEN + "Syntaxe : /kitcreator <name> <cooldown> [rank]");
            return false;
        }
        name = args[0];
        try {
            cooldown = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.GREEN + "Le cooldown doit être un entier");
        }
        if (args.length != 2) {
            if (args[2].equals("sub1") || args[2].equals("sub2") || args[2].equals("sub3")) {
                permission = args[2];
            } else {
                sender.sendMessage(ChatColor.GREEN + "Permission invalide : sub1 sub2 ou sub3");
                return false;
            }

        }
        Player p = (Player) sender;
        Inventory inv = Bukkit.createInventory(null, 27, "§aKit Creator");
        inv.setItem(26, Main.createItem(Material.SPECTRAL_ARROW, "§l§4Fermer", 1));
        p.openInventory(inv);


        return false;
    }
}
