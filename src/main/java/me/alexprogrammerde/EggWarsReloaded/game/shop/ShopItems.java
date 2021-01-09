package me.alexprogrammerde.EggWarsReloaded.game.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum ShopItems {
    WOODEN_SWORD(new ItemPrice(0, 0, 0)),

    DIAMOND_SWORD(new ItemPrice(0, 0, 0)),

    DIAMOND_CHESTPLATE(new ItemPrice(0, 0, 0)),

    GOLDEN_APPLE(new ItemPrice(0, 0, 0));

    private final ItemPrice price;

    ShopItems(ItemPrice itemPrice) {
        this.price = itemPrice;
    }

    public ItemPrice getPrice() {
        return price;
    }

    public Material getMaterial() {
        return Material.valueOf(toString());
    }

    public static void giveItem(Player player, ShopItems item) {
        ItemStack itemStack = new ItemStack(Material.valueOf(item.toString()));

        /*

        if (item == ShopItems.WOODEN_SWORD) {

        } else if (item == ShopItems.DIAMOND_SWORD) {

        } else if (item == ShopItems.DIAMOND_CHESTPLATE) {

        } else if (item == ShopItems.GOLDEN_APPLE) {

        }

       */

        player.getInventory().addItem(itemStack);
    }
}
