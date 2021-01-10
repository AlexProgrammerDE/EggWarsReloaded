package me.alexprogrammerde.eggwarsreloaded.game.shop;

import me.alexprogrammerde.eggwarsreloaded.game.collection.TeamColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShopUtil {
    private ShopUtil() {}

    public static List<ItemStack> getIron(Player player) {
        List<ItemStack> iron = new ArrayList<>();

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.IRON_INGOT) {
                iron.add(item);
            }
        }

        return iron;
    }

    public static List<ItemStack> getGold(Player player) {
        List<ItemStack> gold = new ArrayList<>();

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.GOLD_INGOT) {
                gold.add(item);
            }
        }

        return gold;
    }

    public static List<ItemStack> getDiamonds(Player player) {
        List<ItemStack> diamond = new ArrayList<>();

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.DIAMOND) {
                diamond.add(item);
            }
        }

        return diamond;
    }

    public static int convertToAmount(List<ItemStack> list) {
        return list.stream().mapToInt(ItemStack::getAmount).sum();
    }

    public static void buyItem(ShopItems item, Player player, TeamColor color) {
        if (canPay(item.getPrice(), player)) {
            payPrice(item.getPrice(), player);

            ShopItems.giveItem(player, item, color);
        } else {
            player.sendMessage(ChatColor.GOLD + "You don't have enough money to buy this!");
        }
    }

    private static boolean canPay(ItemPrice price, Player player) {
        return convertToAmount(getIron(player)) >= price.getIron()
                && convertToAmount(getGold(player)) >= price.getGold()
                && convertToAmount(getDiamonds(player)) >= price.getDiamonds();
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
