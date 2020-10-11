package me.alexprogrammerde.EggWarsReloaded.admin.guis;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.admin.EggAssistant;
import me.alexprogrammerde.EggWarsReloaded.admin.ShopAssistant;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import me.alexprogrammerde.EggWarsReloaded.utils.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

public class TeamUnderMenu {

    public static void setupTeamUnderMenu(String arenaName, String teamName, Player player) {
        FileConfiguration items = EggWarsReloaded.getEggWarsMain().getItems();
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

        // Load Data from storage
        ItemStack shop;

        if (arenas.contains("arenas." + arenaName + ".team." + teamName + ".shop")) {
            shop = new ItemStack(Material.ENDER_CHEST);
        } else {
            shop = new ItemStack(Material.CHEST);
        }

        ItemStack egg = new ItemStack(Material.DRAGON_EGG);
        ItemStack spawn = new ItemStack(Material.EMERALD_BLOCK);
        ItemStack back = new ItemStack(Material.BARRIER);

        ItemMeta shopmeta = shop.getItemMeta();
        ItemMeta eggmeta = egg.getItemMeta();
        ItemMeta spawnmeta = spawn.getItemMeta();
        ItemMeta backmeta = back.getItemMeta();

        // Get item names from items.yml
        shopmeta.setDisplayName(items.getString("items.editteam.shop.name"));
        eggmeta.setDisplayName(items.getString("items.editteam.egg.name"));
        spawnmeta.setDisplayName(items.getString("items.editteam.spawn.name"));
        backmeta.setDisplayName(items.getString("items.editteam.back.name"));

        List<String> egglist = new ArrayList<>();
        egglist.add("Left click to start the egg assistant.");

        if (arenas.contains("arenas." + arenaName + ".team." + teamName + ".egg")) {
            Location egglocation = UtilCore.convertLocation(ArenaManager.getArenas().getString("arenas." + arenaName + ".team." + teamName + ".egg"));

            egglist.add("Current: " + egglocation.getWorld().getName() + " "
                    + Math.round(egglocation.getBlockX()) + " "
                    + Math.round(egglocation.getBlockY()) + " "
                    + Math.round(egglocation.getBlockZ()));

            eggmeta.addEnchant(Enchantment.DURABILITY, 0, true);
            eggmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            egglist.add("Use shift + left click to reset it.");
        }

        eggmeta.setLore(egglist);

        List<String> shopList = new ArrayList<>();
        shopList.add("Left click to start the shop assistant.");

        if (arenas.contains("arenas." + arenaName + ".team." + teamName + ".shop")) {
            Location shopLocation = UtilCore.convertLocation(ArenaManager.getArenas().getString("arenas." + arenaName + ".team." + teamName + ".shop.location"));

            shopList.add("Current: " + shopLocation.getWorld().getName() + " "
                    + Math.round(shopLocation.getBlockX()) + " "
                    + Math.round(shopLocation.getBlockY()) + " "
                    + Math.round(shopLocation.getBlockZ()));

            shopmeta.addEnchant(Enchantment.DURABILITY, 0, true);
            shopmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            shopList.add("Use shift + left click to reset it.");
        }

        shopmeta.setLore(shopList);

        List<String> spawnlist = new ArrayList<>();
        spawnlist.add("Left click to start the shop assistant.");

        if (arenas.contains("arenas." + arenaName + ".team." + teamName + ".spawn")) {
            for (String spawns : ArenaManager.getArenas().getStringList("arenas." + arenaName + ".team." + teamName + ".spawn")) {
                String[] spawnssplit = spawns.split(" ");

                spawnlist.add(spawnssplit[0] + " "
                        + Math.round(Double.parseDouble(spawnssplit[1])) + " "
                        + Math.round(Double.parseDouble(spawnssplit[2])) + " "
                        + Math.round(Double.parseDouble(spawnssplit[2])) + " "
                        + Math.round(Float.parseFloat(spawnssplit[2])) + " "
                        + Math.round(Float.parseFloat(spawnssplit[3])));
            }

            spawnmeta.addEnchant(Enchantment.DURABILITY, 0, true);
            spawnmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            spawnlist.add("Use shift + left click to reset it.");
        }

        spawnmeta.setLore(spawnlist);

        shop.setItemMeta(shopmeta);
        egg.setItemMeta(eggmeta);
        spawn.setItemMeta(spawnmeta);
        back.setItemMeta(backmeta);

        GUI gui = new GUI(arenaName, 3, EggWarsReloaded.getEggWarsMain(), player);

        gui.addItem(shop, items.getInt("items.editteam.shop.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setShop(arenaName, teamName, null, null);
                    ArenaManager.setTeamRegistered(arenaName, teamName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);

                    // TODO Kill the villager at reset
                    if (ArenaManager.isArenaRegistered(arenaName) && ArenaManager.isTeamRegistered(arenaName, teamName)) {
                        player.sendMessage("Reset the shop and unregistered the team and the arena. Use /kill to remove the villager.");
                    } if (ArenaManager.isTeamRegistered(arenaName, teamName)) {
                        player.sendMessage("Reset the shop and unregistered the team. Use /kill to remove the villager.");
                    } else if (ArenaManager.isArenaRegistered(arenaName)) {
                        player.sendMessage("Reset the shop and unregistered the arena. Use /kill to remove the villager.");
                    }
                })
                .addDefaultEvent(() -> {
                    if (arenas.contains("arenas." + arenaName + ".team." + teamName + ".shop")) {
                        player.sendMessage("The shop is already set. Please reset it first.");
                    } else {
                        new ShopAssistant(player, arenaName, teamName);

                        player.closeInventory();
                        player.sendMessage("[ShopAssistant] Click a armor stand to make him to a shop.");
                    }
                });

        gui.addItem(egg, items.getInt("items.editteam.egg.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setEgg(arenaName, teamName, null);
                    ArenaManager.setTeamRegistered(arenaName, teamName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);

                    // TODO Remove the egg on reset
                    if (ArenaManager.isArenaRegistered(arenaName) && ArenaManager.isTeamRegistered(arenaName, teamName)) {
                        player.sendMessage("Reset the egg and unregistered the team and the arena. Remove the egg manually in creative mode.");
                    } if (ArenaManager.isTeamRegistered(arenaName, teamName)) {
                        player.sendMessage("Reset the egg and unregistered the team. Remove the egg manually in creative mode.");
                    } else if (ArenaManager.isArenaRegistered(arenaName)) {
                        player.sendMessage("Reset the egg and unregistered the arena. Remove the egg manually in creative mode.");
                    }
                })
                .addDefaultEvent(() -> {
                    if (arenas.contains("arenas." + arenaName + ".team." + teamName + ".egg")) {
                        player.sendMessage("The egg is already set. Please reset it first.");
                    } else {
                        new EggAssistant(player, arenaName, teamName);
                        player.closeInventory();
                        player.sendMessage("[EggAssistant] You can now click a dragon egg to add it to the team.");
                    }
                });

        gui.addItem(spawn, items.getInt("items.editteam.spawn.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    if (arenas.getStringList("arenas." + arenaName + ".team." + teamName + ".spawn").size() < 4) {
                        ArenaManager.setTeamRegistered(arenaName, teamName, false);
                        ArenaManager.setArenaRegistered(arenaName, false, null);
                        ArenaManager.setSpawn(arenaName, teamName, null);

                        TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);

                        if (ArenaManager.isArenaRegistered(arenaName) && ArenaManager.isTeamRegistered(arenaName, teamName)) {
                            player.sendMessage("Reseted the spawns and unregistered the team and the arena.");
                        }
                        if (ArenaManager.isTeamRegistered(arenaName, teamName)) {
                            player.sendMessage("Reseted the spawns and unregistered the team.");
                        } else if (ArenaManager.isArenaRegistered(arenaName)) {
                            player.sendMessage("Reseted the spawns and unregistered the arena.");
                        }
                    } else {
                        player.sendMessage("Sorry 4 spawns are the limit for one team.");
                    }
                })
                .addDefaultEvent(() -> {
                    if (arenas.getStringList("arenas." + arenaName + ".team." + teamName + ".spawn").size() < 4) {
                        Location playerlocation = player.getLocation();
                        Block ground = player.getWorld().getBlockAt(new Location(playerlocation.getWorld(), playerlocation.getBlockX(), playerlocation.getBlockY() - 1, playerlocation.getBlockZ()));

                        if (ground.getType().equals(Material.EMERALD_BLOCK)) {
                            List<String> strings = arenas.getStringList("arenas." + arenaName + ".team." + teamName + ".spawn");
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
                                strings.add(UtilCore.convertString(playerlocation));

                                arenas.set("arenas." + arenaName + ".team." + teamName + ".spawn", strings);

                                try {
                                    EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                EggWarsReloaded.getEggWarsMain().reloadArenas();

                                TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);

                                player.sendMessage("[SpawnAssistant] Added spawn of team " + teamName + " to: " + playerlocation.getWorld().getName() + " " + playerlocation.getBlockX() + " " + playerlocation.getBlockY() + " " + playerlocation.getBlockZ());
                            } else {
                                player.sendMessage("[SpawnAssistant] This location is already registered!");
                            }
                        } else {
                            player.sendMessage("[SpawnAssistant] The block under needs to be a a emerald block!");
                        }
                    } else {
                        player.sendMessage("Sorry 4 spawns are the limit for one team.");
                    }
                });

        gui.addItem(back, items.getInt("items.editteam.back.slot"))
                .addDefaultEvent(() -> TeamMenu.setupTeamMenu(arenaName, player));

        gui.openGUI();
    }
}
