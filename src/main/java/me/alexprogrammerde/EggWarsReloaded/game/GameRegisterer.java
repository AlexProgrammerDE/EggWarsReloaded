package me.alexprogrammerde.EggWarsReloaded.game;

import java.util.HashMap;

public class GameRegisterer {
    public static HashMap<String, GameLib> gamesmap = new HashMap<>();

    public static void addGame(String arenaname) {
        gamesmap.put(arenaname, new GameLib(arenaname));
    }

    public static void removegame(String arenaname) {
        gamesmap.remove(arenaname);
    }
}
