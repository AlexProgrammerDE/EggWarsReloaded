package me.alexprogrammerde.EggWarsReloaded.admin.guis;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.admin.assistants.GeneratorAssistant;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import me.alexprogrammerde.EggWarsReloaded.utils.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditMenu {

    public static void openEditMenu(String arenaName, Player player) {
        FileConfiguration items = EggWarsReloaded.getEggWarsMain().getItems();
        FileConfiguration arenas = ArenaManager.getArenas();

        // Load Data from storage
        ItemStack mainLobby = new ItemStack(Material.GREEN_CONCRETE);
        ItemStack waitingLobby = new ItemStack(Material.RED_CONCRETE);
        ItemStack spectator = new ItemStack(Material.YELLOW_CONCRETE);
        ItemStack register = new ItemStack(Material.END_CRYSTAL);
        ItemStack teams = new ItemStack(Material.WHITE_WOOL);
        ItemStack generators = new ItemStack(Material.DIAMOND_BLOCK);
        ItemStack size = new ItemStack(Material.ENDER_PEARL);
        ItemStack save = new ItemStack(Material.CRAFTING_TABLE);
        ItemStack pos1;
        ItemStack pos2;

        if (arenas.contains(arenaName + ".pos1")) {
            pos1 = new ItemStack(Material.GOLD_INGOT);
        } else {
            pos1 = new ItemStack(Material.IRON_INGOT);
        }

        if (arenas.contains(arenaName + ".pos2")) {
            pos2 = new ItemStack(Material.GOLD_INGOT);
        } else {
            pos2 = new ItemStack(Material.IRON_INGOT);
        }

        ItemMeta mainLobbyMeta = mainLobby.getItemMeta();
        ItemMeta waitingLobbyMeta = waitingLobby.getItemMeta();
        ItemMeta spectatorMeta = spectator.getItemMeta();
        ItemMeta registerMeta = spectator.getItemMeta();
        ItemMeta teamsMeta = teams.getItemMeta();
        ItemMeta generatorsMeta = generators.getItemMeta();
        ItemMeta sizeMeta = size.getItemMeta();
        ItemMeta pos1meta = pos1.getItemMeta();
        ItemMeta pos2meta = pos2.getItemMeta();
        ItemMeta saveMeta = save.getItemMeta();

        // Give item names from items.yml
        mainLobbyMeta.setDisplayName(items.getString("items.editmain.mainlobby.name"));
        waitingLobbyMeta.setDisplayName(items.getString("items.editmain.waitinglobby.name"));
        spectatorMeta.setDisplayName(items.getString("items.editmain.spectator.name"));
        registerMeta.setDisplayName(items.getString("items.editmain.register.name"));
        teamsMeta.setDisplayName(items.getString("items.editmain.teams.name"));
        generatorsMeta.setDisplayName(items.getString("items.editmain.generators.name"));
        sizeMeta.setDisplayName(items.getString("items.editmain.teamsize.name"));
        pos1meta.setDisplayName(items.getString("items.editmain.pos1.name"));
        pos2meta.setDisplayName(items.getString("items.editmain.pos2.name"));
        saveMeta.setDisplayName(items.getString("items.editmain.save.name"));

        if (ArenaManager.getArenas().contains(arenaName + ".mainlobby")) {
            mainLobbyMeta.addEnchant(Enchantment.DURABILITY, 0, true);
            mainLobbyMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (ArenaManager.getArenas().contains(arenaName + ".waitinglobby")) {
            waitingLobbyMeta.addEnchant(Enchantment.DURABILITY, 0, true);
            waitingLobbyMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (ArenaManager.getArenas().contains(arenaName + ".spectator")) {
            spectatorMeta.addEnchant(Enchantment.DURABILITY, 0, true);
            spectatorMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (ArenaManager.getArenas().contains(arenaName + ".register")) {
            spectatorMeta.addEnchant(Enchantment.DURABILITY, 0, true);
            spectatorMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (GeneratorAssistant.isAdding(player)) {
            generatorsMeta.addEnchant(Enchantment.DURABILITY, 0, true);
            generatorsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (ArenaManager.getArenas().contains(arenaName + ".pos1")) {
            pos1meta.addEnchant(Enchantment.DURABILITY, 0, true);
            pos1meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (ArenaManager.getArenas().contains(arenaName + ".pos2")) {
            pos2meta.addEnchant(Enchantment.DURABILITY, 0, true);
            pos2meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        List<String> mainlobbylist = new ArrayList<>();
        List<String> waitinglobbylist = new ArrayList<>();
        List<String> spectatorlist = new ArrayList<>();
        List<String> registerlist = new ArrayList<>();
        List<String> teamslist = new ArrayList<>();
        List<String> sizelist = new ArrayList<>();
        List<String> pos1list = new ArrayList<>();
        List<String> pos2list = new ArrayList<>();

        mainlobbylist.add("Left click to set the main lobby.");
        waitinglobbylist.add("Left click to set the waiting lobby.");
        spectatorlist.add("Left click to set the spectator spawn.");
        teamslist.add("Left click to edit all teams.");
        sizelist.add("Click to increase teamsize. 4 is the maximum!");
        pos1list.add("Left click to set your position to pos1.");
        pos2list.add("Left click to set your position to pos2.");

        if (arenas.getBoolean(arenaName + ".registered")) {
            registerlist.add("Left click to unregister the arena.");
        } else {
            if (ArenaManager.isArenaReady(arenaName)) {
                registerlist.add("Left click to register the arena");
            } else {
                registerlist.add("There are still some steps left!");
            }
        }

        if (ArenaManager.getArenas().contains(arenaName + ".mainlobby")) {
            Location location = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".mainlobby"));

            mainlobbylist.add("Current: " + location.getWorld().getName() + " "
                    + Math.round(location.getBlockX()) + " "
                    + Math.round(location.getBlockY()) + " "
                    + Math.round(location.getBlockZ()));
        }

        if (ArenaManager.getArenas().contains(arenaName + ".waitinglobby")) {
            Location location = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".waitinglobby"));
            waitinglobbylist.add("Current: " + location.getWorld().getName() + " "
                    + Math.round(location.getBlockX()) + " "
                    + Math.round(location.getBlockY()) + " "
                    + Math.round(location.getBlockZ()));
        }

        if (ArenaManager.getArenas().contains(arenaName + ".spectator")) {
            Location location = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".spectator"));
            spectatorlist.add("Current: " + location.getWorld().getName() + " "
                    + Math.round(location.getBlockX()) + " "
                    + Math.round(location.getBlockY()) + " "
                    + Math.round(location.getBlockZ()));
        }

        if (ArenaManager.getArenas().contains(arenaName + ".pos1")) {
            Location location = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".pos1"));
            pos1list.add("Current: " + location.getWorld().getName() + " "
                    + Math.round(location.getBlockX()) + " "
                    + Math.round(location.getBlockY()) + " "
                    + Math.round(location.getBlockZ()));
        }

        if (ArenaManager.getArenas().contains(arenaName + ".pos2")) {
            Location location = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".pos2"));
            pos2list.add("Current: " + location.getWorld().getName() + " "
                    + Math.round(location.getBlockX()) + " "
                    + Math.round(location.getBlockY()) + " "
                    + Math.round(location.getBlockZ()));
        }

        sizelist.add("Current: " + ArenaManager.getTeamSize(arenaName));

        mainlobbylist.add("Use shift + left click to reset it.");
        waitinglobbylist.add("Use shift + left click to reset it.");
        spectatorlist.add("Use shift + left click to reset it.");
        pos1list.add("Use shift + left click to reset it.");
        pos2list.add("Use shift + left click to reset it.");

        mainLobbyMeta.setLore(mainlobbylist);
        waitingLobbyMeta.setLore(waitinglobbylist);
        spectatorMeta.setLore(spectatorlist);
        registerMeta.setLore(registerlist);
        teamsMeta.setLore(teamslist);
        sizeMeta.setLore(sizelist);
        pos1meta.setLore(pos1list);
        pos2meta.setLore(pos2list);

        mainLobby.setItemMeta(mainLobbyMeta);
        waitingLobby.setItemMeta(waitingLobbyMeta);
        spectator.setItemMeta(spectatorMeta);
        register.setItemMeta(registerMeta);
        teams.setItemMeta(teamsMeta);
        generators.setItemMeta(generatorsMeta);
        size.setItemMeta(sizeMeta);
        pos1.setItemMeta(pos1meta);
        pos2.setItemMeta(pos2meta);
        save.setItemMeta(saveMeta);

        GUI gui = new GUI(arenaName, 3, EggWarsReloaded.getEggWarsMain(), player);

        gui.addItem(mainLobby, items.getInt("items.editmain.mainlobby.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    if (arenas.contains(arenaName + ".mainlobby")) {
                        ArenaManager.setMainLobby(arenaName, null);
                        ArenaManager.setArenaRegistered(arenaName, false, null);

                        player.sendMessage("Reset main lobby.");
                    }

                    EditMenu.openEditMenu(arenaName, player);
                })
                .addDefaultEvent(() -> {
                    if (ArenaManager.getArenas().contains(arenaName + "mainlobby")) {
                        player.sendMessage("Sorry this is already set. Please reset it with: shift + click");
                    } else {
                        ArenaManager.setMainLobby(arenaName, player.getLocation());

                        player.sendMessage("Set main lobby.");
                    }

                    EditMenu.openEditMenu(arenaName, player);
                });

        gui.addItem(waitingLobby, items.getInt("items.editmain.waitinglobby.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    if (arenas.contains(arenaName + ".waitinglobby")) {
                        ArenaManager.setWaitingLobby(arenaName, null);
                        ArenaManager.setArenaRegistered(arenaName, false, null);

                        player.sendMessage("Reset waiting lobby.");
                    }

                    EditMenu.openEditMenu(arenaName, player);
                })
                .addDefaultEvent(() -> {
                    if (ArenaManager.getArenas().contains(arenaName + "waitinglobby")) {
                        player.sendMessage("Sorry this is already set. Please reset it with: shift + click");
                    } else {
                        ArenaManager.setWaitingLobby(arenaName, player.getLocation());
                        player.sendMessage("Set waiting lobby.");
                    }

                    EditMenu.openEditMenu(arenaName, player);
                });

        gui.addItem(spectator, items.getInt("items.editmain.spectator.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    if (arenas.contains(arenaName + ".spectator")) {
                        ArenaManager.setSpectator(arenaName, null);
                        ArenaManager.setArenaRegistered(arenaName, false, null);

                        player.sendMessage("Reset spectator spawn.");
                    }

                    EditMenu.openEditMenu(arenaName, player);
                })
                .addDefaultEvent(() -> {
                    if (ArenaManager.getArenas().contains(arenaName + "spectator")) {
                        player.sendMessage("Sorry this is already set. Please reset it with: shift + click");
                    } else {
                        ArenaManager.setSpectator(arenaName, player.getLocation());

                        player.sendMessage("Set spectator spawn.");
                    }

                    EditMenu.openEditMenu(arenaName, player);
                });

        gui.addItem(register, items.getInt("items.editmain.register.slot"))
                .addDefaultEvent(() -> {
                    if (ArenaManager.isArenaRegistered(arenaName)) {
                        ArenaManager.setArenaRegistered(arenaName, false, null);

                        player.sendMessage("Unregistered arena: " + arenaName);
                    } else {
                        List<String> teams1 = new ArrayList<>();

                        for (String team : arenas.getConfigurationSection(arenaName + ".team").getKeys(false)) {
                            if (ArenaManager.isTeamRegistered(arenaName, team)) {
                                teams1.add(team);
                            }
                        }

                        if (teams1.size() < 2) {
                            player.sendMessage("You need at least 2 teams registered!");
                        } else {
                            boolean isWrong = false;
                            StringBuilder wrongteams = new StringBuilder();

                            for (String team : teams1) {
                                EggWarsReloaded.getEggWarsMain().getLogger().info(team);
                                List<String> spawns = arenas.getStringList(arenaName + ".team." + team + ".spawn");
                                EggWarsReloaded.getEggWarsMain().getLogger().info(spawns.toString());
                                EggWarsReloaded.getEggWarsMain().getLogger().info(String.valueOf(spawns.size()));
                                EggWarsReloaded.getEggWarsMain().getLogger().info(String.valueOf(ArenaManager.getTeamSize(team)));

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

        gui.addItem(teams, items.getInt("items.editmain.teams.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    arenas.set(arenaName + ".team", null);

                    try {
                        EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    EggWarsReloaded.getEggWarsMain().reloadArenas();
                    EditMenu.openEditMenu(arenaName, player);

                    player.sendMessage("Reset teams of arena: " + arenaName);
                })
                .addDefaultEvent(() -> {
                    TeamMenu.setupTeamMenu(arenaName, player);
                });

        gui.addItem(generators, items.getInt("items.editmain.generators.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    arenas.set(arenaName + ".iron", null);
                    arenas.set(arenaName + ".gold", null);
                    arenas.set(arenaName + ".diamond", null);

                    try {
                        EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    EggWarsReloaded.getEggWarsMain().reloadArenas();
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

        gui.addItem(size, items.getInt("items.editmain.teamsize.slot"))
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

        gui.addItem(pos1, items.getInt("items.editmain.pos1.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setArenaPos1(arenaName, null);

                    EditMenu.openEditMenu(arenaName, player);
                    player.sendMessage("Reset pos1");
                })
                .addDefaultEvent(() -> {
                    ArenaManager.setArenaPos1(arenaName, player.getLocation());

                    EditMenu.openEditMenu(arenaName, player);
                });

        gui.addItem(pos2, items.getInt("items.editmain.pos2.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setArenaPos2(arenaName, null);

                    EditMenu.openEditMenu(arenaName, player);
                    player.sendMessage("Reset pos2");
                })
                .addDefaultEvent(() -> {
                    ArenaManager.setArenaPos2(arenaName, player.getLocation());

                    EditMenu.openEditMenu(arenaName, player);
                });

        gui.addItem(save, items.getInt("items.editmain.save.slot"))
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
