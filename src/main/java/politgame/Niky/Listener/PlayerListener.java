package politgame.Niky.Listener;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import politgame.Niky.Data.FileData;
import politgame.Niky.ReligionPlugin;
import politgame.Niky.Service.ReligionService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PlayerListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        try {
            Connection conn = ReligionPlugin.getConn();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT EXISTS(SELECT * FROM users WHERE nick ='" + p.getDisplayName() + "') AS bool;");
            if(rs.next()) {
                Boolean bool = rs.getBoolean("bool");
                if(!bool) ReligionService.addPlayer(p);
                else {ReligionService.addEffects(p);}
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        YamlConfiguration ymlPut = FileData.getReligionsYaml();
        YamlConfiguration ymlMsg = FileData.getMsgYaml();
        try {
            List<String> blockFood = ymlPut.getStringList("religions." + ReligionService.getReligion(p) + ".blockFood");
            for(String food : blockFood) {
                Material blockFod = Material.getMaterial(food);
                Material playerFood = item.getType();
                if(blockFod.equals(playerFood)) {
                    String prefix = ymlMsg.getString("prefix");
                    String message = ymlMsg.getString("eatBlockedFood");
                    message = prefix + message;
                    message = message.replace("&", "§");
                    p.sendMessage(message);
                    e.setCancelled(true);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
