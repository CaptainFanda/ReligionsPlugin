package politgame.Niky;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import politgame.Niky.Commands.ReligionExecutor;
import politgame.Niky.Commands.ReligionTabCompleter;
import politgame.Niky.Data.FileData;
import politgame.Niky.Listener.PlayerListener;
import politgame.Niky.Expansion;

import java.sql.Connection;
import java.sql.SQLException;

public final class ReligionPlugin extends JavaPlugin {
    private static Connection conn = null;
    private static ReligionPlugin instance = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        FileData.createData();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        try {
            conn = Database.createDB();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) { //
            new Expansion().register(); //
        }
        getCommand("religion").setExecutor(new ReligionExecutor());
        getCommand("religion").setTabCompleter(new ReligionTabCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Connection getConn() {
        return conn;
    }
    public static ReligionPlugin getInstance() {
        return instance;
    }
}
