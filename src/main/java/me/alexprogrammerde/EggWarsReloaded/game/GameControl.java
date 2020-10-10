package me.alexprogrammerde.EggWarsReloaded.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameControl {
    private static final HashMap<Player, Game> players = new HashMap<>();
    private static final List<Game> games = new ArrayList<>();

    protected static void addGame(Game game) {
        games.add(game);
    }

    protected static void removeGame(Game game) {
        games.add(game);
    }

    public static void addPlayer(Player player, Game game) {
        if (!games.contains(game)) {
            addGame(game);
        }

        players.put(player, game);

        game.addPlayer(player);
    }

    public static void removePlayer(Player player) {
        if (players.containsKey(player)) {
            players.get(player).removePlayer(player);
        }
    }

    public static Game getPlayerGame(Player player) {
        return players.get(player);
    }

    public static boolean isInGame(Player player) {
        return players.containsKey(player);
    }
}
