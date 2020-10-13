package me.alexprogrammerde.EggWarsReloaded.admin.guis;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.admin.assistants.EggAssistant;
import me.alexprogrammerde.EggWarsReloaded.admin.assistants.ShopAssistant;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import me.alexprogrammerde.EggWarsReloaded.utils.gui.GUI;
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
        ItemStack respawn = new ItemStack(Material.REDSTONE_BLOCK);
        ItemStack back = new ItemStack(Material.BARRIER);

        ItemMeta shopmeta = shop.getItemMeta();
        ItemMeta eggmeta = egg.getItemMeta();
        ItemMeta spawnmeta = spawn.getItemMeta();
        ItemMeta respawnmeta = respawn.getItemMeta();
        ItemMeta backmeta = back.getItemMeta();

        // Get item names from items.yml
        shopmeta.setDisplayName(items.getString("items.editteam.shop.name"));
        eggmeta.setDisplayName(items.getString("items.editteam.egg.name"));
        spawnmeta.setDisplayName(items.getString("items.editteam.spawn.name"));
        respawnmeta.setDisplayName(items.getString("items.editteam.respawn.name"));
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
        spawnlist.add("Left click to start the spawn assistant.");

        if (arenas.contains("arenas." + arenaName + ".team." + teamName + ".spawn")) {
            for (String spawns : ArenaManager.getArenas().getStringList("arenas." + arenaName + ".team." + teamName + ".spawn")) {
                Location loc = UtilCore.convertLocation(spawns);

                spawnlist.add(loc.getWorld().getName() + " "
                        + loc.getX() + " "
                        + loc.getY() + " "
                        + loc.getZ() + " "
                        + loc.getYaw() + " "
                        + loc.getPitch());
            }

            spawnmeta.addEnchant(Enchantment.DURABILITY, 0, true);
            spawnmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            spawnlist.add("Use shift + left click to reset it.");
        }

        spawnmeta.setLore(spawnlist);

        List<String> respawnlist = new ArrayList<>();
        respawnlist.add("Left click to set your current place as the respawnpoint.");

        if (arenas.contains("arenas." + arenaName + ".team." + teamName + ".respawn")) {
            Location loc = UtilCore.convertLocation(arenas.getString("arenas." + arenaName + ".team." + teamName + ".respawn"));

            respawnlist.add(loc.getWorld().getName() + " "
                    + loc.getX() + " "
                    + loc.getY() + " "
                    + loc.getZ() + " "
                    + loc.getYaw() + " "
                    + loc.getPitch());

            respawnmeta.addEnchant(Enchantment.DURABILITY, 0, true);
            respawnmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            respawnlist.add("Use shift + left click to reset it.");
        }

        respawnmeta.setLore(respawnlist);

        shop.setItemMeta(shopmeta);
        egg.setItemMeta(eggmeta);
        spawn.setItemMeta(spawnmeta);
        respawn.setItemMeta(respawnmeta);
        back.setItemMeta(backmeta);

        GUI gui = new GUI(arenaName, 3, EggWarsReloaded.getEggWarsMain(), player);

        gui.addItem(shop, items.getInt("items.editteam.shop.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setShop(arenaName, teamName, null, null);
                    ArenaManager.setTeamRegistered(arenaName, teamName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);

                    player.sendMessage("[ShopAssistant] Reset the shop and unregistered the team and the arena. Use /kill to remove the villager.");
                })
                .addDefaultEvent(() -> {
                    if (arenas.contains("arenas." + arenaName + ".team." + teamName + ".shop")) {
                        player.sendMessage("[ShopAssistant] The shop is already set. Please reset it first.");
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

                    player.sendMessage("[EggAssistant] Reset the egg and unregistered the team and the arena. Remove the egg manually in creative mode.");
                })
                .addDefaultEvent(() -> {
                    if (arenas.contains("arenas." + arenaName + ".team." + teamName + ".egg")) {
                        player.sendMessage("[EggAssistant] The egg is already set. Please reset it first.");
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
                        ArenaManager.setFirstSpawn(arenaName, teamName, null);

                        player.sendMessage("[SpawnAssistant] Reset the spawns and unregistered the team and the arena.");
                    } else {
                        player.sendMessage("[SpawnAssistant] Sorry 4 spawns are the limit for one team.");
                    }

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);
                })
                .addDefaultEvent(() -> {
                    if (arenas.getStringList("arenas." + arenaName + ".team." + teamName + ".spawn").size() < 4) {
                        Location playerlocation = player.getLocation();
                        Block ground = player.getWorld().getBlockAt(new Location(playerlocation.getWorld(), playerlocation.getBlockX(), playerlocation.getBlockY() - 1, playerlocation.getBlockZ()));

                        if (ground.getType().equals(Material.EMERALD_BLOCK)) {
                            List<String> strings = arenas.getStringList("arenas." + arenaName + ".team." + teamName + ".spawn");
                            List<Location> locations = new ArrayList<>();

                            for (String str : strings) {
                                locations.add(UtilCore.convertLocation(str));
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

                                player.sendMessage("[SpawnAssistant] Added spawn of team " + teamName + " to: " + playerlocation.getWorld().getName() + " " + playerlocation.getBlockX() + " " + playerlocation.getBlockY() + " " + playerlocation.getBlockZ());
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

        gui.addItem(respawn, items.getInt("items.editteam.respawn.slot"))
                .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                    ArenaManager.setTeamRegistered(arenaName, teamName, false);
                    ArenaManager.setArenaRegistered(arenaName, false, null);
                    ArenaManager.setRespawn(arenaName, teamName, null);

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);

                    player.sendMessage("[RespawnAssistant] Reset the respawn and unregistered the team and the arena.");
                })
                .addDefaultEvent(() -> {
                    Location playerlocation = player.getLocation();

                    if (!arenas.contains("arenas." + arenaName + ".team." + teamName + ".respawn")) {
                        ArenaManager.setRespawn(arenaName, teamName, playerlocation);

                        player.sendMessage("[RespawnAssistant] Set respawn of team " + teamName + " to: " + playerlocation.getWorld().getName() + " " + playerlocation.getBlockX() + " " + playerlocation.getBlockY() + " " + playerlocation.getBlockZ());
                    } else {
                        player.sendMessage("[RespawnAssistant] The respawn is already registered! Please reset it first!");
                    }

                    TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);
                });

        gui.addItem(back, items.getInt("items.editteam.back.slot"))
                .addDefaultEvent(() -> TeamMenu.setupTeamMenu(arenaName, player));

        gui.openGUI();
    }
}
