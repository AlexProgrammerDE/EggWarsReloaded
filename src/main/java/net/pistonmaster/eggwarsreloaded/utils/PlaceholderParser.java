package net.pistonmaster.eggwarsreloaded.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import org.bukkit.OfflinePlayer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class PlaceholderParser {
    private PlaceholderParser() {}

    public static String parse(String str, OfflinePlayer player) {
        EggWarsReloaded plugin = EggWarsReloaded.getPlugin(EggWarsReloaded.class);

        if (plugin.isPapi()) {
            return PlaceholderAPI.setPlaceholders(player, builtInPlaceholders(str, plugin));
        } else {
            return builtInPlaceholders(str, plugin);
        }
    }

    private static String builtInPlaceholders(String str, EggWarsReloaded plugin) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String currentTime = dtf.format(LocalDateTime.now());

        return ChatColor.translateAlternateColorCodes('&', str.replace("%time%", currentTime).replace("%domain%", Objects.requireNonNull(plugin.getScoreboards().getString("domain"))));
    }
}
