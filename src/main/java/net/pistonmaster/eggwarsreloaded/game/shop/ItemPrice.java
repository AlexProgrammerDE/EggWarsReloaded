package net.pistonmaster.eggwarsreloaded.game.shop;

import lombok.Data;

public @Data
class ItemPrice {
    private final int iron;
    private final int gold;
    private final int diamonds;

    public ItemPrice(int iron, int gold, int diamonds) {
        this.iron = iron;
        this.gold = gold;
        this.diamonds = diamonds;
    }
}
