package me.alexprogrammerde.EggWarsReloaded.admin;

import me.alexprogrammerde.EggWarsReloaded.EggWarsMain;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamUnderMenu {

    public static void setupTeamUnderMenu(Inventory inventory, String color) {
        FileConfiguration items = EggWarsMain.getEggWarsMain().getItems();
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

        // Load Data from storage
        ItemStack shop = new ItemStack(Material.CHEST);
        ItemStack egg = new ItemStack(Material.DRAGON_EGG);
        ItemStack spawn = new ItemStack(Material.EMERALD_BLOCK);

        ItemMeta shopmeta = shop.getItemMeta();
        ItemMeta eggmeta = egg.getItemMeta();
        ItemMeta spawnmeta = spawn.getItemMeta();

        // Get item names from items.yml
        shopmeta.setDisplayName(items.getString("items.editteam.shop.name"));
        eggmeta.setDisplayName(items.getString("items.editteam.egg.name"));
        spawnmeta.setDisplayName(items.getString("items.editteam.spawn.name"));

        shop.setItemMeta(shopmeta);
        egg.setItemMeta(eggmeta);
        spawn.setItemMeta(spawnmeta);

        // Assign the items to the inventory
        inventory.setItem(items.getInt("items.editteam.shop.slot"), shop);
        inventory.setItem(items.getInt("items.editteam.egg.slot"), egg);
        inventory.setItem(items.getInt("items.editteam.spawn.slot"), spawn);
    }
}
