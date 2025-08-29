package politgame.Niky.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import politgame.Niky.Data.FileData;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static politgame.Niky.Service.ReligionService.*;

public class ReligionExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player p) {
            YamlConfiguration msg = FileData.getMsgYaml();
            YamlConfiguration religionFile = FileData.getReligionsYaml();
            String prefix = msg.getString("prefix");
            String message;
            switch (args.length) {
                case 0:
                    message = msg.getString("religion");
                    try {
                        message = prefix + message;
                        message = message.replace("%religion%", getReligionName(getReligion(p)));
                        message = message.replace("&", "§");
                        p.sendMessage(message);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    if(args[0].equalsIgnoreCase("god")) {
                        try {
                            message = msg.getString("godReligion");
                            message = prefix + message;
                            message = message.replace("%god%", getGod(getReligion(p)));
                            message = message.replace("&", "§");
                            p.sendMessage(message);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (args[0].equals("members")) {
                        try {
                            Integer count = getMembers(getReligion(p));
                            message = msg.getString("countMembers");
                            message = prefix + message;
                            message = message.replace("%count%", String.valueOf(count));
                            message = message.replace("&", "§");
                            p.sendMessage(message);
                        }catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if(args[0].equals("clear")){
                        try {
                            setReligion(p, "non");
                            message = msg.getString("clearReligion");
                            message = prefix + message;
                            message = message.replace("&", "§");
                            p.sendMessage(message);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    if (args[0].equals("check")) {
                        if(!p.hasPermission("ReligionPlugin.check") || !p.isOp()) {
                            message = msg.getString("notPerms");
                            message = prefix + message;
                            message = message.replace("&", "§");
                            p.sendMessage(message);
                        }
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target == null) {
                            message = msg.getString("notPlayer");
                            message = prefix + message;
                            message = message.replace("&", "§");
                            p.sendMessage(message);
                        }

                        try {
                            String religion = getReligionName(getReligion(target));
                            message = msg.getString("checkReligion");
                            message = prefix + message;
                            message = message.replace("%religion%", religion);
                            message = message.replace("%target%", target.getDisplayName());
                            message = message.replace("&", "§");
                            p.sendMessage(message);
                        }catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    if(args[0].equals("get")) {
                        Boolean religionExists = false;
                        Set<String> religions = religionFile.getConfigurationSection("religions").getKeys(false);
                        for(String religion : religions) {
                            if(args[1].equalsIgnoreCase(religion)) {
                                religionExists = true;
                                break;
                            }
                        }
                        if(religionExists) {
                            try {
                                String religion = args[1];
                                List<String> items = religionFile.getStringList("religions." + religion + ".paywall");
                                if (hasRequiredItems(p, items)) {
                                    removeRequiredItems(p, items);
                                    setReligion(p, religion);
                                    message = msg.getString("getReligion");
                                    message = prefix + message;
                                    message = message.replace("%religion%", getReligionName(religion));
                                    message = message.replace("&", "§");
                                    p.sendMessage(message);
                                } else {
                                    message = msg.getString("notItems");
                                    message = prefix + message;
                                    message = message.replace("&", "§");
                                    p.sendMessage(message);
                                    message = msg.getString("itemsForReligion");
                                    message = prefix + message;
                                    message = message.replace("%items%", items.toString());
                                    message = message.replace("&", "§");
                                    p.sendMessage(message);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            message = msg.getString("notReligion");
                            message = prefix + message;
                            message = message.replace("&", "§");
                            p.sendMessage(message);
                        }
                    }
                    break;
                case 3:
                    if(args[0].equals("set")) {
                        if (p.hasPermission("ReligionPlugin.set") || p.isOp()) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if(target == null) {
                                message = msg.getString("notPlayer");
                                message = prefix + message;
                                message = message.replace("&", "§");
                                p.sendMessage(message);
                            }
                            if(args[2].equals("non")) {
                                try {
                                    setReligion(target, "non");
                                    message = msg.getString("setReligion");
                                    message = prefix + message;
                                    message = message.replace("%target%", args[1]);
                                    message = message.replace("%religion%", getReligionName(args[2]));
                                    message = message.replace("&", "§");
                                    p.sendMessage(message);
                                    break;
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            Boolean religionExists = false;
                            Set<String> religions = religionFile.getConfigurationSection("religions").getKeys(false);
                            for(String religion : religions) {
                                if(args[2].equalsIgnoreCase(religion)) {
                                    religionExists = true;
                                    break;
                                }
                            }
                            if(religionExists) {
                                try {
                                    setReligion(target, args[2]);
                                    message = msg.getString("setReligion");
                                    message = prefix + message;
                                    message = message.replace("%target%", args[1]);
                                    message = message.replace("%religion%", getReligionName(args[2]));
                                    message = message.replace("&", "§");
                                    p.sendMessage(message);
                                }catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                message = msg.getString("notReligion");
                                message = prefix + message;
                                message = message.replace("&", "§");
                                p.sendMessage(message);
                            }
                            break;
                        } else {
                            message = msg.getString("notPerms");
                            message = message.replace("&", "§");
                            p.sendMessage(message);
                        }
                    }
            }
        }
        return true;
    }
}
