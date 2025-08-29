package politgame.Niky.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import politgame.Niky.Data.FileData;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReligionTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> list = new ArrayList<>();
        YamlConfiguration religionFile = FileData.getReligionsYaml();
        Set<String> religions = religionFile.getConfigurationSection("religions").getKeys(false);
        switch (args.length) {
            case 1:
                list.add("get");
                list.add("set");
                list.add("clear");
                list.add("check");
                list.add("members");
                list.add("god");
                break;
            case 2:
                if(args[0].equals("get")) {
                    list.addAll(religions);
                }
                if(args[0].equals("set") || args[0].equals("check")) {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        list.add(player.getDisplayName());
                    }
                }
                break;
            case 3:
                if(args[0].equals("set")) {
                    list.addAll(religions);
                }
        }

        String lastArg = args[args.length - 1].toLowerCase();
        return list.stream()
                .filter(s1 -> s1.toLowerCase().startsWith(lastArg))
                .collect(Collectors.toList());
    }
}
