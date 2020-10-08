package me.alexprogrammerde.EggWarsReloaded.admin;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TeamUnderMenu {

    public static void setupTeamUnderMenu(Inventory inventory, String arenaname, String teamname) {
        FileConfiguration items = EggWarsReloaded.getEggWarsMain().getItems();
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

        // Load Data from storage
        ItemStack shop;

        if (arenas.contains("arenas." + arenaname + ".team." + teamname + ".shop")) {
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

        if (arenas.contains("arenas." + arenaname + ".team." + teamname + ".egg")) {
            Location egglocation = ArenaManager.getArenas().getLocation("arenas." + arenaname + ".team." + teamname + ".egg");

            egglist.add("Current: " + egglocation.getWorld().getName() + " "
                    + Math.round(egglocation.getBlockX()) + " "
                    + Math.round(egglocation.getBlockY()) + " "
                    + Math.round(egglocation.getBlockZ()));

            eggmeta.addEnchant(Enchantment.DURABILITY, 0, true);
            eggmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            egglist.add("Use shift + left click to reset it.");
        }

        eggmeta.setLore(egglist);

        List<String> shoplist = new ArrayList<>();
        shoplist.add("Left click to start the shop assistant.");

        if (arenas.contains("arenas." + arenaname + ".team." + teamname + ".shop")) {
            Location shoplocation = ArenaManager.getArenas().getLocation("arenas." + arenaname + ".team." + teamname + ".shop.location");

            shoplist.add("Current: " + shoplocation.getWorld().getName() + " "
                    + Math.round(shoplocation.getBlockX()) + " "
                    + Math.round(shoplocation.getBlockY()) + " "
                    + Math.round(shoplocation.getBlockZ()));

            shopmeta.addEnchant(Enchantment.DURABILITY, 0, true);
            shopmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            shoplist.add("Use shift + left click to reset it.");
        }

        shopmeta.setLore(shoplist);

        List<String> spawnlist = new ArrayList<>();
        spawnlist.add("Left click to start the shop assistant.");

        if (arenas.contains("arenas." + arenaname + ".team." + teamname + ".spawn")) {
            for (String spawns : ArenaManager.getArenas().getStringList("arenas." + arenaname + ".team." + teamname + ".spawn")) {
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

        // Assign the items to the inventory
        inventory.setItem(items.getInt("items.editteam.shop.slot"), shop);
        inventory.setItem(items.getInt("items.editteam.egg.slot"), egg);
        inventory.setItem(items.getInt("items.editteam.spawn.slot"), spawn);
        inventory.setItem(items.getInt("items.editteam.back.slot"), back);
    }
}
