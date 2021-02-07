package net.pistonmaster.eggwarsreloaded.admin.guis;

import net.md_5.bungee.api.ChatColor;
import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import net.pistonmaster.eggwarsreloaded.admin.assistants.EggAssistant;
import net.pistonmaster.eggwarsreloaded.admin.assistants.ShopAssistant;
import net.pistonmaster.eggwarsreloaded.game.collection.TeamColor;
import net.pistonmaster.eggwarsreloaded.utils.ArenaManager;
import net.pistonmaster.eggwarsreloaded.utils.ItemBuilder;
import net.pistonmaster.eggwarsreloaded.utils.UtilCore;
import net.pistonmaster.eggwarsreloaded.utils.gui.GUI;
import org.bukkit.Location;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamUnderMenu {
    private static final String SPAWN = ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "SpawnAssistant" + ChatColor.GOLD + "] ";
    private static final String RESPAWN = ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "RespawnAssistant" + ChatColor.GOLD + "] ";

    private TeamUnderMenu() {
    }

    public static void setupTeamUnderMenu(String arenaName, TeamColor teamName, Player player, EggWarsReloaded plugin) {
        FileConfiguration items = plugin.getItems();
        FileConfiguration arenas = plugin.getArenaConfig();

        // Load Data from storage
        ItemBuilder shop;

        if (arenas.contains(arenaName + ".team." + teamName + ".shop")) {
            shop = new ItemBuilder(XMaterial.ENDER_CHEST);
        } else {
            shop = new ItemBuilder(XMaterial.CHEST);
        }

        ItemBuilder eggBuilder = new ItemBuilder(XMaterial.DRAGON_EGG);
        ItemBuilder spawnBuilder = new ItemBuilder(XMaterial.EMERALD_BLOCK);
        ItemBuilder respawnBuilder = new ItemBuilder(XMaterial.REDSTONE_BLOCK);
        ItemBuilder backBuilder = new ItemBuilder(XMaterial.BARRIER);

        // Get item names from items.yml
        shop.name(items.getString("items.editteam.shop.name"));
        eggBuilder.name(items.getString("items.editteam.egg.name"));
        spawnBuilder.name(items.getString("items.editteam.spawn.name"));
        respawnBuilder.name(items.getString("items.editteam.respawn.name"));
        backBuilder.name(items.getString("items.editteam.back.name"));

        eggBuilder.lore("Left click to start the egg assistant.");

        if (arenas.contains(arenaName + ".team." + teamName + ".egg")) {
            Location eggLocation = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".team." + teamName + ".egg"));

            eggBuilder.lore("Current: " + eggLocation.getWorld().getName() + " "
                    + eggLocation.getBlockX() + " "
                    + eggLocation.getBlockY() + " "
                    + eggLocation.getBlockZ());

            eggBuilder.enchant();

            eggBuilder.lore("Use shift + left click to reset it.");
        }

        shop.lore("Left click to start the shop assistant.");

        if (arenas.contains(arenaName + ".team." + teamName + ".shop")) {
            Location shopLocation = UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".team." + teamName + ".shop.location"));

            shop.lore("Current: " + shopLocation.getWorld().getName() + " "
                    + shopLocation.getBlockX() + " "
                    + shopLocation.getBlockY() + " "
                    + shopLocation.getBlockZ());

            shop.enchant();

            shop.lore("Use shift + left click to reset it.");
        }

        spawnBuilder.lore("Left click to start the spawn assistant.");

        if (arenas.contains(arenaName + ".team." + teamName + ".spawn")) {
            for (String spawns : ArenaManager.getArenas().getStringList(arenaName + ".team." + teamName + ".spawn")) {
                Location loc = UtilCore.convertLocation(spawns);

                spawnBuilder.lore(loc.getWorld().getName() + " "
                        + loc.getX() + " "
                        + loc.getY() + " "
                        + loc.getZ() + " "
                        + loc.getYaw() + " "
                        + loc.getPitch());
            }

            spawnBuilder.enchant();

            spawnBuilder.lore("Use shift + left click to reset it.");
        }

        respawnBuilder.lore("Left click to set your current place as the respawnpoint.");

        if (arenas.contains(arenaName + ".team." + teamName + ".respawn")) {
            Location loc = UtilCore.convertLocation(arenas.getString(arenaName + ".team." + teamName + ".respawn"));

            respawnBuilder.lore(loc.getWorld().getName() + " "
                    + loc.getX() + " "
                    + loc.getY() + " "
                    + loc.getZ() + " "
                    + loc.getYaw() + " "
                    + loc.getPitch());

            respawnBuilder.enchant();

            respawnBuilder.lore("Use shift + left click to reset it.");
        }

        GUI gui = new GUI(arenaName, 3, plugin, player);

        gui.addItem(shop.build(), items.getInt("items.editteam.shop.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setShop(arenaName, teamName, null, null);
                    ArenaManager.setTeamRegistered(arenaName, teamName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);

                    player.sendMessage(ShopAssistant.PREFIX + "Reset the shop and unregistered the team and the arena. Use /kill to remove the villager.");
                })
                .addDefaultEvent(() -> {
                    if (arenas.contains(arenaName + ".team." + teamName + ".shop")) {
                        player.sendMessage(ShopAssistant.PREFIX + "The shop is already set. Please reset it first.");
                    } else {
                        new ShopAssistant(player, arenaName, teamName, plugin);

                        player.closeInventory();
                        player.sendMessage(ShopAssistant.PREFIX + "Click a armor stand to make him to a shop.");
                    }
                });

        gui.addItem(eggBuilder.build(), items.getInt("items.editteam.egg.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setEgg(arenaName, teamName, null);
                    ArenaManager.setTeamRegistered(arenaName, teamName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);

                    player.sendMessage(EggAssistant.PREFIX + "Reset the egg and unregistered the team and the arena. Remove the egg manually in creative mode.");
                })
                .addDefaultEvent(() -> {
                    if (arenas.contains(arenaName + ".team." + teamName + ".egg")) {
                        player.sendMessage(EggAssistant.PREFIX + "The egg is already set. Please reset it first.");
                    } else {
                        new EggAssistant(player, arenaName, teamName, plugin);
                        player.closeInventory();
                        player.sendMessage(EggAssistant.PREFIX + "You can now click a dragon egg to add it to the team.");
                    }
                });

        gui.addItem(spawnBuilder.build(), items.getInt("items.editteam.spawn.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    if (arenas.getStringList(arenaName + ".team." + teamName + ".spawn").size() < 4) {
                        ArenaManager.setTeamRegistered(arenaName, teamName, false);
                        ArenaManager.setArenaRegistered(arenaName, false, null);
                        ArenaManager.setFirstSpawn(arenaName, teamName, null);

                        player.sendMessage(spawnBuilder + "Reset the spawns and unregistered the team and the arena.");
                    } else {
                        player.sendMessage(spawnBuilder + "Sorry 4 spawns are the limit for one team.");
                    }

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);
                })
                .addDefaultEvent(() -> {
                    if (arenas.getStringList(arenaName + ".team." + teamName + ".spawn").size() < 4) {
                        Location playerLocation = player.getLocation();
                        Block ground = player.getWorld().getBlockAt(new Location(playerLocation.getWorld(), playerLocation.getBlockX(), playerLocation.getBlockY() - 1, playerLocation.getBlockZ()));

                        if (ground.getType().equals(XMaterial.EMERALD_BLOCK)) {
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

                                player.sendMessage(SPAWN + "Added spawn of team " + teamName + " to: " + playerLocation.getWorld().getName() + " " + playerLocation.getBlockX() + " " + playerLocation.getBlockY() + " " + playerLocation.getBlockZ());
                            } else {
                                player.sendMessage(SPAWN + "This location is already registered!");
                            }
                        } else {
                            player.sendMessage(SPAWN + "The block under needs to be a a emerald block!");
                        }
                    } else {
                        player.sendMessage(SPAWN + "Sorry 4 spawns are the limit for one team.");
                    }

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);
                });

        gui.addItem(respawnBuilder.build(), items.getInt("items.editteam.respawn.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setTeamRegistered(arenaName, teamName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);
                    ArenaManager.setRespawn(arenaName, teamName, null);

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);

                    player.sendMessage(RESPAWN + "Reset the respawn and unregistered the team and the arena.");
                })
                .addDefaultEvent(() -> {
                    Location playerLocation = player.getLocation();

                    if (!arenas.contains(arenaName + ".team." + teamName + ".respawn")) {
                        ArenaManager.setRespawn(arenaName, teamName, playerLocation);

                        player.sendMessage(RESPAWN + "Set respawn of team " + teamName + " to: " + playerLocation.getWorld().getName() + " " + playerLocation.getBlockX() + " " + playerLocation.getBlockY() + " " + playerLocation.getBlockZ());
                    } else {
                        player.sendMessage(RESPAWN + "The respawn is already registered! Please reset it first!");
                    }

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);
                });

        gui.addItem(backBuilder.build(), items.getInt("items.editteam.back.slot"))
                .addDefaultEvent(() -> TeamMenu.setupTeamMenu(arenaName, player, plugin));

        gui.openGUI();
    }
}
