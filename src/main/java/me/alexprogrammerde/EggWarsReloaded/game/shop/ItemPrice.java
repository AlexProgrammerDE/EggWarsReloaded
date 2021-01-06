package me.alexprogrammerde.EggWarsReloaded.game.shop;

public class ItemPrice {
    public int iron;

    public int gold;

    public int diamonds;

    public ItemPrice(int iron, int gold, int diamonds) {
        this.iron = iron;
        this.gold = gold;
        this.diamonds = diamonds;
    }

    public int getIron() {
        return iron;
    }

    public int getGold() {
        return gold;
    }

    public int getDiamonds() {
        return diamonds;
    }
}
