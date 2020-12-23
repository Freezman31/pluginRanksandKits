package fr.freezman31.xiw0ll.commands;

import fr.freezman31.xiw0ll.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandRole implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length <= 1) {
            sender.sendMessage(ChatColor.GREEN + "Syntaxe : /role <role> <player>");
            return false;
        }
        ArrayList<String> sub1 = Main.selectAll("sub1");
        ArrayList<String> sub2 = Main.selectAll("sub2");
        ArrayList<String> sub3 = Main.selectAll("sub3");
        ArrayList<String> dev = Main.selectAll("dev");
        ArrayList<String> admin = Main.selectAll("admin");
        ArrayList<String> sm = Main.selectAll("sm");
        ArrayList<String> modo = Main.selectAll("modo");
        ArrayList<String> builder = Main.selectAll("builder");
        Player target = Bukkit.getPlayer(args[1]);
        switch (args[0]) {
            case "sub1":
                if (sub1.contains(target.getName())) {
                    sender.sendMessage(ChatColor.GREEN + "Ce joueur est déjà sub1");
                } else {
                    Main.insert(target.getName(), "sub1");
                    target.setPlayerListName(ChatColor.GREEN + "[Sub Tiers 1] " + target.getName());
                    sender.sendMessage(ChatColor.GREEN + "Le joueur " + target.getName() + " est maintenant Sub Tiers 1 !");
                }
                break;
            case "sub2":
                if (sub2.contains(target.getName())) {
                    sender.sendMessage(ChatColor.GREEN + "Ce joueur est déjà sub2");
                } else {
                    Main.insert(target.getName(), "sub2");
                    target.setPlayerListName(ChatColor.BLUE + "[Sub Tiers 2] " + target.getName());
                    sender.sendMessage(ChatColor.GREEN + "Le joueur " + target.getName() + " est maintenant Sub Tiers 2 !");
                }
                break;
            case "sub3":
                if (sub3.contains(target.getName())) {
                    sender.sendMessage(ChatColor.GREEN + "Ce joueur est déjà sub3");
                } else {
                    Main.insert(target.getName(), "sub3");
                    target.setPlayerListName(ChatColor.DARK_PURPLE + "[Sub Tiers 3] " + target.getName());
                    sender.sendMessage(ChatColor.GREEN + "Le joueur " + target.getName() + " est maintenant Sub Tiers 3 !");
                }
                break;
            case "admin":
                if (admin.contains(target.getName())) {
                    sender.sendMessage(ChatColor.GREEN + "Ce joueur est déjà Administrateur");
                } else {
                    Main.removeStaff(target.getName());
                    Main.insert(target.getName(), "admin");
                    target.setPlayerListName(ChatColor.DARK_RED + "[ADMIN] " + target.getName());
                    sender.sendMessage(ChatColor.GREEN + "Le joueur " + target.getName() + " est maintenant Administrateur");
                }
                break;
            case "dev":
                if (dev.contains(target.getName())) {
                    sender.sendMessage(ChatColor.GREEN + "Ce joueur est déjà Développeur");
                } else {
                    Main.removeStaff(target.getName());
                    Main.insert(target.getName(), "dev");
                    target.setPlayerListName(ChatColor.GOLD + "[DEVELOPPEUR] " + target.getName());
                    sender.sendMessage(ChatColor.GREEN + "Le joueur " + target.getName() + " est maintenant Développeur !");
                }
                break;
            case "sm":
                if (sm.contains(target.getName())) {
                    sender.sendMessage(ChatColor.GREEN + "Ce joueur est déjà Super-Modérateur");
                } else {
                    Main.removeStaff(target.getName());
                    Main.insert(target.getName(), "sm");
                    target.setPlayerListName(ChatColor.YELLOW + "[SUPER-MODO] " + target.getName());
                    sender.sendMessage(ChatColor.GREEN + "Le joueur " + target.getName() + " est maintenant Super-Modérateur !");
                }
                break;
            case "modo":
                if (modo.contains(target.getName())) {
                    sender.sendMessage(ChatColor.GREEN + "Ce joueur est déjà Modérateur");
                } else {
                    Main.removeStaff(target.getName());
                    Main.insert(target.getName(), "modo");
                    target.setPlayerListName(ChatColor.DARK_BLUE + "[MODO] " + target.getName());
                    sender.sendMessage(ChatColor.GREEN + "Le joueur " + target.getName() + " est maintenant Modérateur !");
                }
                break;
            case "builder":
                if (builder.contains(target.getName())) {
                    sender.sendMessage(ChatColor.GREEN + "Ce joueur est déjà Builder !");
                } else {
                    Main.removeStaff(target.getName());
                    Main.insert(target.getName(), "builder");
                    target.setPlayerListName(ChatColor.AQUA + "[BUILDER] " + target.getName());
                    sender.sendMessage(ChatColor.GREEN + "Le joueur " + target.getName() + " est maintenant Builder !");
                }
        }
        return false;
    }
}
