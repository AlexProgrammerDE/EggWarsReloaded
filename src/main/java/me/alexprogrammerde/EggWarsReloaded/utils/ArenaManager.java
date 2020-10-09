package me.alexprogrammerde.EggWarsReloaded.utils;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.List;

public class ArenaManager {
    public static FileConfiguration getArenas() {
        return EggWarsReloaded.getEggWarsMain().getArenas();
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

    public static void setEgg(String arenaname, String teamname, Location egglocation) {
        getArenas().set("arenas." + arenaname + ".team." + teamname + ".egg", egglocation);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setArenaPos1(String arenaName, Location pos1) {
        getArenas().set("arenas." + arenaName + ".pos1", UtilCore.convertString(pos1));

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setArenaPos2(String arenaName, Location pos2) {
        getArenas().set("arenas." + arenaName + ".pos2", UtilCore.convertString(pos2));

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setArenaWorld(String arenaName, String world) {
        getArenas().set("arenas." + arenaName + ".world", world);

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

    public static void deleteArena(String arenaName) {
        getArenas().set("arenas." + arenaName, null);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setShop(String arenaName, String teamName, String uuid, Location location) {
        FileConfiguration arenas = ArenaManager.getArenas();

        if (uuid == null || location == null) {
            arenas.set("arenas." + arenaName + ".team." + teamName + ".shop", null);
        } else {
            arenas.set("arenas." + arenaName + ".team." + teamName + ".shop.uuid", uuid);
            arenas.set("arenas." + arenaName + ".team." + teamName + ".shop.location", location);
        }

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setSpawn(String arenaName, String teamName, Location location) {
        FileConfiguration arenas = ArenaManager.getArenas();

        arenas.set("arenas." + arenaName + ".team." + teamName + ".spawn", location);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static boolean isTeamRegistered(String arenaName, String teamName) {
        return getArenas().getBoolean("arenas." + arenaName + ".team." + teamName + ".registered");
    }

    public static boolean isArenaReady(String arenaName) {
        // TODO: Add checks

        return true;
    }

    public static boolean isTeamReady(String arenaName, String teamName) {
        FileConfiguration arenas = ArenaManager.getArenas();

        return arenas.contains("arenas." + arenaName + ".team." + teamName + ".egg") && arenas.contains("arenas." + arenaName + ".team." + teamName + ".shop") && arenas.contains("arenas." + arenaName + ".team." + teamName + ".spawn");
    }
}
