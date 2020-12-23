package fr.freezman31.xiw0ll.commands.kits;

import fr.freezman31.xiw0ll.Main;
import fr.freezman31.xiw0ll.utils.base64;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CommandKit implements CommandExecutor {
    private int size;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            int number = Main.selectListKit().size();
            if (number < 9) {
                size = 9;
            } else if (number < 18) {
                size = 18;
            } else if (number < 27) {
                size = 27;
            } else if (number < 36) {
                size = 36;
            } else if (number < 45) {
                size = 45;
            } else if (number < 54) {
                size = 54;
            }
            Inventory inv = Bukkit.createInventory(null, size, "§bKits");
            Player p = (Player) sender;
            if (args.length == 0) {
                ArrayList<String> list = Main.selectListKit();
                for (int i = 0; i < list.size(); i++) {
                    inv.setItem(i, Main.selectItemKit(list.get(i)));
                }
                p.openInventory(inv);
            } else {
                ItemStack[] kit = new ItemStack[0];
                try {
                    kit = base64.itemStackArrayFromBase64(Main.selectAllKit(args[0]));
                } catch (IOException e) {
                    p.sendMessage(ChatColor.GREEN + "Ce kit n'existe pas !");
                }
                ArrayList<String> sub1 = Main.selectAll("sub1");
                ArrayList<String> sub2 = Main.selectAll("sub2");
                ArrayList<String> sub3 = Main.selectAll("sub3");
                switch (Main.selectPermsKit(args[0])) {
                    case "sub1":
                        if (!(sub1.contains(p.getName()))) {
                            p.sendMessage(ChatColor.GREEN + "Vous n'avez pas le grade requis");
                        } else {
                            try {
                                kitReceive(p, kit, args[0]);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "sub2":
                        if (!(sub2.contains(p.getName()))) {
                            p.sendMessage(ChatColor.GREEN + "Vous n'avez pas le grade requis");
                        } else {
                            try {
                                kitReceive(p, kit, args[0]);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "sub3":
                        if (!(sub3.contains(p.getName()))) {
                            p.sendMessage(ChatColor.GREEN + "Vous n'avez pas le grade requis");
                        } else {
                            try {
                                kitReceive(p, kit, args[0]);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    default:
                        try {
                            kitReceive(p, kit, args[0]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;

                }

            }
        } else {
            System.out.println(ChatColor.DARK_BLUE + "Veuillez executer cette commande en tant que joueur !");
        }
        return false;
    }

    public static void kitReceive(Player p, ItemStack[] kit, String kitName) throws ParseException {

        if (isAutorized(p.getName(), kitName)) {
            Date date = Calendar.getInstance().getTime();
            Main.removeLastTime(p.getName(), kitName);
            Main.insertPlayerTime(p.getName(), date, kitName);
            for (ItemStack itemStack : kit) {
                if (itemStack != null) {
                    if (itemStack.getType() != Material.SPECTRAL_ARROW) {
                        p.getInventory().addItem(itemStack);
                    }
                }
            }
        } else {
            p.sendMessage(ChatColor.GREEN + "Vous ne pouvez pas encore réutiliser ce kit");
        }
        p.closeInventory();
    }


    public static Boolean isAutorized(String playerName, String kitName) throws ParseException {
        String lastDate = Main.selectTime(playerName, kitName);
        if (lastDate == null) {
            return true;
        }
        Date now = Calendar.getInstance().getTime();
        Date latest = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(lastDate);
        int latestM = latest.getMinutes();
        int nowM = now.getMinutes();
        return nowM - latestM >= Main.selectCooldownKit(kitName);
    }
}
