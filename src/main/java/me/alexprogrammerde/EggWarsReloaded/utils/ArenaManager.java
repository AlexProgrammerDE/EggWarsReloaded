package me.alexprogrammerde.EggWarsReloaded.utils;

import me.alexprogrammerde.EggWarsReloaded.EggWarsMain;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;

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

    public static void setArenaRegistered(String arenaname, boolean register) {
        EggWarsMain.getEggWarsMain().getArenas().set("arenas." + arenaname + ".registered", register);

        try {
            EggWarsMain.getEggWarsMain().getArenas().save(EggWarsMain.getEggWarsMain().getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EggWarsMain.getEggWarsMain().reloadArenas();
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

    public static void addArena(String arenaname) {
        EggWarsMain.getEggWarsMain().getArenas().createSection("arenas." + arenaname);

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
}
