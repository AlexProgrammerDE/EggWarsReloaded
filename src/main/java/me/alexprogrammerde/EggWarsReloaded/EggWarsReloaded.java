package me.alexprogrammerde.EggWarsReloaded;

import me.alexprogrammerde.EggWarsReloaded.admin.ArenaRepairer;
import me.alexprogrammerde.EggWarsReloaded.game.EggListener;
import me.alexprogrammerde.EggWarsReloaded.game.Game;
import me.alexprogrammerde.EggWarsReloaded.game.GameListener;
import me.alexprogrammerde.EggWarsReloaded.game.listeners.LobbyCore;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

public class EggWarsReloaded extends JavaPlugin {
    FileConfiguration config;
    FileConfiguration language;
    FileConfiguration arenas;
    FileConfiguration items;
    static EggWarsReloaded plugin;

    public void onEnable() {
        plugin = this;

        Logger logger = this.getLogger();

        logger.info("§aLoading config");
        this.config = new ConfigManager(this, "config.yml", "config").getConfig();
        this.language = new ConfigManager(this, "language.yml", "language").getConfig();
        this.arenas = new ConfigManager(this, "arenas.yml", "arenas").getConfig();
        this.items = new ConfigManager(this, "items.yml", "items").getConfig();

        logger.info("§aRegistering command");
        getServer().getPluginCommand("eggwarsreloaded").setExecutor(new EggCommand());
        getServer().getPluginCommand("eggwarsreloaded").setTabCompleter(new EggCommand());

        logger.info("§aRegistering listeners");
        getServer().getPluginManager().registerEvents(new ArenaRepairer(), this);
        getServer().getPluginManager().registerEvents(new GameListener(), this);
        getServer().getPluginManager().registerEvents(new LobbyCore(), this);
        getServer().getPluginManager().registerEvents(new EggListener(), this);

        logger.info("§aLoading arenas");
        ConfigurationSection section = ArenaManager.getArenas().getConfigurationSection("arenas");
        if (section == null) {
            logger.info("§aNo arenas found!");
        } else {
            Set<String> arenas = section.getKeys(false);

            if (arenas.size() == 0) {
                logger.info("§aNo arenas found!");
            } else {
                logger.info("§aPreparing arenas");
                for (String arenaName : arenas) {
                    if (ArenaManager.isArenaRegistered(arenaName)) {
                        new Game(arenaName);
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        try {
            getArenas().save(getArenasFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getLanguage() {
        return language;
    }

    public FileConfiguration getArenas() {
        return arenas;
    }

    public FileConfiguration getItems() {
        return items;
    }

    public File getArenasFile() {
        return new File(plugin.getDataFolder(), "arenas.yml");
    }

    public void reloadArenas() { this.arenas = new ConfigManager(this, "arenas.yml", "arenas").getConfig(); }

    public void reloadConfig() {
        this.config = new ConfigManager(this, "config.yml", "config").getConfig();
        this.language = new ConfigManager(this, "language.yml", "language").getConfig();
        this.arenas = new ConfigManager(this, "arenas.yml", "arenas").getConfig();
        this.items = new ConfigManager(this, "items.yml", "items").getConfig();
    }

    public static EggWarsReloaded getEggWarsMain() {
        return plugin;
    }
}
