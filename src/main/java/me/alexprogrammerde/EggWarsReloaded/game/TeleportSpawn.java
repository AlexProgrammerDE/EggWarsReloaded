package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeleportSpawn {
    String arenaName;
    HashMap<String, List<Location>> teams = new HashMap<>();
    HashMap<String, List<Player>> playersinteams = new HashMap<>();
    HashMap<Location, Player> playerinlocation = new HashMap<>();

    public TeleportSpawn(String arenaName) {
        this.arenaName = arenaName;
    }

    public void addTeam(String team) {
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

        List<Location> spawns = new ArrayList<>();

        for (String spawn : arenas.getStringList("arenas." + arenaName + ".team." + team + ".spawn")) {
            spawns.add(UtilCore.convertLocation(spawn));
        }

        teams.put(team, spawns);
    }

    public void teleportPlayer(Player player, String team) {
        List<Location> list = teams.get(team);

        for (Location loc : list) {
            if (!playerinlocation.containsKey(loc)) {
                if (playersinteams.containsKey(team)) {
                    if (!playersinteams.get(team).contains(player)) {
                        playersinteams.get(team).add(player);
                    }
                } else {
                    playersinteams.put(team, new ArrayList<Player>() {{
                        add(player);
                    }});
                }

                playerinlocation.put(loc, player);

                player.teleport(loc);
                break;
            }
        }
    }

    public boolean isTeamFull(String team) {
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

        return playersinteams.get(team).size() != arenas.getInt("arenas." + arenaName + ".size");
    }

    public void setTeam(Player player) {

    }
}