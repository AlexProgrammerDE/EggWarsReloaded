package me.alexprogrammerde.EggWarsReloaded.game.shop;

import me.alexprogrammerde.EggWarsReloaded.game.collection.TeamColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum ShopItems {
    WOODEN_SWORD(new ItemPrice(0, 0, 0)),

    DIAMOND_SWORD(new ItemPrice(0, 0, 0)),

    DIAMOND_CHESTPLATE(new ItemPrice(0, 0, 0)),

    GOLDEN_APPLE(new ItemPrice(0, 0, 0)),

    END_STONE(new ItemPrice(0, 0, 0)),

    BOW(new ItemPrice(0, 0, 0)),

    ARROW(new ItemPrice(0, 0, 0)),

    DIAMOND_PICKAXE(new ItemPrice(0, 0, 0)),

    WHITE_WOOL(new ItemPrice(0, 0, 0));

    private final ItemPrice price;

    ShopItems(ItemPrice itemPrice) {
        this.price = itemPrice;
    }

    public ItemPrice getPrice() {
        return price;
    }

    public Material getMaterial(TeamColor color) {
        return Material.valueOf(toString()) == Material.WHITE_WOOL ? color.getMaterial() : Material.valueOf(toString());
    }

    public static void giveItem(Player player, ShopItems item, TeamColor color) {
        ItemStack itemStack = new ItemStack(item.getMaterial(color));

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
