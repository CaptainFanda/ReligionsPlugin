package politgame.Niky.Service;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import politgame.Niky.Data.FileData;
import politgame.Niky.Database;
import politgame.Niky.ReligionPlugin;

import java.sql.*;
import java.util.List;

public class ReligionService {
    public static boolean hasRequiredItems(Player player, List<String> paywall) {
        for (String item : paywall) {
            String[] parts = item.split(":");
            Material material = Material.getMaterial(parts[0]);
            int amount = Integer.parseInt(parts[1]);
            if (material != null && player.getInventory().contains(material, amount)) {
                continue;
            }
            return false;
        }
        return true;
    }
    public static void removeRequiredItems(Player player, List<String> paywall) {
        for (String item : paywall) {
            String[] parts = item.split(":");
            Material material = Material.getMaterial(parts[0]);
            int amount = Integer.parseInt(parts[1]);
            if (material != null) {
                player.getInventory().removeItem(new ItemStack(material, amount));
            }
        }
    }

    public static void addEffects(Player player) throws SQLException {
        Bukkit.getScheduler().cancelTasks(ReligionPlugin.getInstance());
        String religion = getReligion(player);
        YamlConfiguration ymlPut = FileData.getReligionsYaml();
        List<String> effects;
        if(religion.equals("non")) return;
        effects = ymlPut.getStringList("religions." + religion + ".effects");
        Bukkit.getScheduler().runTaskTimer(ReligionPlugin.getInstance(), () -> {
            for(String effect : effects) {
                String[] parts = effect.split(":");
                String ef = parts[0];
                Integer power = Integer.parseInt(parts[1]);
                PotionEffect potionEffect = new PotionEffect(PotionEffectType.getByName(ef), 40, power-1);
                player.addPotionEffect(potionEffect);
            }
        }, 0L, 20L);
    }

    public static void setReligion(Player player, String religion) throws SQLException {
        Connection conn = ReligionPlugin.getConn();
        Statement st = conn.createStatement();
        st.execute("UPDATE users SET religion='" + religion + "' WHERE nick='" + player.getDisplayName()+"';");
        for(PotionEffect pe : player.getActivePotionEffects()) {
            player.removePotionEffect(pe.getType());
        }
        Bukkit.getScheduler().cancelTasks(ReligionPlugin.getInstance());
        addEffects(player);
    }
    public static String getReligion(Player player) throws SQLException {
        Connection conn = ReligionPlugin.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM users WHERE Nick='" + player.getDisplayName()+"';");
        while (rs.next()) {
            String religion = rs.getString("religion");
            return religion;
        }
        return "non";
    }
    public static void addPlayer(Player player) throws SQLException {
        Connection conn = ReligionPlugin.getConn();
        Statement st = conn.createStatement();
        st.execute("INSERT INTO users(Nick, Religion) VALUES('" + player.getDisplayName() + "', 'non');");
    }

    public static String getReligionName(String religion) {
        YamlConfiguration ymlPut = FileData.getReligionsYaml();
        if(religion.equals("non")) return "&cНету";
        String religionName = ymlPut.getString("religions." + religion + ".name");
        return religionName;
    }
    public static String getGod(String religion) {
        if(religion.equals("non")) return "&cНету";
        YamlConfiguration ymlPut = FileData.getReligionsYaml();
        String god = ymlPut.getString("religions." + religion + ".god");
        return god;
    }
    public static Integer getMembers(String religion) throws SQLException {
        Connection conn = ReligionPlugin.getConn();
        Statement st = conn.createStatement();
        String sql = "SELECT COUNT(*) FROM users WHERE Religion=?;";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, religion);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count;
        }
        return 0;
    }

}
