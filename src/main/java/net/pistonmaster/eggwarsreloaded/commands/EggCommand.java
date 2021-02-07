package net.pistonmaster.eggwarsreloaded.commands;

import com.cryptomorin.xseries.XMaterial;
import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import net.pistonmaster.eggwarsreloaded.admin.guis.EditMenu;
import net.pistonmaster.eggwarsreloaded.game.GameControl;
import net.pistonmaster.eggwarsreloaded.game.collection.RejectType;
import net.pistonmaster.eggwarsreloaded.utils.ArenaManager;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EggCommand implements CommandExecutor, TabExecutor {
    private static final String[] PLAYER = {"help", "join", "randomjoin"};
    private static final String[] ADMIN = {"reload", "addarena", "delarena", "kick", "edit", "endgame"};
    private final EggWarsReloaded plugin;

    public EggCommand(EggWarsReloaded plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                // Player commands
                switch (args[0]) {
                    case "help":
                        if (player.hasPermission("eggwarsreloaded.command.help")) {
                            if (player.hasPermission("eggwarsreloaded.admin")) {
                                List<String> messageList = plugin.getLanguage().getStringList("command.help.adminhelp");
                                String[] messageArr = messageList.toArray(new String[0]);
                                player.sendMessage(messageArr);
                            } else {
                                List<String> messageList = plugin.getLanguage().getStringList("command.help.playerhelp");
                                String[] messageArr = messageList.toArray(new String[0]);
                                player.sendMessage(messageArr);
                            }
                        } else {
                            noPerm(player);
                        }
                        break;
                    case "join":
                        if (player.hasPermission("eggwarsreloaded.command.join")) {
                            if (args.length > 1) {
                                if (ArenaManager.isArenaRegistered(args[1])) {
                                    GameControl.addPlayer(player, GameControl.getGame(args[1]));
                                } else {
                                    player.sendMessage(ChatColor.RED + "Sorry a arena with this name doesn't exist.");
                                }
                            }
                        } else {
                            noPerm(player);
                        }
                        break;
                    case "randomjoin":
                        if (player.hasPermission("eggwarsreloaded.command.randomjoin")) {
                            player.sendMessage(ChatColor.GOLD + "Searching a game for you!");
                            RejectType reject = GameControl.randomJoin(player);

                            if (reject == RejectType.ALREADY_IN) {
                                player.sendMessage(ChatColor.RED + "You are already playing!");
                            } else if (reject == RejectType.FULL) {
                                player.sendMessage(ChatColor.RED + "All games are full!");
                            }
                        } else {
                            noPerm(player);
                        }
                        break;

                    // Admin commands
                    case "reload":
                        if (player.hasPermission("eggwarsreloaded.command.reload")) {
                            plugin.loadConfig();
                            player.sendMessage(ChatColor.GOLD + "Reloaded the config!");
                        } else {
                            noPerm(player);
                        }
                        break;
                    case "addarena":
                        if (player.hasPermission("eggwarsreloaded.command.addarena")) {
                            if (args.length > 1) {
                                ArenaManager.addArena(args[1]);

                                World world = plugin.getWorldManager().createEmptyWorld(args[1], World.Environment.NORMAL);

                                ArenaManager.setArenaWorld(args[1], args[1]);

                                world.getBlockAt(0, 80, 0).setType(XMaterial.BEDROCK.parseMaterial());

                                player.teleport(new Location(world, 0, 81, 0));

                                player.sendMessage(ChatColor.GOLD + "Added arena: " + args[1]);
                                player.sendMessage(ChatColor.GOLD + "Use /eggwars edit " + args[1] + " to set up the arena.");
                            } else {
                                player.sendMessage(ChatColor.RED + "Usage: /eggwars addarena arenaName");
                            }
                        } else {
                            noPerm(player);
                        }
                        break;
                    case "delarena":
                        if (player.hasPermission("eggwarsreloaded.command.delarena")) {
                            if (args.length > 1) {
                                if (ArenaManager.getArenas().contains(args[1])) {
                                    ArenaManager.deleteArena(args[1]);
                                    player.sendMessage(ChatColor.GOLD + "Deleted arena: " + args[1]);
                                } else {
                                    player.sendMessage(ChatColor.RED + "Sorry a arena with this name doesn't exist.");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Usage: /eggwars delarena arenaName");
                            }
                        } else {
                            noPerm(player);
                        }
                        break;
                    case "kick":
                        if (player.hasPermission("eggwarsreloaded.command.kick")) {
                            if (args.length > 1) {
                                Player target = Bukkit.getPlayer(args[1]);

                                if (target != null) {
                                    RejectType reject = GameControl.kickPlayer(target);

                                    if (reject == RejectType.NOT_IN) {
                                        player.sendMessage(ChatColor.GOLD + "This player is not in a game!");
                                    }
                                } else {
                                    player.sendMessage(ChatColor.GOLD + "This player isn't online!");
                                }
                            }
                        } else {
                            noPerm(player);
                        }
                        break;
                    case "endgame":
                        if (player.hasPermission("eggwarsreloaded.command.endgame")) {
                            if (args.length > 1) {
                                if (ArenaManager.isArenaRegistered(args[1])) {
                                    GameControl.getGame(args[1]).endGame();
                                } else {
                                    player.sendMessage(ChatColor.GOLD + "Sorry a arena with this name doesn't exist.");
                                }
                            }
                        } else {
                            noPerm(player);
                        }
                        break;
                    case "edit":
                        if (player.hasPermission("eggwarsreloaded.command.edit")) {
                            if (args.length > 1) {
                                if (ArenaManager.getArenas().isSet(args[1])) {
                                    EditMenu.openEditMenu(args[1], player, plugin);
                                } else {
                                    player.sendMessage(ChatColor.RED + "Sorry a arena with this name doesn't exist.");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Usage: /eggwars edit arenaName");
                            }
                        } else {
                            noPerm(player);
                        }
                        break;
                    default:
                        return false;
                }
            } else {
                return false;
            }
        }

        // Console support soon?
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.GOLD + "Sorry you need to run this command ingame.");
        }

        return true;
    }

    private void noPerm(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You have no permission!");
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
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
                    Set<String> arenas = ArenaManager.getArenas().getKeys(false);

                    StringUtil.copyPartialMatches(args[1], arenas, completions);
                    Collections.sort(completions);
                } catch (NullPointerException ignored) {
                    // ignored
                }
            }

            if (player.hasPermission("eggwarsreloaded.command.delarena") && args[0].equals("delarena") && args.length == 2) {
                try {
                    Set<String> arenas = ArenaManager.getArenas().getKeys(false);

                    StringUtil.copyPartialMatches(args[1], arenas, completions);
                    Collections.sort(completions);
                } catch (NullPointerException ignored) {
                    // ignored
                }
            }

            if (player.hasPermission("eggwarsreloaded.command.join") && args[0].equals("join") && args.length == 2) {
                StringUtil.copyPartialMatches(args[1], GameControl.getRegisteredArenas(), completions);
                Collections.sort(completions);
            }

            if (player.hasPermission("eggwarsreloaded.command.endgame") && args[0].equals("endgame") && args.length == 2) {
                StringUtil.copyPartialMatches(args[1], GameControl.getRegisteredArenas(), completions);
                Collections.sort(completions);
            }
        }

        // Console support soon?

        return completions;
    }
}