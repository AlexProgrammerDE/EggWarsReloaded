package me.alexprogrammerde.EggWarsReloaded.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameLogics {
    private final Game game;

    public GameLogics(Game game) {
        this.game = game;
    }

    public boolean isOnlyOneTeamLeft() {
        List<String> teams = new ArrayList<>();

        for (Player player : game.matchmaker.playerInTeam.keySet()) {
            if (game.livingPlayers.contains(player)) {
                teams.add(game.matchmaker.playerInTeam.get(player));
            }
        }

        return teams.size() == 1;
    }

    public String getLastTeam() {
        List<String> teams = new ArrayList<>();

        for (Player player : game.matchmaker.playerInTeam.keySet()) {
            if (game.livingPlayers.contains(player)) {
                teams.add(game.matchmaker.playerInTeam.get(player));
            }
        }

        return teams.get(0);
    }
}
