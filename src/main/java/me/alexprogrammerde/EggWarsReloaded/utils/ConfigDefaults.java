package me.alexprogrammerde.EggWarsReloaded.utils;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigDefaults {
    public static FileConfiguration getConfiguration(String name, FileConfiguration config) {
        FileConfiguration configuration = config;

        if (name.equals("config")) {
            configuration.addDefault("havenofun", true);
            configuration.addDefault("havefun", true);
        }

        if (name.equals("language")) {
            configuration.addDefault("usage", "Usage: ");
            configuration.addDefault("adminusage", "Usage: ");
        }

        if (name.equals("arenas")) {
            configuration.addDefault("arenas", null);
        }

        return configuration;
    }
}
