package me.alexprogrammerde.EggWarsReloaded.utils;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
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

    public static Location getMainLobby(String arenaName) {
        return UtilCore.convertLocation(getArenas().getString("arenas." + arenaName + ".mainlobby"));
    }

    public static Location getWaitingLobby(String arenaName) {
        return UtilCore.convertLocation(getArenas().getString("arenas." + arenaName + ".waitinglobby"));
    }

    public static Location getSpectator(String arenaName) {
        return UtilCore.convertLocation(getArenas().getString("arenas." + arenaName + ".spectator"));
    }

    public static void setArenaRegistered(String arenaName, boolean register, List<String> teams) {
        getArenas().set("arenas." + arenaName + ".registered", register);
        getArenas().set("arenas." + arenaName + ".registeredteams", teams);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static void setTeamSize(String arenaName, Integer size) {
        getArenas().set("arenas." + arenaName + ".size", size);

        try {
            getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsReloaded.getEggWarsMain().reloadArenas();
    }

    public static int getTeamSize(String arenaName) {
        FileConfiguration arenas = getArenas();

        return arenas.getInt("arenas." + arenaName + ".size");
    }

    public static boolean isArenaRegistered(String arenaName) {
        return getArenas().getBoolean("arenas." + arenaName + ".registered");
    }

    public static void setTeamRegistered(String arenaName, String teamName, boolean register) {
        getArenas().set("arenas." + arenaName + ".team." + teamName + ".registered", register);

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

    public static void addArena(String arenaName) {
        getArenas().createSection("arenas." + arenaName);
        getArenas().set("arenas." + arenaName + ".size", 1);

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

    public static void setEgg(String arenaName, String teamName, Location eggLocation) {
        getArenas().set("arenas." + arenaName + ".team." + teamName + ".egg", UtilCore.convertString(eggLocation));

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
            arenas.set("arenas." + arenaName + ".team." + teamName + ".shop.location", UtilCore.convertString(location));
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

        arenas.set("arenas." + arenaName + ".team." + teamName + ".spawn", UtilCore.convertString(location));

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
