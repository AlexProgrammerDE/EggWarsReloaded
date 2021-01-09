package me.alexprogrammerde.EggWarsReloaded.admin.guis;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.admin.assistants.GeneratorAssistant;
import me.alexprogrammerde.EggWarsReloaded.game.collection.TeamColor;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.ItemBuilder;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import me.alexprogrammerde.EggWarsReloaded.utils.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditMenu {

    public static void openEditMenu(String arenaName, Player player) {
        FileConfiguration items = EggWarsReloaded.get().getItems();
        FileConfiguration arenas = ArenaManager.getArenas();

        // Load Data from storage
        ItemBuilder mainLobby = new ItemBuilder(Material.GREEN_CONCRETE);
        ItemBuilder waitingLobby = new ItemBuilder(Material.RED_CONCRETE);
        ItemBuilder spectator = new ItemBuilder(Material.YELLOW_CONCRETE);
        ItemBuilder register = new ItemBuilder(Material.END_CRYSTAL);
        ItemBuilder teams = new ItemBuilder(Material.WHITE_WOOL);
        ItemBuilder generators = new ItemBuilder(Material.DIAMOND_BLOCK);
        ItemBuilder size = new ItemBuilder(Material.ENDER_PEARL);
        ItemBuilder save = new ItemBuilder(Material.CRAFTING_TABLE);
        ItemBuilder pos1;
        ItemBuilder pos2;

        if (arenas.contains(arenaName + ".pos1")) {
            pos1 = new ItemBuilder(Material.GOLD_INGOT);
        } else {
            pos1 = new ItemBuilder(Material.IRON_INGOT);
        }

        if (arenas.contains(arenaName + ".pos2")) {
            pos2 = new ItemBuilder(Material.GOLD_INGOT);
        } else {
            pos2 = new ItemBuilder(Material.IRON_INGOT);
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
                    + Math.round(location.getBlockX()) + " "
                    + Math.round(location.getBlockY()) + " "
                    + Math.round(location.getBlockZ()));
        }

        if (ArenaManager.getArenas().contains(arenaName + ".waitinglobby")) {
            Location location = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".waitinglobby"));
            waitingLobby.lore("Current: " + location.getWorld().getName() + " "
                    + Math.round(location.getBlockX()) + " "
                    + Math.round(location.getBlockY()) + " "
                    + Math.round(location.getBlockZ()));
        }

        if (ArenaManager.getArenas().contains(arenaName + ".spectator")) {
            Location location = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".spectator"));
            spectator.lore("Current: " + location.getWorld().getName() + " "
                    + Math.round(location.getBlockX()) + " "
                    + Math.round(location.getBlockY()) + " "
                    + Math.round(location.getBlockZ()));
        }

        if (ArenaManager.getArenas().contains(arenaName + ".pos1")) {
            Location location = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".pos1"));
            pos1.lore("Current: " + location.getWorld().getName() + " "
                    + Math.round(location.getBlockX()) + " "
                    + Math.round(location.getBlockY()) + " "
                    + Math.round(location.getBlockZ()));
        }

        if (ArenaManager.getArenas().contains(arenaName + ".pos2")) {
            Location location = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".pos2"));
            pos2.lore("Current: " + location.getWorld().getName() + " "
                    + Math.round(location.getBlockX()) + " "
                    + Math.round(location.getBlockY()) + " "
                    + Math.round(location.getBlockZ()));
        }

        size.lore("Current: " + ArenaManager.getTeamSize(arenaName));

        mainLobby.lore("Use shift + left click to reset it.");
        waitingLobby.lore("Use shift + left click to reset it.");
        spectator.lore("Use shift + left click to reset it.");
        pos1.lore("Use shift + left click to reset it.");
        pos2.lore("Use shift + left click to reset it.");

        GUI gui = new GUI(arenaName, 3, EggWarsReloaded.get(), player);

        gui.addItem(mainLobby.build(), items.getInt("items.editmain.mainlobby.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    if (arenas.contains(arenaName + ".mainlobby")) {
                        ArenaManager.setMainLobby(arenaName, null);
                        ArenaManager.setArenaRegistered(arenaName, false, null);

                        player.sendMessage("Reset main lobby.");
                    }

                    EditMenu.openEditMenu(arenaName, player);
                })
                .addDefaultEvent(() -> {
                    if (ArenaManager.getArenas().contains(arenaName + ".mainlobby")) {
                        player.sendMessage("Sorry this is already set. Please reset it with: shift + click");
                    } else {
                        ArenaManager.setMainLobby(arenaName, player.getLocation());

                        player.sendMessage("Set main lobby.");
                    }

                    EditMenu.openEditMenu(arenaName, player);
                });

        gui.addItem(waitingLobby.build(), items.getInt("items.editmain.waitinglobby.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    if (arenas.contains(arenaName + ".waitinglobby")) {
                        ArenaManager.setWaitingLobby(arenaName, null);
                        ArenaManager.setArenaRegistered(arenaName, false, null);

                        player.sendMessage("Reset waiting lobby.");
                    }

                    EditMenu.openEditMenu(arenaName, player);
                })
                .addDefaultEvent(() -> {
                    if (ArenaManager.getArenas().contains(arenaName + ".waitinglobby")) {
                        player.sendMessage("Sorry this is already set. Please reset it with: shift + click");
                    } else {
                        ArenaManager.setWaitingLobby(arenaName, player.getLocation());
                        player.sendMessage("Set waiting lobby.");
                    }

                    EditMenu.openEditMenu(arenaName, player);
                });

        gui.addItem(spectator.build(), items.getInt("items.editmain.spectator.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    if (arenas.contains(arenaName + ".spectator")) {
                        ArenaManager.setSpectator(arenaName, null);
                        ArenaManager.setArenaRegistered(arenaName, false, null);

                        player.sendMessage("Reset spectator spawn.");
                    }

                    EditMenu.openEditMenu(arenaName, player);
                })
                .addDefaultEvent(() -> {
                    if (ArenaManager.getArenas().contains(arenaName + ".spectator")) {
                        player.sendMessage("Sorry this is already set. Please reset it with: shift + click");
                    } else {
                        ArenaManager.setSpectator(arenaName, player.getLocation());

                        player.sendMessage("Set spectator spawn.");
                    }

                    EditMenu.openEditMenu(arenaName, player);
                });

        gui.addItem(register.build(), items.getInt("items.editmain.register.slot"))
                .addDefaultEvent(() -> {
                    if (ArenaManager.isArenaRegistered(arenaName)) {
                        ArenaManager.setArenaRegistered(arenaName, false, null);

                        player.sendMessage("Unregistered arena: " + arenaName);
                    } else {
                        List<TeamColor> teams1 = new ArrayList<>();

                        for (String team : arenas.getConfigurationSection(arenaName + ".team").getKeys(false)) {
                            if (ArenaManager.isTeamRegistered(arenaName, TeamColor.fromString(team))) {
                                teams1.add(TeamColor.fromString(team));
                            }
                        }

                        if (teams1.size() < 2) {
                            player.sendMessage("You need at least 2 teams registered!");
                        } else {
                            boolean isWrong = false;
                            StringBuilder wrongteams = new StringBuilder();

                            for (TeamColor team : teams1) {
                                List<String> spawns = arenas.getStringList(arenaName + ".team." + team + ".spawn");

                                if (spawns.size() != ArenaManager.getTeamSize(arenaName)) {
                                    isWrong = true;
                                    wrongteams.append(" ").append(team);
                                }
                            }

                            if (isWrong) {
                                player.sendMessage("The following teams have not the set amount of spawns:" + wrongteams);
                            } else {
                                if (arenas.contains(arenaName + ".iron")) {
                                    if (arenas.contains(arenaName + ".gold")) {
                                        if (arenas.contains(arenaName + ".diamond")) {

                                            ArenaManager.setArenaRegistered(arenaName, true, teams1);
                                            player.sendMessage("Registered arena " + arenaName);
                                        } else {
                                            player.sendMessage("You need to set up at least one diamond generator!");
                                        }
                                    } else {
                                        player.sendMessage("You need to set up at least one gold generator!");
                                    }
                                } else {
                                    player.sendMessage("You need to set up at least one iron generator!");
                                }
                            }
                        }
                    }

                    EditMenu.openEditMenu(arenaName, player);
                });

        gui.addItem(teams.build(), items.getInt("items.editmain.teams.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    arenas.set(arenaName + ".team", null);

                    try {
                        EggWarsReloaded.get().getArenas().save(EggWarsReloaded.get().getArenasFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    EggWarsReloaded.get().reloadArenas();
                    EditMenu.openEditMenu(arenaName, player);

                    player.sendMessage("Reset teams of arena: " + arenaName);
                })
                .addDefaultEvent(() -> {
                    TeamMenu.setupTeamMenu(arenaName, player);
                });

        gui.addItem(generators.build(), items.getInt("items.editmain.generators.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    arenas.set(arenaName + ".iron", null);
                    arenas.set(arenaName + ".gold", null);
                    arenas.set(arenaName + ".diamond", null);

                    try {
                        EggWarsReloaded.get().getArenas().save(EggWarsReloaded.get().getArenasFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    EggWarsReloaded.get().reloadArenas();
                    EditMenu.openEditMenu(arenaName, player);
                    player.sendMessage("Reset all generators of arena: " + arenaName);
                })
                .addDefaultEvent(() -> {
                    if (GeneratorAssistant.isAdding(player)) {
                        GeneratorAssistant.removePlayer(player);

                        player.sendMessage("[GeneratorAssistant] You left generator adding mode.");

                        EditMenu.openEditMenu(arenaName, player);
                    } else {
                        new GeneratorAssistant(player, arenaName);

                        player.sendMessage("[GeneratorAssistant] You are in generator adding mode. Left click a iron/gold/diamond block and it gets added to the list.");
                        player.closeInventory();
                    }
                });

        gui.addItem(size.build(), items.getInt("items.editmain.teamsize.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setTeamSize(arenaName, 1);
                    EditMenu.openEditMenu(arenaName, player);
                    player.sendMessage("Reset teamsize to 1 of arena: " + arenaName);
                })
                .addDefaultEvent(() -> {
                    int size1 = ArenaManager.getTeamSize(arenaName);

                    if (size1 == 4) {
                        ArenaManager.setTeamSize(arenaName, 1);
                        ArenaManager.setArenaRegistered(arenaName, false, null);
                        player.sendMessage("Changed the team size and unregistered the arena.");
                    } else {
                        ArenaManager.setTeamSize(arenaName, size1 + 1);
                    }

                    EditMenu.openEditMenu(arenaName, player);
                });

        gui.addItem(pos1.build(), items.getInt("items.editmain.pos1.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setArenaPos1(arenaName, null);

                    EditMenu.openEditMenu(arenaName, player);
                    player.sendMessage("Reset pos1");
                })
                .addDefaultEvent(() -> {
                    ArenaManager.setArenaPos1(arenaName, player.getLocation());

                    EditMenu.openEditMenu(arenaName, player);
                });

        gui.addItem(pos2.build(), items.getInt("items.editmain.pos2.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setArenaPos2(arenaName, null);

                    EditMenu.openEditMenu(arenaName, player);
                    player.sendMessage("Reset pos2");
                })
                .addDefaultEvent(() -> {
                    ArenaManager.setArenaPos2(arenaName, player.getLocation());

                    EditMenu.openEditMenu(arenaName, player);
                });

        gui.addItem(save.build(), items.getInt("items.editmain.save.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    player.teleport(new Location(Bukkit.getWorld(ArenaManager.getArenaWorld(arenaName)), 0, 81, 0));

                    EditMenu.openEditMenu(arenaName, player);
                })
                .addDefaultEvent(() -> {
                    Bukkit.getWorld(ArenaManager.getArenaWorld(arenaName)).save();

                    EditMenu.openEditMenu(arenaName, player);
                });

        gui.openGUI();
    }
}
