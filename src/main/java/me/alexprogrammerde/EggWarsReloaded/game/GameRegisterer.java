package me.alexprogrammerde.EggWarsReloaded.game;

import java.util.HashMap;

public class GameRegisterer {
    public static HashMap<String, Game> gamesmap = new HashMap<>();

    public static void addGame(String arenaname, Game game) {
        if (!gamesmap.containsKey(arenaname)) {
            gamesmap.put(arenaname, game);
        }
    }

    public static void removegame(String arenaname) {
        gamesmap.remove(arenaname);
    }
}
