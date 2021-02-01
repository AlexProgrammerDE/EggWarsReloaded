package me.alexprogrammerde.eggwarsreloaded.utils;

import me.alexprogrammerde.eggwarsreloaded.EggWarsReloaded;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;

public class StatsManager {
    private static EggWarsReloaded plugin;

    public StatsManager(EggWarsReloaded plugin) {
        StatsManager.plugin = plugin;
    }

    public static void addPlayer(Player player) {
        FileConfiguration config = plugin.getStats();

        if (config.contains(player.getUniqueId().toString()))
            return;

        config.set(player.getUniqueId() + ".name", player.getName());
        config.set(player.getUniqueId() + ".wins", 0);
        config.set(player.getUniqueId() + ".deaths", 0);
        config.set(player.getUniqueId() + ".kills", 0);
        config.set(player.getUniqueId() + ".games", 0);

        save();
    }

    private static void save() {
        try {
            plugin.getStats().save(plugin.getStatsFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        plugin.reloadStats();
    }
}
