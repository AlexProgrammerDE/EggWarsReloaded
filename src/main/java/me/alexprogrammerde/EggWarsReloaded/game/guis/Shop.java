package me.alexprogrammerde.EggWarsReloaded.game.guis;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.game.shop.ShopItems;
import me.alexprogrammerde.EggWarsReloaded.game.shop.ShopUtil;
import me.alexprogrammerde.EggWarsReloaded.utils.gui.GUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Shop {
    public static void setupShopMenu(String teamName, Player player) {
        GUI gui = new GUI("Shop", 1, EggWarsReloaded.get(), player);

        ItemStack woodenSword = new ItemStack(Material.WOODEN_SWORD);
        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemStack goldenApple = new ItemStack(Material.GOLDEN_APPLE);
        ItemStack closeButton = new ItemStack(Material.BARRIER);

        gui.addItem(woodenSword, 0).addDefaultEvent(() -> {
            ShopUtil.buyItem(ShopItems.WOODEN_SWORD, player);

            setupShopMenu(teamName, player);
        });

        gui.addItem(diamondSword, 1).addDefaultEvent(() -> {
            ShopUtil.buyItem(ShopItems.DIAMOND_SWORD, player);

            setupShopMenu(teamName, player);
        });

        gui.addItem(chestplate, 2).addDefaultEvent(() -> {
            ShopUtil.buyItem(ShopItems.DIAMOND_CHESTPLATE, player);

            setupShopMenu(teamName, player);
        });

        gui.addItem(goldenApple, 3).addDefaultEvent(() -> {
            ShopUtil.buyItem(ShopItems.GOLDEN_APPLE, player);

            setupShopMenu(teamName, player);
        });

        gui.addItem(closeButton, 8).addDefaultEvent(player::closeInventory);

        gui.openGUI();
    }
}
