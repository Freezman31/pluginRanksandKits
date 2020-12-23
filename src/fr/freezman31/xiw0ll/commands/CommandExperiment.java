package fr.freezman31.xiw0ll.commands;

import fr.freezman31.xiw0ll.Main;
import fr.freezman31.xiw0ll.utils.base64;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

public class CommandExperiment implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            Inventory i = p.getInventory();
            String data = base64.itemStackArrayToBase64(i.getContents());
            Main.insert(data, "dev");
            i.clear();
            try {
                i.setContents(base64.itemStackArrayFromBase64(data));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
