package me.alexprogrammerde.EggWarsReloaded.game;

import java.util.HashMap;

public class GameRegisterer {
    public static HashMap<String, GameLib> gamesmap = new HashMap<>();

    public static void addGame(String arenaName) {
        gamesmap.put(arenaName, new GameLib(arenaName));
    }

    public static void removegame(String arenaName) {
        gamesmap.remove(arenaName);
    }
}
