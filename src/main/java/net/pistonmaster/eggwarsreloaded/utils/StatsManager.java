package net.pistonmaster.eggwarsreloaded.utils;

import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Arrays;

public class StatsManager {
    private static EggWarsReloaded plugin;

    public static void addPlayer(Player player) {
        FileConfiguration config = plugin.getStats();

        if (config.contains(player.getUniqueId().toString()))
            return;

        config.set(player.getUniqueId() + ".name", player.getName());
        config.set(player.getUniqueId() + ".wins", 0);
        config.set(player.getUniqueId() + ".deaths", 0);
        config.set(player.getUniqueId() + ".kills", 0);
        config.set(player.getUniqueId() + ".games", 0);
        config.set(player.getUniqueId() + ".winstreak", 0);

        save();
    }

    public static void rewardPlayer(OfflinePlayer player, Type type) {
        if (type == Type.WIN) {
            incrementStat(player, "wins");
            incrementStat(player, "winstreak");
        } else if (type == Type.DEATH) {
            incrementStat(player, "deaths");
        } else if (type == Type.KILL) {
            incrementStat(player, "kills");
        } else if (type == Type.GAME) {
            incrementStat(player, "games");
        } else if (type == Type.LOSE) {
            plugin.getStats().set(player.getUniqueId() + ".winstreak", 0);
        }

        save();
    }

    public static int getStat(OfflinePlayer player, String stat) {
        return plugin.getStats().getInt(player.getUniqueId() + "." + stat);
    }

    private static void incrementStat(OfflinePlayer player, String stat) {
        FileConfiguration config = plugin.getStats();

        config.set(player.getUniqueId() + "." + stat, config.getInt(player.getUniqueId() + "." + stat) + 1);
    }

    public static boolean isValidStat(String stat) {
        String[] stats = {"wins", "winstreak", "deaths", "kills", "games"};

        return Arrays.stream(stats).anyMatch(stat::equalsIgnoreCase);
    }

    private static void save() {
        try {
            plugin.getStats().save(plugin.getStatsFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        plugin.reloadStats();
    }

    public void setInstance(EggWarsReloaded plugin) {
        StatsManager.plugin = plugin;
    }

    public enum Type {
        WIN,
        DEATH,
        KILL,
        GAME,
        LOSE
    }
}
