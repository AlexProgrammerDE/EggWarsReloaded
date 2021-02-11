package net.pistonmaster.eggwarsreloaded.game;

import net.pistonmaster.eggwarsreloaded.game.collection.GameState;
import net.pistonmaster.eggwarsreloaded.game.collection.RejectType;
import net.pistonmaster.eggwarsreloaded.utils.StatsManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GameControl {
    private static final HashMap<Player, Game> players = new HashMap<>();
    private static final List<Game> games = new ArrayList<>();

    private GameControl() {
    }

    protected static void addGame(Game game) {
        games.add(game);
    }

    protected static void removeGame(Game game) {
        games.add(game);
    }

    public static RejectType addPlayer(Player player, Game game) {
        StatsManager.addPlayer(player);

        players.put(player, game);

        return game.addPlayer(player);
    }

    public static RejectType kickPlayer(Player player) {
        if (players.containsKey(player)) {
            Game game = players.get(player);
            players.remove(player);
            return game.kickPlayer(player);
        } else {
            return RejectType.NOT_IN;
        }
    }

    protected static void removePlayerFromGame(Player player) {
        players.remove(player);
    }

    public static Game getPlayerGame(Player player) {
        return players.get(player);
    }

    public static boolean isInGame(Player player) {
        return players.containsKey(player);
    }

    public static Game getGame(String arenaName) {
        Game returnedGame = null;

        for (Game game : games) {
            if (game.getArenaName().equalsIgnoreCase(arenaName))
                returnedGame = game;
        }

        return returnedGame;
    }

    public static RejectType randomJoin(Player player) {
        if (isInGame(player))
            return RejectType.ALREADY_IN;

        for (Game game : games) {
            if (game.getState() == GameState.LOBBY) {
                addPlayer(player, game);
                return RejectType.NONE;
            }
        }

        return RejectType.FULL;
    }

    public static List<String> getRegisteredArenas() {
        return games.stream().map(Game::getArenaName).collect(Collectors.toList());
    }
}
