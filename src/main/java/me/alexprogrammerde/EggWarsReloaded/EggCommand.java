package me.alexprogrammerde.EggWarsReloaded;

import me.alexprogrammerde.EggWarsReloaded.admin.guis.EditMenu;
import me.alexprogrammerde.EggWarsReloaded.game.GameRegisterer;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class EggCommand implements CommandExecutor, TabExecutor {
    private static final String[] PLAYER = { "help", "join"/*, "randomjoin"*/ };
    private static final String[] ADMIN = { /*"reload", */"addarena", "delarena", /*"kick", */"edit" };

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                // Player commands
                if (args[0].equals("help")) {
                    if (player.hasPermission("eggwarsreloaded.command.help")) {
                        if (player.hasPermission("eggwarsreloaded.admin")) {
                            List<String> messagelist = EggWarsReloaded.getEggWarsMain().getLanguage().getStringList("command.help.adminhelp");
                            String[] messagearr = new String[messagelist.size()];
                            messagearr = messagelist.toArray(messagearr);
                            player.sendMessage(messagearr);
                        } else {
                            List<String> messagelist = EggWarsReloaded.getEggWarsMain().getLanguage().getStringList("command.help.playerhelp");
                            String[] messagearr = new String[messagelist.size()];
                            messagearr = messagelist.toArray(messagearr);
                            player.sendMessage(messagearr);
                        }
                    } else {
                        player.sendMessage("You have no permission!");
                    }
                }

                if (args[0].equals("join")) {
                    if (player.hasPermission("eggwarsreloaded.command.join")) {
                        if (args.length > 1) {
                            if (ArenaManager.getArenas().contains("arenas." + args[1]) && ArenaManager.getArenas().getBoolean("arenas." + args[1] + ".registered")) {
                                if (!GameRegisterer.gamesmap.containsKey(args[1])) {
                                    GameRegisterer.addGame(args[1]);
                                }

                                GameRegisterer.gamesmap.get(args[1]).addPlayer(player);

                            } else {
                                player.sendMessage("Sorry a arena with this name doesnt exist.");
                            }
                        }
                    }
                }

                /*if (args[0].equals("randomjoin")) {
                    if (player.hasPermission("eggwarsreloaded.command.randomjoin")) {

                    }
                }*/

                // Admin commands
                /*if (args[0].equals("reload")) {
                    if (player.hasPermission("eggwarsreloaded.command.reload")) {
                        EggWarsReloaded.getEggWarsMain().reloadConfig();
                    }
                }*/

                if (args[0].equals("addarena")) {
                    if (player.hasPermission("eggwarsreloaded.command.addarena")) {
                        if (args.length > 1) {
                            ArenaManager.addArena(args[1]);

                            player.sendMessage("Added arena: " + args[1]);
                            player.sendMessage("Use /eggwars edit " + args[1] + " to set up the arena.");
                        } else {
                            player.sendMessage("Usage: /eggwars addarena arenaName");
                        }
                    } else {
                        player.sendMessage("You have no permission!");
                    }
                }

                if (args[0].equals("delarena")) {
                    if (player.hasPermission("eggwarsreloaded.command.delarena")) {
                        if (args.length > 1) {
                            if (ArenaManager.getArenas().contains("arenas." + args[1])) {
                                ArenaManager.deleteArena(args[1]);
                                player.sendMessage("Deleted arena: " + args[1]);
                            } else {
                                player.sendMessage("Sorry a arena with this name doesn't exist.");
                            }
                        } else {
                            player.sendMessage("Usage: /eggwars delarena arenaName");
                        }
                    } else {
                        player.sendMessage("You have no permission!");
                    }
                }

                /*if (args[0].equals("kick")) {
                    if (player.hasPermission("eggwarsreloaded.command.kick")) {

                    }
                }*/

                if (args[0].equals("edit")) {
                    if (player.hasPermission("eggwarsreloaded.command.edit")) {
                        if (args.length > 1) {
                            if (ArenaManager.getArenas().contains("arenas." + args[1])) {
                                EditMenu.openEditMenu(args[1], player);
                            } else {
                                player.sendMessage("Sorry a arena with this name doesn't exist.");
                            }
                        } else {
                            player.sendMessage("Usage: /eggwars edit arenaName");
                        }
                    } else {
                        player.sendMessage("You have no permission!");
                    }
                }
            }
        }

        // Console support soon?
        if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            sender.sendMessage("Sorry you need to run this command ingame.");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1) {
                if (player.hasPermission("eggwarsreloaded.player")) {
                    StringUtil.copyPartialMatches(args[0], Arrays.asList(PLAYER), completions);
                    Collections.sort(completions);
                }

                if (player.hasPermission("eggwarsreloaded.admin")) {
                    StringUtil.copyPartialMatches(args[0], Arrays.asList(ADMIN), completions);
                    Collections.sort(completions);
                }
            }

            if (player.hasPermission("eggwarsreloaded.command.edit") && args[0].equals("edit") && args.length == 2) {
                try {
                    Set<String> arenas = ArenaManager.getArenas().getConfigurationSection("arenas").getKeys(false);

                    StringUtil.copyPartialMatches(args[1], arenas, completions);
                    Collections.sort(completions);
                } catch (NullPointerException ignored) {
                }
            }

            if (player.hasPermission("eggwarsreloaded.command.delarena") && args[0].equals("delarena") && args.length == 2) {
                try {
                    Set<String> arenas = ArenaManager.getArenas().getConfigurationSection("arenas").getKeys(false);

                    StringUtil.copyPartialMatches(args[1], arenas, completions);
                    Collections.sort(completions);
                } catch (NullPointerException ignored) {
                }
            }

            if (player.hasPermission("eggwarsreloaded.command.join") && args[0].equals("join") && args.length == 2) {
                Set<String> arenas = ArenaManager.getArenas().getConfigurationSection("arenas").getKeys(false);
                Set<String> registeredarenas = new HashSet<>();

                for (String arenaName : arenas) {
                    if (ArenaManager.getArenas().getBoolean("arenas." + arenaName + ".registered")) {
                        registeredarenas.add(arenaName);
                    }
                }

                StringUtil.copyPartialMatches(args[1], arenas, completions);
                Collections.sort(completions);
            }

        }

        // Console support soon?
        if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
        }

        return completions;
    }
}