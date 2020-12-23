package fr.freezman31.xiw0ll;

import fr.freezman31.xiw0ll.commands.kits.CommandKitSetup;
import fr.freezman31.xiw0ll.utils.base64;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static fr.freezman31.xiw0ll.commands.kits.CommandKit.kitReceive;

public class listener implements Listener {
    private String kitContent;
    private ItemStack[] contents;

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        ArrayList<String> sub1 = Main.selectAll("sub1");
        ArrayList<String> sub2 = Main.selectAll("sub2");
        ArrayList<String> sub3 = Main.selectAll("sub3");
        ArrayList<String> dev = Main.selectAll("dev");
        ArrayList<String> admin = Main.selectAll("admin");
        ArrayList<String> sm = Main.selectAll("sm");
        ArrayList<String> modo = Main.selectAll("modo");
        ArrayList<String> builder = Main.selectAll("builder");

        if (admin.contains(p.getName())) {
            event.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "[ADMIN] <" + p.getName() + "> " + event.getMessage());
        } else if (dev.contains(p.getName())) {
            event.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.GOLD + "[DEVELOPPEUR] <" + p.getName() + "> " + ChatColor.RESET + event.getMessage());
        } else if (sm.contains(p.getName())) {
            event.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.YELLOW + "[SUPER-MODO] <" + p.getName() + "> " + ChatColor.RESET + event.getMessage());
        } else if (modo.contains(p.getName())) {
            event.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.DARK_BLUE + "[MODO] <" + p.getName() + "> " + ChatColor.RESET + event.getMessage());
        } else if (builder.contains(p.getName())) {
            event.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.AQUA + "[BUILDER] <" + p.getName() + "> " + ChatColor.RESET + event.getMessage());
        } else if (sub3.contains(p.getName())) {
            event.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "<" + p.getName() + "> " + ChatColor.RESET + event.getMessage());
        } else if (sub2.contains(p.getName())) {
            event.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.BLUE + "<" + p.getName() + "> " + ChatColor.RESET + event.getMessage());
        } else if (sub1.contains(p.getName())) {
            event.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.GREEN + "<" + p.getName() + "> " + ChatColor.RESET + event.getMessage());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        ArrayList<String> sub1 = Main.selectAll("sub1");
        ArrayList<String> sub2 = Main.selectAll("sub2");
        ArrayList<String> sub3 = Main.selectAll("sub3");
        ArrayList<String> dev = Main.selectAll("dev");
        ArrayList<String> admin = Main.selectAll("admin");
        ArrayList<String> sm = Main.selectAll("sm");
        ArrayList<String> modo = Main.selectAll("modo");
        ArrayList<String> builder = Main.selectAll("builder");
        if (admin.contains(p.getName())) {
            p.setPlayerListName(ChatColor.DARK_RED + "[ADMIN] " + p.getName());
        } else if (dev.contains(p.getName())) {
            p.setPlayerListName(ChatColor.GOLD + "[DEVELOPPEUR] " + p.getName());
        } else if (sm.contains(p.getName())) {
            p.setPlayerListName(ChatColor.YELLOW + "[SUPER-MODO] " + p.getName());
        } else if (modo.contains(p.getName())) {
            p.setPlayerListName(ChatColor.DARK_BLUE + "[MODO] " + p.getName());
        } else if (builder.contains(p.getName())) {
            p.setPlayerListName(ChatColor.AQUA + "[BUILDER] " + p.getName());
        } else if (sub3.contains(p.getName())) {
            p.setPlayerListName(ChatColor.DARK_PURPLE + "[Sub Tiers 3] " + p.getName());
        } else if (sub2.contains(p.getName())) {
            p.setPlayerListName(ChatColor.BLUE + "[Sub Tiers 2] " + p.getName());
        } else if (sub1.contains(p.getName())) {
            p.setPlayerListName(ChatColor.GREEN + "[Sub Tiers 1] " + p.getName());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws IOException, ParseException {
        Player p = (Player) event.getWhoClicked();
        Inventory second = Bukkit.createInventory(null, 9, "§bConfirmation");
        ItemStack end = Main.createItem(Material.BLACK_CONCRETE, "§bFermer", 1);
        for (int i = 1; i < 9; i++) {
            second.setItem(i, end);
        }
        ArrayList<String> sub1 = Main.selectAll("sub1");
        ArrayList<String> sub2 = Main.selectAll("sub2");
        ArrayList<String> sub3 = Main.selectAll("sub3");

        if (event.getView().getTitle().equalsIgnoreCase("§bKits")) {
            if (event.getCurrentItem() == null) return;
            if (event.getClickedInventory() == p.getInventory()) return;
            event.setCancelled(true);
            String kitName = event.getCurrentItem().getItemMeta().getDisplayName();
            String data = Main.selectAllKit(event.getCurrentItem().getItemMeta().getDisplayName());
            ItemStack[] kit = base64.itemStackArrayFromBase64(data);
            switch (Main.selectPermsKit(event.getCurrentItem().getItemMeta().getDisplayName())) {
                case "sub1":
                    if (!(sub1.contains(p.getName()) || sub3.contains(p.getName()) || sub2.contains(p.getName()))) {
                        p.sendMessage(ChatColor.GREEN + "Vous n'avez pas le grade requis");
                        p.closeInventory();
                    } else {
                        kitReceive(p, kit, kitName);
                    }
                    break;
                case "sub2":
                    if (!(sub2.contains(p.getName()) || sub3.contains(p.getName()))) {
                        p.sendMessage(ChatColor.GREEN + "Vous n'avez pas le grade requis");
                        p.closeInventory();
                    } else {
                        kitReceive(p, kit, kitName);
                    }
                    break;
                case "sub3":
                    if (!(sub3.contains(p.getName()))) {
                        p.sendMessage(ChatColor.GREEN + "Vous n'avez pas le grade requis");
                        p.closeInventory();
                    } else {
                        kitReceive(p, kit, kitName);
                    }
                    break;
                case "false":
                    kitReceive(p, kit, kitName);
                    break;

            }
        }
        if (event.getView().getTitle().equalsIgnoreCase("§aKit Creator")) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getType() == Material.SPECTRAL_ARROW) {
                event.setCancelled(true);
                Inventory inv = event.getInventory();
                //this.kitContent = base64.itemStackArrayToBase64(save);
                this.contents = inv.getContents();
                p.closeInventory();
                p.openInventory(second);

            }
        }
        if (event.getView().getTitle().equals("§bConfirmation")) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§bFermer")) {
                event.setCancelled(true);
                ItemStack itN = event.getInventory().getItem(0);
                ItemMeta itNM = itN.getItemMeta();
                itNM.setDisplayName(CommandKitSetup.name);
                itN.setItemMeta(itNM);
                this.kitContent = base64.itemStackArrayToBase64(this.contents);
                if (CommandKitSetup.permission != null) {
                    Main.insertKitPerms(CommandKitSetup.name, kitContent, CommandKitSetup.permission, base64.itemStackToBase64(itN), CommandKitSetup.cooldown);
                } else {
                    Main.insertKit(CommandKitSetup.name, kitContent, base64.itemStackToBase64(itN), CommandKitSetup.cooldown);
                }
                p.closeInventory();

            }
        }
    }

}
