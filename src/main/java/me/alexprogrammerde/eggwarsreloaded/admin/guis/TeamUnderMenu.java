package me.alexprogrammerde.eggwarsreloaded.admin.guis;

import me.alexprogrammerde.eggwarsreloaded.EggWarsReloaded;
import me.alexprogrammerde.eggwarsreloaded.admin.assistants.EggAssistant;
import me.alexprogrammerde.eggwarsreloaded.admin.assistants.ShopAssistant;
import me.alexprogrammerde.eggwarsreloaded.game.collection.TeamColor;
import me.alexprogrammerde.eggwarsreloaded.utils.ArenaManager;
import me.alexprogrammerde.eggwarsreloaded.utils.ItemBuilder;
import me.alexprogrammerde.eggwarsreloaded.utils.UtilCore;
import me.alexprogrammerde.eggwarsreloaded.utils.gui.GUI;
import net.md_5.bungee.api.ChatColor;
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
    private static final String spawn = ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "SpawnAssistant" + ChatColor.GOLD + "] ";
    private static final String respawn = ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "RespawnAssistant" + ChatColor.GOLD + "] ";

    public static void setupTeamUnderMenu(String arenaName, TeamColor teamName, Player player, EggWarsReloaded plugin) {
        FileConfiguration items = plugin.getItems();
        FileConfiguration arenas = plugin.getArenaConfig();

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

        egg.lore("Left click to start the egg assistant.");

        if (arenas.contains(arenaName + ".team." + teamName + ".egg")) {
            Location egglocation = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".team." + teamName + ".egg"));

            egg.lore("Current: " + egglocation.getWorld().getName() + " "
                    + Math.round(egglocation.getBlockX()) + " "
                    + Math.round(egglocation.getBlockY()) + " "
                    + Math.round(egglocation.getBlockZ()));

            egg.enchant();

            egg.lore("Use shift + left click to reset it.");
        }

        shop.lore("Left click to start the shop assistant.");

        if (arenas.contains(arenaName + ".team." + teamName + ".shop")) {
            Location shopLocation = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".team." + teamName + ".shop.location"));

            shop.lore("Current: " + shopLocation.getWorld().getName() + " "
                    + Math.round(shopLocation.getBlockX()) + " "
                    + Math.round(shopLocation.getBlockY()) + " "
                    + Math.round(shopLocation.getBlockZ()));

            shop.enchant();

            shop.lore("Use shift + left click to reset it.");
        }

        spawn.lore("Left click to start the spawn assistant.");

        if (arenas.contains(arenaName + ".team." + teamName + ".spawn")) {
            for (String spawns : ArenaManager.getArenas().getStringList(arenaName + ".team." + teamName + ".spawn")) {
                Location loc = UtilCore.convertLocation(spawns);

                spawn.lore(loc.getWorld().getName() + " "
                        + loc.getX() + " "
                        + loc.getY() + " "
                        + loc.getZ() + " "
                        + loc.getYaw() + " "
                        + loc.getPitch());
            }

            spawn.enchant();

            spawn.lore("Use shift + left click to reset it.");
        }

        respawn.lore("Left click to set your current place as the respawnpoint.");

        if (arenas.contains(arenaName + ".team." + teamName + ".respawn")) {
            Location loc = UtilCore.convertLocation(arenas.getString(arenaName + ".team." + teamName + ".respawn"));

            respawn.lore(loc.getWorld().getName() + " "
                    + loc.getX() + " "
                    + loc.getY() + " "
                    + loc.getZ() + " "
                    + loc.getYaw() + " "
                    + loc.getPitch());

            respawn.enchant();

            respawn.lore("Use shift + left click to reset it.");
        }

        GUI gui = new GUI(arenaName, 3, plugin, player);

        gui.addItem(shop.build(), items.getInt("items.editteam.shop.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setShop(arenaName, teamName, null, null);
                    ArenaManager.setTeamRegistered(arenaName, teamName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);

                    player.sendMessage(ShopAssistant.prefix + "Reset the shop and unregistered the team and the arena. Use /kill to remove the villager.");
                })
                .addDefaultEvent(() -> {
                    if (arenas.contains(arenaName + ".team." + teamName + ".shop")) {
                        player.sendMessage(ShopAssistant.prefix + "The shop is already set. Please reset it first.");
                    } else {
                        new ShopAssistant(player, arenaName, teamName, plugin);

                        player.closeInventory();
                        player.sendMessage(ShopAssistant.prefix + "Click a armor stand to make him to a shop.");
                    }
                });

        gui.addItem(egg.build(), items.getInt("items.editteam.egg.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setEgg(arenaName, teamName, null);
                    ArenaManager.setTeamRegistered(arenaName, teamName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);

                    player.sendMessage(EggAssistant.prefix + "Reset the egg and unregistered the team and the arena. Remove the egg manually in creative mode.");
                })
                .addDefaultEvent(() -> {
                    if (arenas.contains(arenaName + ".team." + teamName + ".egg")) {
                        player.sendMessage(EggAssistant.prefix + "The egg is already set. Please reset it first.");
                    } else {
                        new EggAssistant(player, arenaName, teamName, plugin);
                        player.closeInventory();
                        player.sendMessage(EggAssistant.prefix + "You can now click a dragon egg to add it to the team.");
                    }
                });

        gui.addItem(spawn.build(), items.getInt("items.editteam.spawn.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    if (arenas.getStringList(arenaName + ".team." + teamName + ".spawn").size() < 4) {
                        ArenaManager.setTeamRegistered(arenaName, teamName, false);
                        ArenaManager.setArenaRegistered(arenaName, false, null);
                        ArenaManager.setFirstSpawn(arenaName, teamName, null);

                        player.sendMessage(spawn + "Reset the spawns and unregistered the team and the arena.");
                    } else {
                        player.sendMessage(spawn + "Sorry 4 spawns are the limit for one team.");
                    }

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);
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
                                    plugin.getArenaConfig().save(plugin.getArenasFile());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                plugin.loadConfig();

                                player.sendMessage(spawn + "Added spawn of team " + teamName + " to: " + playerLocation.getWorld().getName() + " " + playerLocation.getBlockX() + " " + playerLocation.getBlockY() + " " + playerLocation.getBlockZ());
                            } else {
                                player.sendMessage(spawn + "This location is already registered!");
                            }
                        } else {
                            player.sendMessage(spawn + "The block under needs to be a a emerald block!");
                        }
                    } else {
                        player.sendMessage(spawn + "Sorry 4 spawns are the limit for one team.");
                    }

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);
                });

        gui.addItem(respawn.build(), items.getInt("items.editteam.respawn.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setTeamRegistered(arenaName, teamName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);
                    ArenaManager.setRespawn(arenaName, teamName, null);

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);

                    player.sendMessage(respawn + "Reset the respawn and unregistered the team and the arena.");
                })
                .addDefaultEvent(() -> {
                    Location playerLocation = player.getLocation();

                    if (!arenas.contains(arenaName + ".team." + teamName + ".respawn")) {
                        ArenaManager.setRespawn(arenaName, teamName, playerLocation);

                        player.sendMessage(respawn + "Set respawn of team " + teamName + " to: " + playerLocation.getWorld().getName() + " " + playerLocation.getBlockX() + " " + playerLocation.getBlockY() + " " + playerLocation.getBlockZ());
                    } else {
                        player.sendMessage(respawn + "The respawn is already registered! Please reset it first!");
                    }

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);
                });

        gui.addItem(back.build(), items.getInt("items.editteam.back.slot"))
                .addDefaultEvent(() -> TeamMenu.setupTeamMenu(arenaName, player, plugin));

        gui.openGUI();
    }
}
