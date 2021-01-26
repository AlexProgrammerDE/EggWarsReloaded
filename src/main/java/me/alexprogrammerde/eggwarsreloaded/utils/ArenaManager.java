package me.alexprogrammerde.eggwarsreloaded.utils;

import me.alexprogrammerde.eggwarsreloaded.EggWarsReloaded;
import me.alexprogrammerde.eggwarsreloaded.game.collection.TeamColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ArenaManager {
    private static EggWarsReloaded plugin;

    private ArenaManager() {
    }

    public static void setEggwarsMain(EggWarsReloaded plugin) {
        ArenaManager.plugin = plugin;
    }

    public static FileConfiguration getArenas() {
        return plugin.getArenaConfig();
    }

    public static void addArena(String arenaName) {
        getArenas().createSection(arenaName);
        getArenas().set(arenaName + ".size", 1);

        save();
    }

    public static String getArenaWorld(String arenaName) {
        return getArenas().getString(arenaName + ".world");
    }

    public static void setArenaWorld(String arenaName, String world) {
        getArenas().set(arenaName + ".world", world);

        save();
    }

    public static Location getMainLobby(String arenaName) {
        return UtilCore.convertLocation(getArenas().getString(arenaName + ".mainlobby"));
    }

    public static Location getWaitingLobby(String arenaName) {
        return UtilCore.convertLocation(getArenas().getString(arenaName + ".waitinglobby"));
    }

    public static Location getSpectator(String arenaName) {
        return UtilCore.convertLocation(getArenas().getString(arenaName + ".spectator"));
    }

    public static void setMainLobby(String arenaName, Location location) {
        getArenas().set(arenaName + ".mainlobby", UtilCore.convertString(location));

        save();
    }

    public static void setWaitingLobby(String arenaName, Location location) {
        getArenas().set(arenaName + ".waitinglobby", UtilCore.convertString(location));

        save();
    }

    public static void setSpectator(String arenaName, Location location) {
        getArenas().set(arenaName + ".spectator", UtilCore.convertString(location));

        save();
    }

    public static void setArenaRegistered(String arenaName, boolean register, List<TeamColor> teams) {
        getArenas().set(arenaName + ".registered", register);
        getArenas().set(arenaName + ".registeredteams", TeamColor.toStringList(teams));

        save();
    }

    public static int getTeamSize(String arenaName) {
        return getArenas().getInt(arenaName + ".size");
    }

    public static void setTeamSize(String arenaName, Integer size) {
        getArenas().set(arenaName + ".size", size);

        save();
    }

    public static void setTeamRegistered(String arenaName, TeamColor team, boolean register) {
        getArenas().set(arenaName + ".team." + team + ".registered", register);

        save();
    }

    public static void setArenaPos1(String arenaName, Location pos1) {
        getArenas().set(arenaName + ".pos1", UtilCore.convertString(pos1));

        save();
    }

    public static void setArenaPos2(String arenaName, Location pos2) {
        getArenas().set(arenaName + ".pos2", UtilCore.convertString(pos2));

        save();
    }

    public static void deleteArena(String arenaName) {
        getArenas().set(arenaName, null);

        save();
    }

    public static void setEgg(String arenaName, TeamColor team, Location eggLocation) {
        getArenas().set(arenaName + ".team." + team + ".egg", UtilCore.convertString(eggLocation));

        save();
    }

    public static void setShop(String arenaName, TeamColor team, String uuid, Location location) {
        FileConfiguration arenas = ArenaManager.getArenas();

        if (uuid == null || location == null) {
            arenas.set(arenaName + ".team." + team + ".shop", null);
        } else {
            arenas.set(arenaName + ".team." + team + ".shop.uuid", uuid);
            arenas.set(arenaName + ".team." + team + ".shop.location", UtilCore.convertString(location));
        }

        save();
    }

    public static void setFirstSpawn(String arenaName, TeamColor team, Location location) {
        getArenas().set(arenaName + ".team." + team + ".spawn", UtilCore.convertString(location));

        save();
    }

    public static void setRespawn(String arenaName, TeamColor team, Location location) {
        getArenas().set(arenaName + ".team." + team + ".respawn", UtilCore.convertString(location));

        save();
    }

    public static Location getRespawn(String arenaName, TeamColor team) {
        return UtilCore.convertLocation(getArenas().getString(arenaName + ".team." + team.toString() + ".respawn"));
    }

    public static boolean isArenaRegistered(String arenaName) {
        return getArenas().getBoolean(arenaName + ".registered");
    }

    public static boolean isTeamRegistered(String arenaName, TeamColor team) {
        return getArenas().getBoolean(arenaName + ".team." + team.toString() + ".registered");
    }

    public static boolean isArenaReady(String arenaName) {
        FileConfiguration arenas = ArenaManager.getArenas();

        if (!areEnoughTeamsRegistered(arenaName))
            return false;

        return arenas.contains(arenaName + ".world")
                && arenas.contains(arenaName + ".pos1")
                && arenas.contains(arenaName + ".pos2")
                && arenas.contains(arenaName + ".size")
                && arenas.contains(arenaName + ".mainlobby")
                && arenas.contains(arenaName + ".waitinglobby")
                && arenas.contains(arenaName + ".spectator");
    }

    private static boolean areEnoughTeamsRegistered(String arenaName) {
        FileConfiguration arenas = ArenaManager.getArenas();
        int registeredTeams = 0;

        for (String team : Objects.requireNonNull(arenas.getConfigurationSection(arenaName + ".team")).getKeys(false)) {
            if (isTeamRegistered(arenaName, TeamColor.fromString(team)))
                registeredTeams++;
        }

        return registeredTeams >= 2;
    }

    public static boolean isTeamReady(String arenaName, TeamColor team) {
        FileConfiguration arenas = ArenaManager.getArenas();

        return arenas.contains(arenaName + ".team." + team + ".egg")
                && arenas.contains(arenaName + ".team." + team + ".shop")
                && arenas.contains(arenaName + ".team." + team + ".spawn")
                && arenas.contains(arenaName + ".team." + team + ".respawn");
    }

    private static void save() {
        try {
            getArenas().save(plugin.getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        plugin.reloadArenas();
    }
}
