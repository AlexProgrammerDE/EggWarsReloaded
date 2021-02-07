package net.pistonmaster.eggwarsreloaded.admin.guis;

import net.md_5.bungee.api.ChatColor;
import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import net.pistonmaster.eggwarsreloaded.admin.assistants.GeneratorAssistant;
import net.pistonmaster.eggwarsreloaded.game.Game;
import net.pistonmaster.eggwarsreloaded.game.collection.TeamColor;
import net.pistonmaster.eggwarsreloaded.utils.ArenaManager;
import net.pistonmaster.eggwarsreloaded.utils.ItemBuilder;
import net.pistonmaster.eggwarsreloaded.utils.UtilCore;
import net.pistonmaster.eggwarsreloaded.utils.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditMenu {
    private static final String PREFIX = ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "SetupAssistant" + ChatColor.GOLD + "] ";

    private EditMenu() {

    }

    public static void openEditMenu(String arenaName, Player player, EggWarsReloaded plugin) {
        FileConfiguration items = plugin.getItems();
        FileConfiguration arenas = ArenaManager.getArenas();

        // Load Data from storage
        ItemBuilder mainLobby = new ItemBuilder(XMaterial.GREEN_CONCRETE);
        ItemBuilder waitingLobby = new ItemBuilder(XMaterial.RED_CONCRETE);
        ItemBuilder spectator = new ItemBuilder(XMaterial.YELLOW_CONCRETE);
        ItemBuilder register = new ItemBuilder(XMaterial.END_CRYSTAL);
        ItemBuilder teams = new ItemBuilder(XMaterial.WHITE_WOOL);
        ItemBuilder generators = new ItemBuilder(XMaterial.DIAMOND_BLOCK);
        ItemBuilder size = new ItemBuilder(XMaterial.ENDER_PEARL);
        ItemBuilder save = new ItemBuilder(XMaterial.CRAFTING_TABLE);
        ItemBuilder pos1;
        ItemBuilder pos2;

        if (arenas.contains(arenaName + ".pos1")) {
            pos1 = new ItemBuilder(XMaterial.GOLD_INGOT);
        } else {
            pos1 = new ItemBuilder(XMaterial.IRON_INGOT);
        }

        if (arenas.contains(arenaName + ".pos2")) {
            pos2 = new ItemBuilder(XMaterial.GOLD_INGOT);
        } else {
            pos2 = new ItemBuilder(XMaterial.IRON_INGOT);
        }

        // Give item names from items.yml
        mainLobby.name(items.getString("items.editmain.mainlobby.name"));
        waitingLobby.name(items.getString("items.editmain.waitinglobby.name"));
        spectator.name(items.getString("items.editmain.spectator.name"));
        register.name(items.getString("items.editmain.register.name"));
        teams.name(items.getString("items.editmain.teams.name"));
        generators.name(items.getString("items.editmain.generators.name"));
        size.name(items.getString("items.editmain.teamsize.name"));
        pos1.name(items.getString("items.editmain.pos1.name"));
        pos2.name(items.getString("items.editmain.pos2.name"));
        save.name(items.getString("items.editmain.save.name"));

        if (ArenaManager.getArenas().contains(arenaName + ".mainlobby")) {
            mainLobby.enchant();
        }

        if (ArenaManager.getArenas().contains(arenaName + ".waitinglobby")) {
            waitingLobby.enchant();
        }

        if (ArenaManager.getArenas().contains(arenaName + ".spectator")) {
            spectator.enchant();
        }

        if (ArenaManager.getArenas().contains(arenaName + ".register")) {
            spectator.enchant();
        }

        if (GeneratorAssistant.isAdding(player)) {
            generators.enchant();
        }

        if (ArenaManager.getArenas().contains(arenaName + ".pos1")) {
            pos1.enchant();
        }

        if (ArenaManager.getArenas().contains(arenaName + ".pos2")) {
            pos2.enchant();
        }

        mainLobby.lore("Left click to set the main lobby.");
        waitingLobby.lore("Left click to set the waiting lobby.");
        spectator.lore("Left click to set the spectator spawn.");
        teams.lore("Left click to edit all teams.");
        size.lore("Click to increase teamsize. 4 is the maximum!");
        pos1.lore("Left click to set your position to pos1.");
        pos2.lore("Left click to set your position to pos2.");

        if (arenas.getBoolean(arenaName + ".registered")) {
            register.lore("Left click to unregister the arena.");
        } else {
            if (ArenaManager.isArenaReady(arenaName)) {
                register.lore("Left click to register the arena");
            } else {
                register.lore("There are still some steps left!");
            }
        }

        if (ArenaManager.getArenas().contains(arenaName + ".mainlobby")) {
            Location location = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".mainlobby"));

            mainLobby.lore("Current: " + location.getWorld().getName() + " "
                    + location.getBlockX() + " "
                    + location.getBlockY() + " "
                    + location.getBlockZ());
        }

        if (ArenaManager.getArenas().contains(arenaName + ".waitinglobby")) {
            Location location = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".waitinglobby"));
            waitingLobby.lore("Current: " + location.getWorld().getName() + " "
                    + location.getBlockX() + " "
                    + location.getBlockY() + " "
                    + location.getBlockZ());
        }

        if (ArenaManager.getArenas().contains(arenaName + ".spectator")) {
            Location location = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".spectator"));
            spectator.lore("Current: " + location.getWorld().getName() + " "
                    + location.getBlockX() + " "
                    + location.getBlockY() + " "
                    + location.getBlockZ());
        }

        if (ArenaManager.getArenas().contains(arenaName + ".pos1")) {
            Location location = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".pos1"));
            pos1.lore("Current: " + location.getWorld().getName() + " "
                    + location.getBlockX() + " "
                    + location.getBlockY() + " "
                    + location.getBlockZ());
        }

        if (ArenaManager.getArenas().contains(arenaName + ".pos2")) {
            Location location = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".pos2"));
            pos2.lore("Current: " + location.getWorld().getName() + " "
                    + location.getBlockX() + " "
                    + location.getBlockY() + " "
                    + location.getBlockZ());
        }

        size.lore("Current: " + ArenaManager.getTeamSize(arenaName));

        mainLobby.lore("Use shift + left click to reset it.");
        waitingLobby.lore("Use shift + left click to reset it.");
        spectator.lore("Use shift + left click to reset it.");
        pos1.lore("Use shift + left click to reset it.");
        pos2.lore("Use shift + left click to reset it.");

        GUI gui = new GUI(arenaName, 3, plugin, player);

        gui.addItem(mainLobby.build(), items.getInt("items.editmain.mainlobby.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    if (arenas.contains(arenaName + ".mainlobby")) {
                        ArenaManager.setMainLobby(arenaName, null);
                        ArenaManager.setArenaRegistered(arenaName, false, null);

                        player.sendMessage(PREFIX + "Reset main lobby.");
                    }

                    EditMenu.openEditMenu(arenaName, player, plugin);
                })
                .addDefaultEvent(() -> {
                    if (ArenaManager.getArenas().contains(arenaName + ".mainlobby")) {
                        player.sendMessage(PREFIX + "Sorry this is already set. Please reset it with: shift + click");
                    } else {
                        ArenaManager.setMainLobby(arenaName, player.getLocation());

                        player.sendMessage(PREFIX + "Set main lobby.");
                    }

                    EditMenu.openEditMenu(arenaName, player, plugin);
                });

        gui.addItem(waitingLobby.build(), items.getInt("items.editmain.waitinglobby.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    if (arenas.contains(arenaName + ".waitinglobby")) {
                        ArenaManager.setWaitingLobby(arenaName, null);
                        ArenaManager.setArenaRegistered(arenaName, false, null);

                        player.sendMessage(PREFIX + "Reset waiting lobby.");
                    }

                    EditMenu.openEditMenu(arenaName, player, plugin);
                })
                .addDefaultEvent(() -> {
                    if (ArenaManager.getArenas().contains(arenaName + ".waitinglobby")) {
                        player.sendMessage(PREFIX + "Sorry this is already set. Please reset it with: shift + click");
                    } else {
                        ArenaManager.setWaitingLobby(arenaName, player.getLocation());
                        player.sendMessage(PREFIX + "Set waiting lobby.");
                    }

                    EditMenu.openEditMenu(arenaName, player, plugin);
                });

        gui.addItem(spectator.build(), items.getInt("items.editmain.spectator.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    if (arenas.contains(arenaName + ".spectator")) {
                        ArenaManager.setSpectator(arenaName, null);
                        ArenaManager.setArenaRegistered(arenaName, false, null);

                        player.sendMessage(PREFIX + "Reset spectator spawn.");
                    }

                    EditMenu.openEditMenu(arenaName, player, plugin);
                })
                .addDefaultEvent(() -> {
                    if (ArenaManager.getArenas().contains(arenaName + ".spectator")) {
                        player.sendMessage(PREFIX + "Sorry this is already set. Please reset it with: shift + click");
                    } else {
                        ArenaManager.setSpectator(arenaName, player.getLocation());

                        player.sendMessage(PREFIX + "Set spectator spawn.");
                    }

                    EditMenu.openEditMenu(arenaName, player, plugin);
                });

        gui.addItem(register.build(), items.getInt("items.editmain.register.slot"))
                .addDefaultEvent(() -> {
                    if (ArenaManager.isArenaRegistered(arenaName)) {
                        ArenaManager.setArenaRegistered(arenaName, false, null);

                        player.sendMessage(PREFIX + "Unregistered arena: " + arenaName);
                    } else {
                        List<TeamColor> teams1 = new ArrayList<>();

                        for (String team : Objects.requireNonNull(arenas.getConfigurationSection(arenaName + ".team")).getKeys(false)) {
                            if (ArenaManager.isTeamRegistered(arenaName, TeamColor.fromString(team))) {
                                teams1.add(TeamColor.fromString(team));
                            }
                        }

                        if (teams1.size() < 2) {
                            player.sendMessage(PREFIX + "You need at least 2 teams registered!");
                        } else {
                            boolean isWrong = false;
                            StringBuilder wrongTeams = new StringBuilder();

                            for (TeamColor team : teams1) {
                                List<String> spawns = arenas.getStringList(arenaName + ".team." + team + ".spawn");

                                if (spawns.size() != ArenaManager.getTeamSize(arenaName)) {
                                    isWrong = true;
                                    wrongTeams.append(" ").append(team);
                                }
                            }

                            if (isWrong) {
                                player.sendMessage(PREFIX + "The following teams have not the set amount of spawns:" + wrongTeams);
                            } else {
                                if (arenas.contains(arenaName + ".iron")) {
                                    if (arenas.contains(arenaName + ".gold")) {
                                        if (arenas.contains(arenaName + ".diamond")) {

                                            ArenaManager.setArenaRegistered(arenaName, true, teams1);
                                            new Game(arenaName, plugin);
                                            player.sendMessage(PREFIX + "Registered arena " + arenaName);
                                        } else {
                                            player.sendMessage(PREFIX + "You need to set up at least one diamond generator!");
                                        }
                                    } else {
                                        player.sendMessage(PREFIX + "You need to set up at least one gold generator!");
                                    }
                                } else {
                                    player.sendMessage(PREFIX + "You need to set up at least one iron generator!");
                                }
                            }
                        }
                    }

                    EditMenu.openEditMenu(arenaName, player, plugin);
                });

        gui.addItem(teams.build(), items.getInt("items.editmain.teams.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    arenas.set(arenaName + ".team", null);

                    try {
                        plugin.getArenaConfig().save(plugin.getArenasFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    plugin.loadConfig();
                    EditMenu.openEditMenu(arenaName, player, plugin);

                    player.sendMessage(PREFIX + "Reset teams of arena: " + arenaName);
                })
                .addDefaultEvent(() -> TeamMenu.setupTeamMenu(arenaName, player, plugin));

        gui.addItem(generators.build(), items.getInt("items.editmain.generators.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    arenas.set(arenaName + ".iron", null);
                    arenas.set(arenaName + ".gold", null);
                    arenas.set(arenaName + ".diamond", null);

                    try {
                        plugin.getArenaConfig().save(plugin.getArenasFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    plugin.loadConfig();
                    EditMenu.openEditMenu(arenaName, player, plugin);
                    player.sendMessage(PREFIX + "Reset all generators of arena: " + arenaName);
                })
                .addDefaultEvent(() -> {
                    if (GeneratorAssistant.isAdding(player)) {
                        GeneratorAssistant.removePlayer(player);

                        player.sendMessage(GeneratorAssistant.PREFIX + "You left generator adding mode.");

                        EditMenu.openEditMenu(arenaName, player, plugin);
                    } else {
                        new GeneratorAssistant(player, arenaName, plugin);

                        player.sendMessage(GeneratorAssistant.PREFIX + "You are in generator adding mode. Left click a iron/gold/diamond block and it gets added to the list.");
                        player.closeInventory();
                    }
                });

        gui.addItem(size.build(), items.getInt("items.editmain.teamsize.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setTeamSize(arenaName, 1);
                    EditMenu.openEditMenu(arenaName, player, plugin);
                    player.sendMessage(PREFIX + "Reset teamsize to 1 of arena: " + arenaName);
                })
                .addDefaultEvent(() -> {
                    int size1 = ArenaManager.getTeamSize(arenaName);

                    if (size1 == 4) {
                        ArenaManager.setTeamSize(arenaName, 1);
                        ArenaManager.setArenaRegistered(arenaName, false, null);
                        player.sendMessage(PREFIX + "Changed the team size and unregistered the arena.");
                    } else {
                        ArenaManager.setTeamSize(arenaName, size1 + 1);
                    }

                    EditMenu.openEditMenu(arenaName, player, plugin);
                });

        gui.addItem(pos1.build(), items.getInt("items.editmain.pos1.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setArenaPos1(arenaName, null);

                    EditMenu.openEditMenu(arenaName, player, plugin);
                    player.sendMessage(PREFIX + "Reset pos1");
                })
                .addDefaultEvent(() -> {
                    ArenaManager.setArenaPos1(arenaName, player.getLocation());

                    EditMenu.openEditMenu(arenaName, player, plugin);
                });

        gui.addItem(pos2.build(), items.getInt("items.editmain.pos2.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setArenaPos2(arenaName, null);

                    EditMenu.openEditMenu(arenaName, player, plugin);
                    player.sendMessage(PREFIX + "Reset pos2");
                })
                .addDefaultEvent(() -> {
                    ArenaManager.setArenaPos2(arenaName, player.getLocation());

                    EditMenu.openEditMenu(arenaName, player, plugin);
                });

        gui.addItem(save.build(), items.getInt("items.editmain.save.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    player.teleport(Objects.requireNonNull(Bukkit.getWorld(ArenaManager.getArenaWorld(arenaName))).getSpawnLocation());

                    EditMenu.openEditMenu(arenaName, player, plugin);
                })
                .addDefaultEvent(() -> {
                    Objects.requireNonNull(Bukkit.getWorld(ArenaManager.getArenaWorld(arenaName))).save();

                    EditMenu.openEditMenu(arenaName, player, plugin);
                });

        gui.openGUI();
    }
}
