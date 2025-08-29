package politgame.Niky;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import politgame.Niky.Service.ReligionService;

import java.sql.SQLException;

public class Expansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "ReligionPlugin";
    }

    @Override
    public @NotNull String getAuthor() {
        return "CaptainNiky";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {
        Player p = (Player) offlinePlayer;
        if(params.equals("religion")) {
            try {
                return ReligionService.getReligionName(ReligionService.getReligion(p));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (params.equals("members")) {
            try {
                return ReligionService.getMembers(ReligionService.getReligion(p)).toString();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(params.equals("god")) {
            try {
                return ReligionService.getGod(ReligionService.getReligion(p));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
