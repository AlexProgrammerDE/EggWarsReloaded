package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.game.collection.TeamColor;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MatchMaker {
    private final String arenaName;
    private final Game game;
    private final EggWarsReloaded plugin;

    // This value is used as a storage for possible locations for players
    private final HashMap<Location, TeamColor> spawns = new HashMap<>();

    private final HashMap<TeamColor, List<Location>> teams = new HashMap<>();

    private final HashMap<Player, Location> playerInLocation = new HashMap<>();

    protected final HashMap<Player, TeamColor> playerInTeam = new HashMap<>();

    protected final HashMap<TeamColor, Boolean> hasTeamEgg = new HashMap<>();

    public MatchMaker(String arenaName, Game game, EggWarsReloaded plugin) {
        this.arenaName = arenaName;
        this.game = game;
        this.plugin = plugin;
    }

    public void readSpawns() {
        FileConfiguration arenas = ArenaManager.getArenas();

        for (TeamColor team : game.usedTeams) {
            hasTeamEgg.put(team, true);

            List<Location> tempList = new ArrayList<>();

            for (String spawn : arenas.getStringList(arenaName + ".team." + team + ".spawn")) {
                spawns.put(UtilCore.convertLocation(spawn), team);
                tempList.add(UtilCore.convertLocation(spawn));
            }

            teams.put(team, tempList);
        }
    }

    public void teleportPlayers() {
        for (Player player : playerInLocation.keySet()) {
            player.setGameMode(GameMode.ADVENTURE);
            player.teleport(playerInLocation.get(player).getBlock().getLocation().add(0.5, 0, 0.5).setDirection(playerInLocation.get(player).getDirection()));
        }
    }

    public boolean isTeamFull(TeamColor team) {
        FileConfiguration arenas = plugin.getArenas();

        int spawnCount = 0;

        for (Location loc : teams.get(team)) {
            if (playerInLocation.containsValue(loc)) {
                spawnCount++;
            }
        }

        return spawnCount >= game.maxTeamPlayers;
    }

    public void setTeam(Player player, TeamColor team) {
        if (!isTeamFull(team)) {
            for (Location loc : teams.get(team)) {
                if (!playerInLocation.containsValue(loc)) {
                    playerInLocation.put(player, loc);
                    playerInTeam.put(player, team);
                    break;
                }
            }
        }
    }

    public void findTeamForPlayer(Player player) {
        if (!spawns.isEmpty()) {
            for (Location loc : spawns.keySet()) {
                playerInLocation.put(player, loc);
                playerInTeam.put(player, spawns.get(loc));
                spawns.remove(loc);
                break;
            }
        }
    }

    public TeamColor getTeamOfPlayer(Player player) {
        return playerInTeam.get(player);
    }

    public List<Player> getPlayersInTeam(TeamColor team) {
        List<Player> players = new ArrayList<>();

        for (Player player : playerInTeam.keySet()) {
            if (playerInTeam.get(player).equals(team)) {
                players.add(player);
            }
        }

        return players;
    }

    public Collection<TeamColor> getTeams() {
        return playerInTeam.values();
    }
}
