package me.alexprogrammerde.eggwarsreloaded.game.shop;

import me.alexprogrammerde.eggwarsreloaded.game.collection.TeamColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum ShopItems {
    WOODEN_SWORD(new ItemPrice(3, 0, 0)),

    DIAMOND_SWORD(new ItemPrice(0, 4, 1)),

    COOKED_BEEF(3, new ItemPrice(2, 1, 0)),

    GOLDEN_APPLE(3, new ItemPrice(3, 2, 0)),

    BOW(new ItemPrice(2, 5, 0)),

    ARROW(5, new ItemPrice(2, 0, 0)),

    DIAMOND_CHESTPLATE(new ItemPrice(0, 2, 3)),

    DIAMOND_PICKAXE(new ItemPrice(0, 2, 1)),

    END_STONE(10, new ItemPrice(3, 0, 0)),

    WHITE_WOOL(15, new ItemPrice(1, 0, 0));

    private final ItemPrice price;
    private final int amount;

    ShopItems(ItemPrice itemPrice) {
        this.price = itemPrice;
        this.amount = 1;
    }

    ShopItems(int amount, ItemPrice itemPrice) {
        this.price = itemPrice;
        this.amount = amount;
    }

    public static void giveItem(Player player, ShopItems item, TeamColor color) {
        ItemStack itemStack = new ItemStack(item.getMaterial(color));

        itemStack.setAmount(item.getAmount());

        player.getInventory().addItem(itemStack);
    }

    public ItemPrice getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public Material getMaterial(TeamColor color) {
        return Material.valueOf(toString()) == Material.WHITE_WOOL ? color.getMaterial() : Material.valueOf(toString());
    }
}
