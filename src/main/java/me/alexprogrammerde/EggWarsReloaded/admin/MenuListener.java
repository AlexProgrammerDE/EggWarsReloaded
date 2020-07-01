package me.alexprogrammerde.EggWarsReloaded.admin;

import me.alexprogrammerde.EggWarsReloaded.EggWarsMain;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getClickedInventory();
            Inventory[] foundarr = new Inventory[0];
            FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

            boolean isedit = false;
            int index = 0;

            if (EditManager.inventorymap.containsKey(player)) {
                for (Inventory[] arr : EditManager.menus.keySet()) {
                    for (Inventory inv : arr) {
                        if (Objects.equals(inventory, inv)) {
                            isedit = true;
                            break;
                        }
                    }

                    if (isedit) {
                        index = Arrays.asList(arr).indexOf(inventory);
                        foundarr = arr;
                        break;
                    }
                }

                if (isedit) {
                    int slot = event.getSlot();

                    FileConfiguration items = EggWarsMain.getEggWarsMain().getItems();

                    String arenaname = EditManager.menus.get(foundarr);

                    event.setCancelled(true);

                    // Check the inventory array index and execute actions
                    if (index == 0) {
                        if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                            if (arenas.contains("arenas." + arenaname + ".mainlobby") && items.getInt("items.editmain.mainlobby.slot") == slot) {
                                ArenaManager.setMainLobby(arenaname, null);
                                EditMenu.setupEditMenu(inventory, arenaname, player);
                                player.sendMessage("Reseted main lobby.");
                            } else if (arenas.contains("arenas." + arenaname + ".waitinglobby") && items.getInt("items.editmain.waitinglobby.slot") == slot) {
                                ArenaManager.setWaitingLobby(arenaname, null);
                                EditMenu.setupEditMenu(inventory, arenaname, player);
                                player.sendMessage("Reseted waiting lobby.");
                            } else if (arenas.contains("arenas." + arenaname + ".spectator") && items.getInt("items.editmain.spectator.slot") == slot) {
                                ArenaManager.setSpectator(arenaname, null);
                                EditMenu.setupEditMenu(inventory, arenaname, player);
                                player.sendMessage("Reseted spectator spawn.");
                            } else {
                                player.sendMessage("It seems like that item wasn't set.");
                            }
                        } else {
                            if (items.getInt("items.editmain.mainlobby.slot") == slot) {
                                if (ArenaManager.getArenas().contains("arenas." + arenaname + "mainlobby")) {
                                    player.sendMessage("Sorry this is already set. Please reset it with: shift + click");
                                } else {
                                    ArenaManager.setMainLobby(arenaname, player.getLocation());
                                    EditMenu.setupEditMenu(inventory, arenaname, player);
                                    player.sendMessage("Set main lobby.");
                                }
                            } else if (items.getInt("items.editmain.waitinglobby.slot") == slot) {
                                if (ArenaManager.getArenas().contains("arenas." + arenaname + "waitinglobby")) {
                                    player.sendMessage("Sorry this is already set. Please reset it with: shift + click");
                                } else {
                                    ArenaManager.setWaitingLobby(arenaname, player.getLocation());
                                    EditMenu.setupEditMenu(inventory, arenaname, player);
                                    player.sendMessage("Set waiting lobby.");
                                }
                            } else if (items.getInt("items.editmain.spectator.slot") == slot) {
                                if (ArenaManager.getArenas().contains("arenas." + arenaname + "spectator")) {
                                    player.sendMessage("Sorry this is already set. Please reset it with: shift + click");
                                } else {
                                    ArenaManager.setSpectator(arenaname, player.getLocation());
                                    EditMenu.setupEditMenu(inventory, arenaname, player);
                                    player.sendMessage("Set spectator spawn.");
                                }
                            } else if (items.getInt("items.editmain.teams.slot") == slot) {
                                // Open the inv
                                TeamMenu.setupTeamMenu(foundarr[1], arenaname);
                                player.openInventory(foundarr[1]);
                            } else if (items.getInt("items.editmain.register.slot") == slot) {
                                if (ArenaManager.isArenaRegistered(arenaname)) {
                                    ArenaManager.setArenaRegistered(arenaname, false, null);
                                    player.sendMessage("Unregistered arena: " + arenaname);
                                } else {
                                    List<String> teams = new ArrayList<>();

                                    for (String team : arenas.getConfigurationSection("arenas." + arenaname + ".team").getKeys(false)) {
                                        if (ArenaManager.isTeamRegistered(arenaname, team)) {
                                            teams.add(team);
                                        }
                                    }

                                    if (teams.size() < 2) {
                                        player.sendMessage("You need at least 2 teams registered!");
                                    } else {
                                        boolean isWrong = false;
                                        String wrongteams = "";

                                        for (String team : teams) {
                                            List<String> spawns = arenas.getStringList("arenas." + arenaname + ".team." + team + ".spawn");

                                            if (spawns.size() != ArenaManager.getTeamSize(team)) {
                                                isWrong = true;
                                                wrongteams = " " + team;
                                            }
                                        }

                                        if (isWrong) {
                                            player.sendMessage("The following teams have not the set amount of spawns:" + wrongteams);
                                        } else {
                                            if (arenas.contains("arenas." + arenaname + ".iron")) {
                                                if (arenas.contains("arenas." + arenaname + ".gold")) {
                                                    if (arenas.contains("arenas." + arenaname + ".diamond")) {

                                                        ArenaManager.setArenaRegistered(arenaname, true, teams);
                                                        player.sendMessage("Registered arena " + arenaname);
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
                            } else if (items.getInt("items.editmain.generators.slot") == slot) {
                                if (GeneratorAssistant.shouldTouchBlock.containsKey(player)) {
                                    GeneratorAssistant.shouldTouchBlock.remove(player);
                                    player.sendMessage("[GeneratorAssistant] You left generator adding mode.");
                                } else {
                                    GeneratorAssistant.shouldTouchBlock.put(player, true);
                                    GeneratorAssistant.playerdata.put(player, arenaname);
                                    player.sendMessage("[GeneratorAssistant] You are in generator adding mode. Left click a iron/gold/diamond block and it gets added to the list.");
                                }
                            } else if (items.getInt("items.editmain.teamsize.slot") == slot) {
                                int size = ArenaManager.getTeamSize(arenaname);

                                if (size == 4) {
                                    ArenaManager.setTeamSize(arenaname, 1);
                                    ArenaManager.setArenaRegistered(arenaname, false, null);
                                    player.sendMessage("Changed the team size and unregistered the arena.");
                                } else {
                                    ArenaManager.setTeamSize(arenaname, size + 1);
                                }

                                EditMenu.setupEditMenu(inventory, arenaname, player);
                            }
                        }
                    } else if (index == 1) {
                        boolean isButton = false;
                        boolean isTeam = false;
                        String teamname = "";

                        for (String key : items.getConfigurationSection("items.editteams").getKeys(false)) {
                            if (items.getInt("items.editteams." + key + ".slot") == slot) {
                                if (key.equals("back")) {
                                    player.openInventory(foundarr[0]);
                                } else {
                                    teamname = key;
                                    isTeam = true;
                                }

                                break;
                            }

                            if (items.contains("items.editteams." + key + ".buttonslot") && items.getInt("items.editteams." + key + ".buttonslot") == slot) {
                                teamname = key;
                                isButton = true;
                                break;
                            }
                        }

                        if (isTeam) {
                            Inventory undermenu = Bukkit.createInventory(player, 9 * 3, teamname);
                            TeamUnderMenu.setupTeamUnderMenu(undermenu, arenaname, teamname);

                            EditManager.inventorymap.get(player)[2] = undermenu;

                            EditManager.playerteam.put(player, teamname);

                            EggAssistant.setPlayer(player, arenaname, teamname, "none");

                            player.openInventory(undermenu);
                        }

                        if (isButton) {
                            if (ArenaManager.isTeamReady(arenaname, teamname)) {
                                if (ArenaManager.isTeamRegistered(arenaname, teamname)) {
                                    ArenaManager.setTeamRegistered(arenaname, teamname, false);
                                    TeamMenu.setupTeamMenu(inventory, arenaname);
                                } else {
                                    ArenaManager.setTeamRegistered(arenaname, teamname, true);
                                    TeamMenu.setupTeamMenu(inventory, arenaname);
                                    player.sendMessage("Registered team: " + teamname);
                                }
                            } else {
                                player.sendMessage("This team is not ready! Please finish the team setup. (Click the wool)");
                            }
                        }
                    } else if (index == 2) {
                        String teamname = EggAssistant.setup.get(player)[1];

                        if (items.getInt("items.editteam.egg.slot") == slot) {
                            if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                                ArenaManager.setEgg(arenaname, teamname, null);
                                TeamUnderMenu.setupTeamUnderMenu(inventory, arenaname, teamname);
                                ArenaManager.setTeamRegistered(arenaname, teamname, false);
                                ArenaManager.setArenaRegistered(arenaname, false, null);

                                // TODO Remove the egg on reset
                                if (ArenaManager.isArenaRegistered(arenaname) && ArenaManager.isTeamRegistered(arenaname, teamname)) {
                                    player.sendMessage("Reseted the egg and unregistered the team and the arena. Remove the egg manually in creative mode.");
                                } if (ArenaManager.isTeamRegistered(arenaname, teamname)) {
                                    player.sendMessage("Reseted the egg and unregistered the team. Remove the egg manually in creative mode.");
                                } else if (ArenaManager.isArenaRegistered(arenaname)) {
                                    player.sendMessage("Reseted the egg and unregistered the arena. Remove the egg manually in creative mode.");
                                }
                            } else {
                                if (arenas.contains("arenas." + arenaname + ".team." + teamname + ".egg")) {
                                    player.sendMessage("The egg is already set. Please reset it first.");
                                } else {
                                    EggAssistant.setPlayer(player, arenaname, teamname, "egg");
                                    player.closeInventory();
                                    player.sendMessage("[EggAssistant] You can now click a dragon egg to add it to the team.");
                                }
                            }
                        }

                        if (items.getInt("items.editteam.shop.slot") == slot) {
                            if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                                ArenaManager.setShop(arenaname, teamname, null, null);
                                TeamUnderMenu.setupTeamUnderMenu(inventory, arenaname, teamname);

                                ArenaManager.setTeamRegistered(arenaname, teamname, false);
                                ArenaManager.setArenaRegistered(arenaname, false, null);

                                // TODO Kill the villager at reset
                                if (ArenaManager.isArenaRegistered(arenaname) && ArenaManager.isTeamRegistered(arenaname, teamname)) {
                                    player.sendMessage("Reseted the shop and unregistered the team and the arena. Use /kill to remove the villager.");
                                } if (ArenaManager.isTeamRegistered(arenaname, teamname)) {
                                    player.sendMessage("Reseted the shop and unregistered the team. Use /kill to remove the villager.");
                                } else if (ArenaManager.isArenaRegistered(arenaname)) {
                                    player.sendMessage("Reseted the shop and unregistered the arena. Use /kill to remove the villager.");
                                }
                            } else {
                                if (arenas.contains("arenas." + arenaname + ".team." + teamname + ".shop")) {
                                    player.sendMessage("The shop is already set. Please reset it first.");
                                } else {
                                    String[] data = new String[2];
                                    data[0] = arenaname;
                                    data[1] = teamname;

                                    ShopAssistant.shouldCreateShop.put(player, true);
                                    ShopAssistant.playerdata.put(player, data);

                                    player.closeInventory();
                                    player.sendMessage("[ShopAssistant] Click a armor stand to make him to a shop.");
                                }
                            }

                        }

                        if (items.getInt("items.editteam.spawn.slot") == slot) {
                            if (arenas.getStringList("arenas." + arenaname + ".team." + teamname + ".spawn").size() < 4) {
                                if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                                    ArenaManager.setTeamRegistered(arenaname, teamname, false);
                                    ArenaManager.setArenaRegistered(arenaname, false, null);
                                    ArenaManager.setSpawn(arenaname, teamname, null);

                                    if (ArenaManager.isArenaRegistered(arenaname) && ArenaManager.isTeamRegistered(arenaname, teamname)) {
                                        player.sendMessage("Reseted the spawns and unregistered the team and the arena.");
                                    }
                                    if (ArenaManager.isTeamRegistered(arenaname, teamname)) {
                                        player.sendMessage("Reseted the spawns and unregistered the team.");
                                    } else if (ArenaManager.isArenaRegistered(arenaname)) {
                                        player.sendMessage("Reseted the spawns and unregistered the arena.");
                                    }

                                    TeamUnderMenu.setupTeamUnderMenu(inventory, arenaname, teamname);
                                } else {
                                    Location playerlocation = player.getLocation();
                                    Block ground = player.getWorld().getBlockAt(new Location(playerlocation.getWorld(), playerlocation.getBlockX(), playerlocation.getBlockY() - 1, playerlocation.getBlockZ()));

                                    if (ground.getType().equals(Material.EMERALD_BLOCK)) {
                                        List<String> strings = arenas.getStringList("arenas." + arenaname + ".team." + teamname + ".spawn");
                                        List<Location> locations = new ArrayList<>();

                                        for (String location : strings) {
                                            String[] data = location.split(" ");
                                            Location loc = new Location(player.getWorld(), 0, 0, 0, 0, 0);

                                            loc.setWorld(Bukkit.getWorld(data[0]));
                                            loc.setX(Double.parseDouble(data[1]));
                                            loc.setY(Double.parseDouble(data[2]));
                                            loc.setZ(Double.parseDouble(data[3]));
                                            loc.setYaw(Float.parseFloat(data[4]));
                                            loc.setPitch(Float.parseFloat(data[5]));

                                            locations.add(loc);
                                        }

                                        if (!locations.contains(playerlocation)) {
                                            strings.add(playerlocation.getWorld().getName() + " " + playerlocation.getX() + " " + playerlocation.getY() + " " + playerlocation.getZ() + " " + playerlocation.getYaw() + " " + playerlocation.getPitch());

                                            arenas.set("arenas." + arenaname + ".team." + teamname + ".spawn", strings);

                                            try {
                                                EggWarsMain.getEggWarsMain().getArenas().save(EggWarsMain.getEggWarsMain().getArenasFile());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            EggWarsMain.getEggWarsMain().reloadArenas();

                                            TeamUnderMenu.setupTeamUnderMenu(inventory, arenaname, teamname);

                                            player.sendMessage("[SpawnAssistant] Added spawn of team " + EditManager.playerteam.get(player) + " to: " + playerlocation.getWorld().getName() + " " + playerlocation.getBlockX() + " " + playerlocation.getBlockY() + " " + playerlocation.getBlockZ());
                                        } else {
                                            player.sendMessage("[SpawnAssistant] This location is already registered!");
                                        }
                                    } else {
                                        player.sendMessage("[SpawnAssistant] The block under needs to be a a emerald block!");
                                    }
                                }
                            } else {
                                player.sendMessage("Sorry 4 spawns are the limit for one team.");
                            }
                        }

                        if (items.getInt("items.editteam.back.slot") == slot) {
                            TeamMenu.setupTeamMenu(foundarr[1], arenaname);
                            player.openInventory(foundarr[1]);
                        }
                    }
                }
            }
        }
    }

    // For later use
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player= event.getPlayer();
    }
}
