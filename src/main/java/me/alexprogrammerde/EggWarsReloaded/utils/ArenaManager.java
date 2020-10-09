package me.alexprogrammerde.EggWarsReloaded.utils;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.List;

public class ArenaManager {
    public static FileConfiguration getArenas() {
        return getArenas();
    }

    public static void setMainLobby(String arenaName, Location location) {
        getArenas().set("arenas." + arenaName + ".mainlobby", UtilCore.convertString(location));

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setWaitingLobby(String arenaName, Location location) {
        getArenas().set("arenas." + arenaName + ".waitinglobby", UtilCore.convertString(location));

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setSpectator(String arenaName, Location location) {
        getArenas().set("arenas." + arenaName + ".spectator", UtilCore.convertString(location));

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static Location getMainLobby(String arenaname) {
        return UtilCore.convertLocation(getArenas().getString("arenas." + arenaname + ".mainlobby"));
    }

    public static Location getWaitingLobby(String arenaname) {
        return UtilCore.convertLocation(getArenas().getString("arenas." + arenaname + ".waitinglobby"));
    }

    public static Location getSpectator(String arenaname) {
        return UtilCore.convertLocation(getArenas().getString("arenas." + arenaname + ".spectator"));
    }

    public static void setArenaRegistered(String arenaname, boolean register, List<String> teams) {
        getArenas().set("arenas." + arenaname + ".registered", register);
        getArenas().set("arenas." + arenaname + ".registeredteams", teams);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setTeamSize(String arenaname, Integer size) {
        getArenas().set("arenas." + arenaname + ".size", size);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static int getTeamSize(String arenaname) {
        FileConfiguration arenas = getArenas();

        return arenas.getInt("arenas." + arenaname + ".size");
    }

    public static boolean isArenaRegistered(String arenaname) {
        return getArenas().getBoolean("arenas." + arenaname + ".registered");
    }

    public static void setTeamRegistered(String arenaname, String teamname, boolean register) {
        getArenas().set("arenas." + arenaname + ".team." + teamname + ".registered", register);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static boolean isTeamRegistered(String arenaname, String teamname) {
        return getArenas().getBoolean("arenas." + arenaname + ".team." + teamname + ".registered");
    }

    public static void setEgg(String arenaname, String teamname, Location egglocation) {
        getArenas().set("arenas." + arenaname + ".team." + teamname + ".egg", egglocation);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setArenaPos1(String arenaname, Location pos1) {
        getArenas().set("arenas." + arenaname + ".pos1", pos1);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setArenaPos2(String arenaname, Location pos2) {
        getArenas().set("arenas." + arenaname + ".pos2", pos2);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setArenaWorld(String arenaname, String world) {
        getArenas().set("arenas." + arenaname + ".world", world);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void addArena(String arenaname) {
        getArenas().createSection("arenas." + arenaname);
        getArenas().set("arenas." + arenaname + ".size", 1);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void deleteArena(String arenaname) {
        getArenas().set("arenas." + arenaname, null);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static boolean isArenaReady(String arenaname) {
        // TODO Check for generators
        FileConfiguration arenas = getArenas();

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

        return arenas.contains("arenas." + arenaname + ".team." + teamname + ".egg") && arenas.contains("arenas." + arenaname + ".team." + teamname + ".shop") && arenas.contains("arenas." + arenaname + ".team." + teamname + ".spawn");
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
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setSpawn(String arenaname, String teamname, Location location) {
        FileConfiguration arenas = ArenaManager.getArenas();

        arenas.set("arenas." + arenaname + ".team." + teamname + ".spawn", location);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }
}
