package politgame.Niky.Data;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileData {
    private static final File config = new File("plugins/ReligionPlugin/config.yml");
    private static final File religions = new File("plugins/ReligionPlugin/religions.yml");
    private static final File msg = new File("plugins/ReligionPlugin/messages.yml");

    public static void createData() {
        YamlConfiguration ymlPut;
        if(!config.exists()){
            ymlPut = YamlConfiguration.loadConfiguration(config);
            ymlPut.addDefault("dbms", "sqlite");
            ymlPut.addDefault("database.jdbc", "jdbc:mysql:");
            ymlPut.addDefault("database.user", "root");
            ymlPut.addDefault("database.pass", "root");
            ymlPut.options().copyDefaults(true);
            try {
                ymlPut.save(config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!religions.exists()) {
            ymlPut = YamlConfiguration.loadConfiguration(religions);
            List<String> paywall = new ArrayList<>();
            paywall.add("DIRT:128");
            paywall.add("COBBLESTONE:128");
            List<String> effects = new ArrayList<>();
            effects.add("STRENGTH:1");
            effects.add("SPEED:1");
            List<String> blockFood = new ArrayList<>();
            blockFood.add("MILK_BUCKET");
            ymlPut.addDefault("religions.catholicism.name", "&eКатолизм");
            ymlPut.addDefault("religions.catholicism.god", "CaptainNiky");
            ymlPut.addDefault("religions.catholicism.paywall", paywall);
            ymlPut.addDefault("religions.catholicism.effects", effects);
            ymlPut.addDefault("religions.catholicism.blockFood", blockFood);
            ymlPut.options().copyDefaults(true);
            try {
                ymlPut.save(religions);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!msg.exists()) {
            ymlPut = YamlConfiguration.loadConfiguration(msg);
            ymlPut.addDefault("prefix", "&7[&6Server Name&7]&f ");
            ymlPut.addDefault("religion", "&7Ваша религия: &6%religion%");
            ymlPut.addDefault("setReligion", "&aУспешно &7установлена религия &6%religion% &7игроку &e%target%&f");
            ymlPut.addDefault("getReligion", "&aУспешно &7принята новая религия: &6%religion%&f");
            ymlPut.addDefault("checkReligion", "&7Религия игрока &e%target%&7: &6%religion%&f");
            ymlPut.addDefault("godReligion", "&7Ваш бог: %god%");
            ymlPut.addDefault("itemsForReligion", "&7Ресурсы для Религии: &e%items%");
            ymlPut.addDefault("countMembers", "&7Участников Религии: &e%count%");
            ymlPut.addDefault("clearReligion", "&aВы больше не приверженец Религии!");
            ymlPut.addDefault("notItems", "&cУ вас недостаточно предметов");
            ymlPut.addDefault("noPerms", "&cУ вас недостаточно ресурсов");
            ymlPut.addDefault("eatBlockedFood", "&cДанная Еда запрещена в вашей религии!");
            ymlPut.addDefault("notPlayer", "&cТакой Игрок не найден");
            ymlPut.addDefault("notReligion", "&cДанная религия не найдена");
            ymlPut.options().copyDefaults(true);
            try {
                ymlPut.save(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static YamlConfiguration getConfigYaml() {
        return YamlConfiguration.loadConfiguration(config);
    }
    public static YamlConfiguration getReligionsYaml() { return YamlConfiguration.loadConfiguration(religions); }
    public static YamlConfiguration getMsgYaml() { return YamlConfiguration.loadConfiguration(msg); }

}
