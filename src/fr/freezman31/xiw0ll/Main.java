package fr.freezman31.xiw0ll;

import fr.freezman31.xiw0ll.commands.*;
import fr.freezman31.xiw0ll.commands.kits.CommandKit;
import fr.freezman31.xiw0ll.commands.kits.CommandKitRemove;
import fr.freezman31.xiw0ll.commands.kits.CommandKitSetup;
import fr.freezman31.xiw0ll.commands.sub.CommandReset;
import fr.freezman31.xiw0ll.utils.base64;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Main extends JavaPlugin {
    public static String url = "jdbc:sqlite:plugins/ranks/ranks.db";


    @Override
    public void onEnable() {
        System.out.println("Le plugin est active");
        setupDB();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("jdbc driver unavailable!");
        }
        connect();
        getCommand("reset").setExecutor(new CommandReset());
        getCommand("unrole").setExecutor(new CommandRemove());
        getCommand("kit").setExecutor(new CommandKit());
        getCommand("role").setExecutor(new CommandRole());
        getCommand("kitsetup").setExecutor(new CommandKitSetup());
        getCommand("kitremove").setExecutor(new CommandKitRemove());
        getServer().getPluginManager().registerEvents(new listener(), this);
    }



    @Override
    public void onDisable() {
        System.out.println("Plugin disabled");
    }

    private void setupDB() {
        File directory = new File("plugins/ranks");
        if (!directory.exists()) {
            directory.mkdir();
        }
        File database = new File("plugins/ranks/ranks.db");
        if (!database.exists()) {
            createNewDatabase("ranks.db");
            createTableKits();
            createTableCooldown();
        }
    }

    public static Connection connect() {
        java.sql.Connection conn = null;
        try {

            conn = DriverManager.getConnection(url);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:plugins/PluginMetrics/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insert(String name, String subTiers) {
        String sql = "INSERT INTO `" + subTiers + "` (player) VALUES ('" + name + "')";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            conn.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<String> selectAll(String subTiers) {
        ArrayList<String> r = new ArrayList<>();
        String sql = "SELECT * FROM " + subTiers;

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("player") != null) {
                    r.add(rs.getString("player"));
                }
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }

    public static void createTable(String name) {
        String sql = "CREATE TABLE IF NOT EXISTS " + name + "(\n"
                + "	player text\n"
                + ");";
        try {
            Connection conn = DriverManager.getConnection(Main.url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTableCooldown() {
        String sql = "CREATE TABLE IF NOT EXISTS cooldown(\n"
                + "	player text,\n"
                + " moment text,\n"
                + " kit text\n"
                + ");";
        try {
            Connection conn = DriverManager.getConnection(Main.url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTableKits() {
        String sql = "CREATE TABLE IF NOT EXISTS kits(\n"
                + "	id text,\n"
                + " data text,"
                + " perms text,"
                + " item text,\n"
                + "cooldown int\n"
                + ");";
        try {
            Connection conn = DriverManager.getConnection(Main.url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteAll(String subTiers) {
        String sql = "DELETE FROM `" + subTiers + "`";
        try {
            Connection conn = DriverManager.getConnection(Main.url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void remove(String subTiers, String name) {
        String sql = "DELETE FROM `" + subTiers + "`\n"
                + "WHERE `player` = '" + name + "'";
        try {
            Connection conn = DriverManager.getConnection(Main.url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            Player p = Bukkit.getPlayer(subTiers);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ItemStack createItem(Material material, String name, int amount) {
        ItemStack it = new ItemStack(material, amount);
        ItemMeta itM = it.getItemMeta();
        itM.setDisplayName(name);
        it.setItemMeta(itM);
        return it;
    }

    public static void removeStaff(String name) {
        Main.remove("modo", name);
        Main.remove("sm", name);
        Main.remove("admin", name);
        Main.remove("dev", name);
    }

    public static void insertKit(String id, String data, String item, int cooldown) {
        String sql = "INSERT INTO `kits` (id,data,perms,item,cooldown) VALUES ('" + id + "','" + data + "','false','" + item + "','" + cooldown + "')";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            conn.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertKitPerms(String id, String data, String perms, String item, int cooldown) {
        String sql = "INSERT INTO `kits` (id,data,perms,item) VALUES ('" + id + "','" + data + "','" + perms + "','" + item + "','" + cooldown + "')";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            conn.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String selectAllKit(String name) {
        String r = "";
        String sql = "SELECT * FROM kits WHERE id = '" + name + "'";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                r = rs.getString("data");
            }
            rs.close();
            conn.close();
            return r;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }

    public static ItemStack selectItemKit(String name) {
        ItemStack r = new ItemStack(Material.BEACON);
        String temp;
        String sql = "SELECT * FROM kits WHERE id = '" + name + "'";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                temp = rs.getString("item");
                r = base64.base64ToItemStack(temp);
            }
            rs.close();
            conn.close();
            return r;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }

    public static ArrayList<String> selectListKit() {
        ArrayList<String> r = new ArrayList<>();
        String sql = "SELECT id FROM kits";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                r.add(rs.getString("id"));
            }
            rs.close();
            conn.close();
            return r;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }

    public static String selectPermsKit(String name) {
        String r = "";
        String sql = "SELECT perms FROM kits WHERE id = '" + name + "'";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                r = rs.getString("perms");
            }
            rs.close();
            conn.close();
            return r;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }

    public static ArrayList<String> selectNameKit() {
        ArrayList<String> r = new ArrayList<String>();
        String sql = "SELECT `item` FROM kits";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                r.add(rs.getString("item"));
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }

    public static void insertPlayerTime(String playername, Date date, String kit) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        String sql = "INSERT INTO `cooldown` (player,moment, kit) VALUES ('" + playername + "','" + strDate + "','" + kit + "')";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            conn.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String selectTime(String player, String kit) {
        String r = null;
        String sql = "SELECT moment FROM cooldown WHERE player = '" + player + "' AND kit = '" + kit + "'";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                r = rs.getString("moment");
            }
            rs.close();
            conn.close();
            return r;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }

    public static void removeLastTime(String player, String kit) {
        String sql = "DELETE FROM `cooldown`\n"
                + "WHERE `player` = '" + player + "' AND `kit` = '" + kit + "'";
        try {
            Connection conn = DriverManager.getConnection(Main.url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int selectCooldownKit(String name) {
        int r = 0;
        String sql = "SELECT cooldown FROM kits WHERE id = '" + name + "'";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                r = rs.getInt("cooldown");
            }
            rs.close();
            conn.close();
            return r;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }

    public static void deleteKit(String kitName) {
        String sql = "DELETE FROM `kits` WHERE id = '" + kitName + "'";
        try {
            Connection conn = DriverManager.getConnection(Main.url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
