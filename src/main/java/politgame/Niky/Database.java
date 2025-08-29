package politgame.Niky;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import politgame.Niky.Data.FileData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Connection conn = null;

    public static Connection createDB() throws SQLException {
        YamlConfiguration ymlPut = FileData.getConfigYaml();
        String dbms = ymlPut.getString("dbms");
        Statement st;
        switch (dbms.toLowerCase()) {
            case "sqlite":
                conn = DriverManager.getConnection("jdbc:sqlite:plugins/ReligionPlugin/database.mv.db");
                st = conn.createStatement();
                st.execute("CREATE TABLE IF NOT EXISTS users(" +
                        "Nick varchar(20)," +
                        "Religion varchar(50)" +
                        ");");
                return conn;
            case "mysql":
                String jdbc = ymlPut.getString("database.jdbc");
                String user = ymlPut.getString("database.user");
                String pass = ymlPut.getString("database.pass");
                conn = DriverManager.getConnection(
                        jdbc,
                        user,
                        pass
                );
                st = conn.createStatement();
                st.execute("CREATE TABLE IF NOT EXISTS users(" +
                        "Nick varchar(20)," +
                        "Religion varchar(50)" +
                        ");");
                return conn;
            default:
                return null;
        }
    }
}
