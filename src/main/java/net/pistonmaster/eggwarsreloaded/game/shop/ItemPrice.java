package net.pistonmaster.eggwarsreloaded.game.shop;

public class ItemPrice {
    private final int iron;

    private final int gold;

    private final int diamonds;

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
