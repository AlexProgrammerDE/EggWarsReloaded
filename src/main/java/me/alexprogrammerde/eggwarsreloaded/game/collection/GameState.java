package me.alexprogrammerde.eggwarsreloaded.game.collection;

public enum GameState {
    UNREGISTERED(0),

    REGISTERED(1),

    /**
     * People can still join and hang out!
     */
    LOBBY(2),

    /**
     * Starting countdown in the lobby!
     */
    STARTING1(3),

    /**
     * People have been teleported to their cages and wait for the game start!
     */
    STARTING2(4),

    RUNNING(5),

    ENDING(6),

    ENDED(7);

    private final int value;

    GameState(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
