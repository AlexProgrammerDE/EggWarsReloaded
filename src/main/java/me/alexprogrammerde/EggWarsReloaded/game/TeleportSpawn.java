package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
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
            String[] spawnsplit = spawn.split(" ");

            spawns.add(new Location(Bukkit.getWorld(spawnsplit[0]), Double.parseDouble(spawnsplit[1]), Double.parseDouble(spawnsplit[2]), Double.parseDouble(spawnsplit[3]), Float.parseFloat(spawnsplit[4]), Float.parseFloat(spawnsplit[5])));
        }

        teams.put(team, spawns);
    }

    public void teleportPlayer(Player player, String team) {
        boolean teleportplayer = false;
        List<Location> list = teams.get(team);
        Location teleportto = null;

        for (Location loc : list) {
            if (!playerinlocation.containsKey(loc)) {
                teleportplayer = true;
                teleportto = loc;
                break;
            }
        }

        if (teleportplayer) {
            if (playersinteams.containsKey(team)) {
                if (!playersinteams.get(team).contains(player)) {
                    playersinteams.get(team).add(player);
                }
            } else {
                playersinteams.put(team, new ArrayList<Player>() {{
                    add(player);
                }});
            }

            playerinlocation.put(teleportto, player);

            player.teleport(teleportto);
        }
    }

    public boolean isTeamFull(String team) {
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

        return playersinteams.get(team).size() != arenas.getInt("arenas." + arenaName + ".size");
    }

    public void setTeam(Player player) {

    }
}