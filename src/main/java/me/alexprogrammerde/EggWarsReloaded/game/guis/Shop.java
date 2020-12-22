package me.alexprogrammerde.EggWarsReloaded.game.guis;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.game.collection.ShopItems;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.ShopUtil;
import me.alexprogrammerde.EggWarsReloaded.utils.gui.GUI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Shop {
    public static void setupShopMenu(String arenaName, String teamName, Player player) {
        FileConfiguration items = EggWarsReloaded.getEggWarsMain().getItems();
        FileConfiguration arenas = ArenaManager.getArenas();

        GUI gui = new GUI(arenaName, 1, EggWarsReloaded.getEggWarsMain(), player);

        ItemStack woodenSword = new ItemStack(Material.WOODEN_SWORD);
        ItemStack diamondSword = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemStack goldenApple = new ItemStack(Material.GOLDEN_APPLE);

        gui.addItem(woodenSword, 0).addDefaultEvent(() -> {
            ShopUtil.buyItem(ShopItems.WOODEN_SWORD, player);
        });

        gui.addItem(diamondSword, 0).addDefaultEvent(() -> {
            ShopUtil.buyItem(ShopItems.DIAMOND_SWORD, player);
        });

        gui.addItem(chestplate, 0).addDefaultEvent(() -> {
            ShopUtil.buyItem(ShopItems.DIAMOND_CHESTPLATE, player);
        });

        gui.addItem(goldenApple, 0).addDefaultEvent(() -> {
            ShopUtil.buyItem(ShopItems.GOLDEN_APPLE, player);
        });
    }
}
