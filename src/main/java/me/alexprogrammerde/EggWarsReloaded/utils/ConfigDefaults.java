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
            configuration.addDefault("command.usage.playerusage", "Usage: /eggwars [help, join, randomjoin]");
            configuration.addDefault("command.usage.adminusage", "Usage: /eggwars [help, join, randomjoin, reload, addarena, delarena, kick]");
        }

        return configuration;
    }
}
