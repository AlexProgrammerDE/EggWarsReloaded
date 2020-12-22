package me.alexprogrammerde.EggWarsReloaded.game.collection;

public enum GameState {
    NONE,

    UNREGISTERED,

    REGISTERED,

    /**
     * People can still join and hang out!
     */
    LOBBY,

    /**
     * Starting countdown in the lobby!
     */
    STARTING1,

    /**
     * People have been teleported to their cages and wait for the game start!
     */
    STARTING2,

    RUNNING,

    ENDING,

    ENDED
}
