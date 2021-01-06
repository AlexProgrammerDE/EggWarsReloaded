package me.alexprogrammerde.EggWarsReloaded.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigManager {
    FileConfiguration config;

    public ConfigManager(JavaPlugin plugin, String filename) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        File file = new File(plugin.getDataFolder(), filename);

        if (!file.exists()) {
            try (InputStream in = plugin.getResource(filename)) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);

        try {
            this.config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
