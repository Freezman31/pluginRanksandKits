package fr.freezman31.xiw0ll.commands.kits;

import fr.freezman31.xiw0ll.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.*;

public class CommandKitRemove implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "Erreur de syntaxe : /kitremove <nom>");
            return false;
        } else {
            String kitName = args[0];
            if (Main.selectListKit().contains(kitName)) {
                Main.deleteKit(kitName);
                sender.sendMessage(ChatColor.GREEN + "Le kit " + kitName + " a bien été supprimé");
            } else {
                sender.sendMessage(ChatColor.GREEN + "Ce kit n'existe pas");
            }
        }

        return false;
    }
}
