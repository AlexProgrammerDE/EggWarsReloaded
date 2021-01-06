package me.alexprogrammerde.EggWarsReloaded.game.shop;

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
}
