package fr.freezman31.xiw0ll.commands;

import fr.freezman31.xiw0ll.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRemove implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        p.sendTitle("Bienvenue", "a", 1, 2, 3);
        if (p.isOp()) {
            Player target = Bukkit.getPlayer(args[0]);
            Main.remove("sub1", target.getName());
            Main.remove("sub2", target.getName());
            Main.remove("sub3", target.getName());
            Main.remove("sm", target.getName());
            Main.remove("admin", target.getName());
            Main.remove("modo", target.getName());
            Main.remove("dev", target.getName());
            Main.remove("builder", target.getName());

            target.setPlayerListName(target.getName());

        }
        return false;
    }
}
