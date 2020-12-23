package fr.freezman31.xiw0ll.commands.sub;

import fr.freezman31.xiw0ll.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReset implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.isOp()) {
                Main.deleteAll("sub1");
                Main.deleteAll("sub2");
                Main.deleteAll("sub3");
            }
        } else {
            Main.deleteAll("sub1");
            Main.deleteAll("sub2");
            Main.deleteAll("sub3");
        }
        return false;
    }
}
