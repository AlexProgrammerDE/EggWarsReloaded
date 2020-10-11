package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatchMaker {
    private final String arenaName;

    // This value is used as a storage for possible locations for players
    private final HashMap<Location, String> spawns = new HashMap<>();

    private final HashMap<String, List<Location>> teams = new HashMap<>();

    private final HashMap<Player, Location> playerInLocation = new HashMap<>();

    private final HashMap<Player, String> playerInTeam = new HashMap<>();

    private final Game game;

    public MatchMaker(String arenaName, Game game) {
        this.arenaName = arenaName;
        this.game = game;
    }

    public void readSpawns() {
        FileConfiguration arenas = ArenaManager.getArenas();

        for (String team : game.usedTeams) {
            List<Location> tempList = new ArrayList<>();

            for (String spawn : arenas.getStringList("arenas." + arenaName + ".team." + team + ".spawn")) {
                spawns.put(UtilCore.convertLocation(spawn), team);
                tempList.add(UtilCore.convertLocation(spawn));
            }

            teams.put(team, tempList);
        }
    }

    public void teleportPlayers() {
        for (Player player : playerInLocation.keySet()) {
            player.sendMessage("b");
            player.teleport(playerInLocation.get(player));
        }
    }

    public boolean isTeamFull(String team) {
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

        int spawnCount = 0;

        for (Location loc : teams.get(team)) {
            if (playerInLocation.containsValue(loc)) {
                spawnCount++;
            }
        }

        return spawnCount >= game.maxTeamPlayers;
    }

    public void setTeam(Player player, String team) {
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

    public String getTeamOfPlayer(Player player) {
        return playerInTeam.get(player);
    }
}
