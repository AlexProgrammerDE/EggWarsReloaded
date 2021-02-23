package net.pistonmaster.eggwarsreloaded.game;

import net.pistonmaster.eggwarsreloaded.game.collection.TeamColor;
import net.pistonmaster.eggwarsreloaded.utils.ArenaManager;
import net.pistonmaster.eggwarsreloaded.utils.UtilCore;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.stream.Collectors;

public class MatchMaker {
    protected final Map<TeamColor, Boolean> hasTeamEgg = new EnumMap<>(TeamColor.class);

    // This value is used as a storage for possible locations for players
    private final Map<Location, TeamColor> spawns = new HashMap<>();
    private final Map<TeamColor, List<Location>> teams = new EnumMap<>(TeamColor.class);
    private final Map<UUID, Location> playerInLocation = new HashMap<>();
    protected final Map<UUID, TeamColor> playerInTeam = new HashMap<>();

    private final String arenaName;
    private final Game game;

    public MatchMaker(String arenaName, Game game) {
        this.arenaName = arenaName;
        this.game = game;
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
        cleanUpTeams();

        for (Map.Entry<UUID, Location> entry : playerInLocation.entrySet()) {
            getPlayer(entry.getKey()).setGameMode(GameMode.ADVENTURE);
            getPlayer(entry.getKey()).teleport(entry.getValue().getBlock().getLocation().add(0.5, 0, 0.5).setDirection(entry.getValue().getDirection()));
        }
    }

    public boolean isTeamFull(TeamColor team) {
        cleanUpTeams();
        int spawnCount = 0;

        for (Location loc : teams.get(team)) {
            if (playerInLocation.containsValue(loc))
                spawnCount++;
        }

        return spawnCount >= game.maxTeamPlayers;
    }

    public void setTeam(Player player, TeamColor team) {
        cleanUpTeams();
        if (!isTeamFull(team)) {
            for (Location loc : teams.get(team)) {
                if (!playerInLocation.containsValue(loc)) {
                    playerInLocation.put(player.getUniqueId(), loc);
                    playerInTeam.put(player.getUniqueId(), team);
                    break;
                }
            }
        }
    }

    public void findTeamForPlayer(Player player) {
        cleanUpTeams();
        playerInTeam.remove(player.getUniqueId());
        playerInLocation.remove(player.getUniqueId());

        if (!spawns.isEmpty()) {
            Optional<TeamColor> match = getPerfectNonFullTeam();

            if (match.isPresent()) {
                for (Map.Entry<Location, TeamColor> entry : spawns.entrySet()) {
                    if (entry.getValue().equals(match.get())) {
                        playerInLocation.put(player.getUniqueId(), entry.getKey());
                        playerInTeam.put(player.getUniqueId(), spawns.get(entry.getKey()));
                        spawns.remove(entry.getKey());
                        return;
                    }
                }
            }

            List<Location> keysAsArray = new ArrayList<>(spawns.keySet());
            Location loc = keysAsArray.get(game.random.nextInt(keysAsArray.size()));

            playerInLocation.put(player.getUniqueId(), loc);
            playerInTeam.put(player.getUniqueId(), spawns.get(loc));
            spawns.remove(loc);
        }
    }

    public TeamColor getTeamOfPlayer(Player player) {
        cleanUpTeams();

        return playerInTeam.get(player.getUniqueId());
    }

    public List<Player> getPlayersInTeam(TeamColor team) {
        cleanUpTeams();
        List<Player> players = new ArrayList<>();

        for (Map.Entry<UUID, TeamColor> entry : playerInTeam.entrySet()) {
            if (entry.getValue().equals(team))
                players.add(getPlayer(entry.getKey()));
        }

        return players;
    }

    public Collection<TeamColor> getTeams() {
        cleanUpTeams();
        return playerInTeam.values();
    }

    public void cleanUpTeams() {
        playerInTeam.keySet().stream().filter(uuid -> getPlayer(uuid) == null).collect(Collectors.toList()).forEach(playerInTeam::remove);
        playerInLocation.keySet().stream().filter(uuid -> getPlayer(uuid) == null).collect(Collectors.toList()).forEach(playerInLocation::remove);

        new ArrayList<>(playerInTeam.keySet()).forEach(player -> {
            if (!getPlayer(player).isOnline()) {
                playerInTeam.remove(player);
            }
        });

        new ArrayList<>(playerInLocation.keySet()).forEach(player -> {
            if (!getPlayer(player).isOnline()) {
                playerInLocation.remove(player);
            }
        });
    }

    private Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    private Optional<TeamColor> getPerfectNonFullTeam() {
        TeamColor color = null;
        int maxTeamSize = ArenaManager.getTeamSize(game.getArenaName());
        Map<TeamColor, Integer> counts = new EnumMap<>(TeamColor.class);

        for (TeamColor teamColor : playerInTeam.values()) {
            if (counts.containsKey(teamColor)) {
                counts.put(teamColor, counts.get(teamColor) + 1);
            } else {
                counts.put(teamColor, 1);
            }
        }

        for (Map.Entry<TeamColor, Integer> entry : counts.entrySet()) {
            if (entry.getValue() < maxTeamSize) {
                color = entry.getKey();
                break;
            }
        }

        return Optional.ofNullable(color);
    }
}
