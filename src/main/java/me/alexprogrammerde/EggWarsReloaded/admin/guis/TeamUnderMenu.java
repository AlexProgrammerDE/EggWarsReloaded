package me.alexprogrammerde.EggWarsReloaded.admin.guis;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.admin.assistants.EggAssistant;
import me.alexprogrammerde.EggWarsReloaded.admin.assistants.ShopAssistant;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.ItemBuilder;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import me.alexprogrammerde.EggWarsReloaded.utils.gui.GUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamUnderMenu {

    public static void setupTeamUnderMenu(String arenaName, String teamName, Player player) {
        FileConfiguration items = EggWarsReloaded.get().getItems();
        FileConfiguration arenas = EggWarsReloaded.get().getArenas();

        // Load Data from storage
        ItemBuilder shop;

        if (arenas.contains(arenaName + ".team." + teamName + ".shop")) {
            shop = new ItemBuilder(Material.ENDER_CHEST);
        } else {
            shop = new ItemBuilder(Material.CHEST);
        }

        ItemBuilder egg = new ItemBuilder(Material.DRAGON_EGG);
        ItemBuilder spawn = new ItemBuilder(Material.EMERALD_BLOCK);
        ItemBuilder respawn = new ItemBuilder(Material.REDSTONE_BLOCK);
        ItemBuilder back = new ItemBuilder(Material.BARRIER);

        // Get item names from items.yml
        shop.name(items.getString("items.editteam.shop.name"));
        egg.name(items.getString("items.editteam.egg.name"));
        spawn.name(items.getString("items.editteam.spawn.name"));
        respawn.name(items.getString("items.editteam.respawn.name"));
        back.name(items.getString("items.editteam.back.name"));

        egg.addLore("Left click to start the egg assistant.");

        if (arenas.contains(arenaName + ".team." + teamName + ".egg")) {
            Location egglocation = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".team." + teamName + ".egg"));

            egg.addLore("Current: " + egglocation.getWorld().getName() + " "
                    + Math.round(egglocation.getBlockX()) + " "
                    + Math.round(egglocation.getBlockY()) + " "
                    + Math.round(egglocation.getBlockZ()));

            egg.enchant();

            egg.addLore("Use shift + left click to reset it.");
        }

        shop.addLore("Left click to start the shop assistant.");

        if (arenas.contains(arenaName + ".team." + teamName + ".shop")) {
            Location shopLocation = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".team." + teamName + ".shop.location"));

            shop.addLore("Current: " + shopLocation.getWorld().getName() + " "
                    + Math.round(shopLocation.getBlockX()) + " "
                    + Math.round(shopLocation.getBlockY()) + " "
                    + Math.round(shopLocation.getBlockZ()));

            shop.enchant();

            shop.addLore("Use shift + left click to reset it.");
        }

        spawn.addLore("Left click to start the spawn assistant.");

        if (arenas.contains(arenaName + ".team." + teamName + ".spawn")) {
            for (String spawns : ArenaManager.getArenas().getStringList(arenaName + ".team." + teamName + ".spawn")) {
                Location loc = UtilCore.convertLocation(spawns);

                spawn.addLore(loc.getWorld().getName() + " "
                        + loc.getX() + " "
                        + loc.getY() + " "
                        + loc.getZ() + " "
                        + loc.getYaw() + " "
                        + loc.getPitch());
            }

            spawn.enchant();

            spawn.addLore("Use shift + left click to reset it.");
        }

        respawn.addLore("Left click to set your current place as the respawnpoint.");

        if (arenas.contains(arenaName + ".team." + teamName + ".respawn")) {
            Location loc = UtilCore.convertLocation(arenas.getString(arenaName + ".team." + teamName + ".respawn"));

            respawn.addLore(loc.getWorld().getName() + " "
                    + loc.getX() + " "
                    + loc.getY() + " "
                    + loc.getZ() + " "
                    + loc.getYaw() + " "
                    + loc.getPitch());

            respawn.enchant();

            respawn.addLore("Use shift + left click to reset it.");
        }

        GUI gui = new GUI(arenaName, 3, EggWarsReloaded.get(), player);

        gui.addItem(shop.build(), items.getInt("items.editteam.shop.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setShop(arenaName, teamName, null, null);
                    ArenaManager.setTeamRegistered(arenaName, teamName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);

                    player.sendMessage("[ShopAssistant] Reset the shop and unregistered the team and the arena. Use /kill to remove the villager.");
                })
                .addDefaultEvent(() -> {
                    if (arenas.contains(arenaName + ".team." + teamName + ".shop")) {
                        player.sendMessage("[ShopAssistant] The shop is already set. Please reset it first.");
                    } else {
                        new ShopAssistant(player, arenaName, teamName);

                        player.closeInventory();
                        player.sendMessage("[ShopAssistant] Click a armor stand to make him to a shop.");
                    }
                });

        gui.addItem(egg.build(), items.getInt("items.editteam.egg.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setEgg(arenaName, teamName, null);
                    ArenaManager.setTeamRegistered(arenaName, teamName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);

                    player.sendMessage("[EggAssistant] Reset the egg and unregistered the team and the arena. Remove the egg manually in creative mode.");
                })
                .addDefaultEvent(() -> {
                    if (arenas.contains(arenaName + ".team." + teamName + ".egg")) {
                        player.sendMessage("[EggAssistant] The egg is already set. Please reset it first.");
                    } else {
                        new EggAssistant(player, arenaName, teamName);
                        player.closeInventory();
                        player.sendMessage("[EggAssistant] You can now click a dragon egg to add it to the team.");
                    }
                });

        gui.addItem(spawn.build(), items.getInt("items.editteam.spawn.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    if (arenas.getStringList(arenaName + ".team." + teamName + ".spawn").size() < 4) {
                        ArenaManager.setTeamRegistered(arenaName, teamName, false);
                        ArenaManager.setArenaRegistered(arenaName, false, null);
                        ArenaManager.setFirstSpawn(arenaName, teamName, null);

                        player.sendMessage("[SpawnAssistant] Reset the spawns and unregistered the team and the arena.");
                    } else {
                        player.sendMessage("[SpawnAssistant] Sorry 4 spawns are the limit for one team.");
                    }

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);
                })
                .addDefaultEvent(() -> {
                    if (arenas.getStringList(arenaName + ".team." + teamName + ".spawn").size() < 4) {
                        Location playerLocation = player.getLocation();
                        Block ground = player.getWorld().getBlockAt(new Location(playerLocation.getWorld(), playerLocation.getBlockX(), playerLocation.getBlockY() - 1, playerLocation.getBlockZ()));

                        if (ground.getType().equals(Material.EMERALD_BLOCK)) {
                            List<String> strings = arenas.getStringList(arenaName + ".team." + teamName + ".spawn");
                            List<Location> locations = new ArrayList<>();

                            for (String str : strings) {
                                locations.add(UtilCore.convertLocation(str));
                            }

                            if (!locations.contains(playerLocation)) {
                                strings.add(UtilCore.convertString(playerLocation));

                                arenas.set(arenaName + ".team." + teamName + ".spawn", strings);

                                try {
                                    EggWarsReloaded.get().getArenas().save(EggWarsReloaded.get().getArenasFile());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                EggWarsReloaded.get().reloadArenas();

                                player.sendMessage("[SpawnAssistant] Added spawn of team " + teamName + " to: " + playerLocation.getWorld().getName() + " " + playerLocation.getBlockX() + " " + playerLocation.getBlockY() + " " + playerLocation.getBlockZ());
                            } else {
                                player.sendMessage("[SpawnAssistant] This location is already registered!");
                            }
                        } else {
                            player.sendMessage("[SpawnAssistant] The block under needs to be a a emerald block!");
                        }
                    } else {
                        player.sendMessage("[SpawnAssistant] Sorry 4 spawns are the limit for one team.");
                    }

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);
                });

        gui.addItem(respawn.build(), items.getInt("items.editteam.respawn.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setTeamRegistered(arenaName, teamName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);
                    ArenaManager.setRespawn(arenaName, teamName, null);

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);

                    player.sendMessage("[RespawnAssistant] Reset the respawn and unregistered the team and the arena.");
                })
                .addDefaultEvent(() -> {
                    Location playerLocation = player.getLocation();

                    if (!arenas.contains(arenaName + ".team." + teamName + ".respawn")) {
                        ArenaManager.setRespawn(arenaName, teamName, playerLocation);

                        player.sendMessage("[RespawnAssistant] Set respawn of team " + teamName + " to: " + playerLocation.getWorld().getName() + " " + playerLocation.getBlockX() + " " + playerLocation.getBlockY() + " " + playerLocation.getBlockZ());
                    } else {
                        player.sendMessage("[RespawnAssistant] The respawn is already registered! Please reset it first!");
                    }

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);
                });

        gui.addItem(back.build(), items.getInt("items.editteam.back.slot"))
                .addDefaultEvent(() -> TeamMenu.setupTeamMenu(arenaName, player));

        gui.openGUI();
    }
}
