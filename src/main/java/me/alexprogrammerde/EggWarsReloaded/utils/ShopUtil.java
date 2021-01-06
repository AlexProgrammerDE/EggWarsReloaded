package me.alexprogrammerde.EggWarsReloaded.utils;

import me.alexprogrammerde.EggWarsReloaded.game.collection.ItemPrice;
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

    public static int convertToAmount(List<ItemStack> list) {
        int i = 0;

        for (ItemStack item : list) {
            i = i + item.getAmount();
        }

        return i;
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
        if (canPay(item.getPrice(), player)) {
            payPrice(item.getPrice(), player);

            // TODO: Add item to his inventory here!
        } else {
            player.sendMessage("You don't have enough money to buy this!");
        }
    }

    private static boolean canPay(ItemPrice price, Player player) {
        if (convertToAmount(getIron(player)) < price.getIron())
            return false;

        if (convertToAmount(getGold(player)) < price.getGold())
            return false;

        if (convertToAmount(getDiamonds(player)) < price.getDiamonds())
            return false;

        return true;
    }

    private static void payPrice(ItemPrice price, Player player) {
        subtractAmount(getIron(player), price.getIron());
        subtractAmount(getGold(player), price.getGold());
        subtractAmount(getDiamonds(player), price.getDiamonds());
    }

    private static void subtractAmount(List<ItemStack> list, int amount) {
        int i = amount;

        for (ItemStack item : list) {
            int amountOfItem = item.getAmount();

            if (i == amountOfItem) {
                item.setAmount(0);

                break;
            } else if (i > amountOfItem) {
                item.setAmount(0);

                i = i - amountOfItem;
            } else { // i < amountOfItem
                item.setAmount(amountOfItem - i);
                break;
            }
        }
    }
}
