package me.alexprogrammerde.EggWarsReloaded.utils;

import me.alexprogrammerde.EggWarsReloaded.game.collection.ShopItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShopUtil {
    public static List<ItemStack> getIron(Player player) {
        List<ItemStack> iron = new ArrayList<>();

        for (ItemStack item : player.getInventory().getContents()) {
            if (item.getType() == Material.IRON_INGOT) {
                iron.add(item);
            }
        }

        return iron;
    }

    public static List<ItemStack> getGold(Player player) {
        List<ItemStack> gold = new ArrayList<>();

        for (ItemStack item : player.getInventory().getContents()) {
            if (item.getType() == Material.GOLD_INGOT) {
                gold.add(item);
            }
        }

        return gold;
    }

    public static List<ItemStack> getDiamonds(Player player) {
        List<ItemStack> diamond = new ArrayList<>();

        for (ItemStack item : player.getInventory().getContents()) {
            if (item.getType() == Material.DIAMOND) {
                diamond.add(item);
            }
        }

        return diamond;
    }

    public static void buyItem(ShopItems item, Player player) {
        if (item == ShopItems.WOODEN_SWORD) {

        } else if (item == ShopItems.DIAMOND_SWORD) {

        } else if (item == ShopItems.DIAMOND_CHESTPLATE) {

        } else if (item == ShopItems.GOLDEN_APPLE) {

        }
    }
}
