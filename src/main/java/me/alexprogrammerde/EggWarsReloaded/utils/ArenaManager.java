package me.alexprogrammerde.EggWarsReloaded.utils;

import me.alexprogrammerde.EggWarsReloaded.EggWarsMain;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.List;

public class ArenaManager {

    public static FileConfiguration getArenas() {
        return EggWarsMain.getEggWarsMain().getArenas();
    }

    public static void setMainLobby(String arenaname, Location location) {
        EggWarsMain.getEggWarsMain().getArenas().set("arenas." + arenaname + ".mainlobby", location);

        try {
            EggWarsMain.getEggWarsMain().getArenas().save(EggWarsMain.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsMain.getEggWarsMain().reloadArenas();
    }

    public static void setWaitingLobby(String arenaname, Location location) {
        EggWarsMain.getEggWarsMain().getArenas().set("arenas." + arenaname + ".waitinglobby", location);

        try {
            EggWarsMain.getEggWarsMain().getArenas().save(EggWarsMain.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsMain.getEggWarsMain().reloadArenas();
    }

    public static void setSpectator(String arenaname, Location location) {
        EggWarsMain.getEggWarsMain().getArenas().set("arenas." + arenaname + ".spectator", location);

        try {
            EggWarsMain.getEggWarsMain().getArenas().save(EggWarsMain.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsMain.getEggWarsMain().reloadArenas();
    }

    public static void setArenaRegistered(String arenaname, boolean register, List<String> teams) {
        EggWarsMain.getEggWarsMain().getArenas().set("arenas." + arenaname + ".registered", register);
        EggWarsMain.getEggWarsMain().getArenas().set("arenas." + arenaname + ".registeredteams", teams);

        try {
            EggWarsMain.getEggWarsMain().getArenas().save(EggWarsMain.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsMain.getEggWarsMain().reloadArenas();
    }

    public static void setTeamSize(String arenaname, Integer size) {
        EggWarsMain.getEggWarsMain().getArenas().set("arenas." + arenaname + ".size", size);

        try {
            EggWarsMain.getEggWarsMain().getArenas().save(EggWarsMain.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsMain.getEggWarsMain().reloadArenas();
    }

    public static int getTeamSize(String arenaname) {
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

        return arenas.getInt("arenas." + arenaname + ".size");
    }

    public static boolean isArenaRegistered(String arenaname) {
        return EggWarsMain.getEggWarsMain().getArenas().getBoolean("arenas." + arenaname + ".registered");
    }

    public static void setTeamRegistered(String arenaname, String teamname, boolean register) {
        EggWarsMain.getEggWarsMain().getArenas().set("arenas." + arenaname + ".team." + teamname + ".registered", register);

        try {
            EggWarsMain.getEggWarsMain().getArenas().save(EggWarsMain.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsMain.getEggWarsMain().reloadArenas();
    }

    public static boolean isTeamRegistered(String arenaname, String teamname) {
        return EggWarsMain.getEggWarsMain().getArenas().getBoolean("arenas." + arenaname + ".team." + teamname + ".registered");
    }

    public static void setEgg(String arenaname, String teamname, Location egglocation) {
        EggWarsMain.getEggWarsMain().getArenas().set("arenas." + arenaname + ".team." + teamname + ".egg", egglocation);

        try {
            EggWarsMain.getEggWarsMain().getArenas().save(EggWarsMain.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsMain.getEggWarsMain().reloadArenas();
    }

    public static void addArena(String arenaname) {
        EggWarsMain.getEggWarsMain().getArenas().createSection("arenas." + arenaname);
        EggWarsMain.getEggWarsMain().getArenas().set("arenas." + arenaname + ".size", 1);

        try {
            EggWarsMain.getEggWarsMain().getArenas().save(EggWarsMain.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsMain.getEggWarsMain().reloadArenas();
    }

    public static void deleteArena(String arenaname) {
        EggWarsMain.getEggWarsMain().getArenas().set("arenas." + arenaname, null);

        try {
            EggWarsMain.getEggWarsMain().getArenas().save(EggWarsMain.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsMain.getEggWarsMain().reloadArenas();
    }

    public static boolean isArenaReady(String arenaname) {
        // TODO Check for generators
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

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
            EggWarsMain.getEggWarsMain().getArenas().save(EggWarsMain.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsMain.getEggWarsMain().reloadArenas();
    }

    public static void setSpawn(String arenaname, String teamname, Location location) {
        FileConfiguration arenas = ArenaManager.getArenas();

        arenas.set("arenas." + arenaname + ".team." + teamname + ".spawn", location);

        try {
            EggWarsMain.getEggWarsMain().getArenas().save(EggWarsMain.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsMain.getEggWarsMain().reloadArenas();
    }
}
