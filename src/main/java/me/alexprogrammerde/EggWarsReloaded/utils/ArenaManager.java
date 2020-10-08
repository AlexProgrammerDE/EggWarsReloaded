package me.alexprogrammerde.EggWarsReloaded.utils;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.List;

public class ArenaManager {
    // WORLD + X + Y + X + YAW + PITCH


    public static FileConfiguration getArenas() {
        return EggWarsReloaded.getEggWarsMain().getArenas();
    }

    public static void setMainLobby(String arenaname, Location location) {
        EggWarsReloaded.getEggWarsMain().getArenas().set("arenas." + arenaname + ".mainlobby", location.getWorld().getName() + " " + location.getX() + " " + location.getY() + " " + location.getZ() + " " + location.getYaw() + " " + location.getPitch());

        try {
            EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setWaitingLobby(String arenaname, Location location) {
        EggWarsReloaded.getEggWarsMain().getArenas().set("arenas." + arenaname + ".waitinglobby", location.getWorld().getName() + " " + location.getX() + " " + location.getY() + " " + location.getZ() + " " + location.getYaw() + " " + location.getPitch());

        try {
            EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setSpectator(String arenaname, Location location) {
        EggWarsReloaded.getEggWarsMain().getArenas().set("arenas." + arenaname + ".spectator", location.getWorld().getName() + " " + location.getX() + " " + location.getY() + " " + location.getZ() + " " + location.getYaw() + " " + location.getPitch());

        try {
            EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static Location getMainLobby(String arenaname) {
        String[] split = EggWarsReloaded.getEggWarsMain().getArenas().getString("arenas." + arenaname + ".mainlobby").split(" ");

        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }

    public static Location getWaitingLobby(String arenaname) {
        String[] split = EggWarsReloaded.getEggWarsMain().getArenas().getString("arenas." + arenaname + ".waitinglobby").split(" ");

        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }

    public static Location getSpectator(String arenaname) {
        String[] split = EggWarsReloaded.getEggWarsMain().getArenas().getString("arenas." + arenaname + ".spectator").split(" ");

        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }

    public static void setArenaRegistered(String arenaname, boolean register, List<String> teams) {
        EggWarsReloaded.getEggWarsMain().getArenas().set("arenas." + arenaname + ".registered", register);
        EggWarsReloaded.getEggWarsMain().getArenas().set("arenas." + arenaname + ".registeredteams", teams);

        try {
            EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setTeamSize(String arenaname, Integer size) {
        EggWarsReloaded.getEggWarsMain().getArenas().set("arenas." + arenaname + ".size", size);

        try {
            EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static int getTeamSize(String arenaname) {
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

        return arenas.getInt("arenas." + arenaname + ".size");
    }

    public static boolean isArenaRegistered(String arenaname) {
        return EggWarsReloaded.getEggWarsMain().getArenas().getBoolean("arenas." + arenaname + ".registered");
    }

    public static void setTeamRegistered(String arenaname, String teamname, boolean register) {
        EggWarsReloaded.getEggWarsMain().getArenas().set("arenas." + arenaname + ".team." + teamname + ".registered", register);

        try {
            EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static boolean isTeamRegistered(String arenaname, String teamname) {
        return EggWarsReloaded.getEggWarsMain().getArenas().getBoolean("arenas." + arenaname + ".team." + teamname + ".registered");
    }

    public static void setEgg(String arenaname, String teamname, Location egglocation) {
        EggWarsReloaded.getEggWarsMain().getArenas().set("arenas." + arenaname + ".team." + teamname + ".egg", egglocation);

        try {
            EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setArenaPos1(String arenaname, Location pos1) {
        EggWarsReloaded.getEggWarsMain().getArenas().set("arenas." + arenaname + ".pos1", pos1);

        try {
            EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setArenaPos2(String arenaname, Location pos2) {
        EggWarsReloaded.getEggWarsMain().getArenas().set("arenas." + arenaname + ".pos2", pos2);

        try {
            EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setArenaWorld(String arenaname, String world) {
        EggWarsReloaded.getEggWarsMain().getArenas().set("arenas." + arenaname + ".world", world);

        try {
            EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void addArena(String arenaname) {
        EggWarsReloaded.getEggWarsMain().getArenas().createSection("arenas." + arenaname);
        EggWarsReloaded.getEggWarsMain().getArenas().set("arenas." + arenaname + ".size", 1);

        try {
            EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void deleteArena(String arenaname) {
        EggWarsReloaded.getEggWarsMain().getArenas().set("arenas." + arenaname, null);

        try {
            EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static boolean isArenaReady(String arenaname) {
        // TODO Check for generators
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

        if (!arenas.contains("arenas." + arenaname + ".mainlobby")) {
            return false;
        }

        if (!arenas.contains("arenas." + arenaname + ".waitinglobby")) {
            return false;
        }

        if (!arenas.contains("arenas." + arenaname + ".spectator")) {
            return false;
        }

        return true;
    }

    public static boolean isTeamReady(String arenaname, String teamname) {
        FileConfiguration arenas = ArenaManager.getArenas();

        if (arenas.contains("arenas." + arenaname + ".team." + teamname + ".egg") && arenas.contains("arenas." + arenaname + ".team." + teamname + ".shop") && arenas.contains("arenas." + arenaname + ".team." + teamname + ".spawn")) {
            return true;
        }

        return false;
    }

    public static void setShop(String arenaname, String teamname, String uuid, Location location) {
        FileConfiguration arenas = ArenaManager.getArenas();

        if (uuid == null || location == null) {
            arenas.set("arenas." + arenaname + ".team." + teamname + ".shop", null);
        } else {
            arenas.set("arenas." + arenaname + ".team." + teamname + ".shop.uuid", uuid);
            arenas.set("arenas." + arenaname + ".team." + teamname + ".shop.location", location);
        }

        try {
            EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setSpawn(String arenaname, String teamname, Location location) {
        FileConfiguration arenas = ArenaManager.getArenas();

        arenas.set("arenas." + arenaname + ".team." + teamname + ".spawn", location);

        try {
            EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }
}
