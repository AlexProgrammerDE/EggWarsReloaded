package me.alexprogrammerde.EggWarsReloaded;

import me.alexprogrammerde.EggWarsReloaded.admin.ArenaRepairer;
import me.alexprogrammerde.EggWarsReloaded.commands.EggCommand;
import me.alexprogrammerde.EggWarsReloaded.game.Game;
import me.alexprogrammerde.EggWarsReloaded.game.listeners.GameListener;
import me.alexprogrammerde.EggWarsReloaded.game.listeners.EggListener;
import me.alexprogrammerde.EggWarsReloaded.game.listeners.LobbyListener;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.ConfigManager;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import me.alexprogrammerde.EggWarsReloaded.utils.world.FileWorldManager;
import me.alexprogrammerde.EggWarsReloaded.utils.world.WorldManager;
import org.bukkit.ChatColor;
import org.bukkit.World;
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
    public WorldManager worldManager = new FileWorldManager(this);

    public void onEnable() {
        plugin = this;

        Logger logger = this.getLogger();

        logger.info(ChatColor.LIGHT_PURPLE + "Loading config");
        this.config = new ConfigManager(this, "config.yml").getConfig();
        this.language = new ConfigManager(this, "language.yml").getConfig();
        this.arenas = new ConfigManager(this, "arenas.yml").getConfig();
        this.items = new ConfigManager(this, "items.yml").getConfig();

        logger.info(ChatColor.LIGHT_PURPLE + "Registering command");
        getServer().getPluginCommand("eggwarsreloaded").setExecutor(new EggCommand());
        getServer().getPluginCommand("eggwarsreloaded").setTabCompleter(new EggCommand());

        logger.info(ChatColor.LIGHT_PURPLE + "Registering listeners");
        getServer().getPluginManager().registerEvents(new ArenaRepairer(), this);
        getServer().getPluginManager().registerEvents(new GameListener(), this);
        getServer().getPluginManager().registerEvents(new LobbyListener(), this);
        getServer().getPluginManager().registerEvents(new EggListener(), this);

        logger.info(ChatColor.LIGHT_PURPLE + "Loading arenas");
        ConfigurationSection section = ArenaManager.getArenas().getConfigurationSection("arenas");
        if (section == null) {
            logger.info(ChatColor.LIGHT_PURPLE + "No arenas found!");
        } else {
            Set<String> arenas = section.getKeys(false);

            if (arenas.size() == 0) {
                logger.info(ChatColor.LIGHT_PURPLE + "No arenas found!");
            } else {
                logger.info(ChatColor.LIGHT_PURPLE + "Preparing arenas");
                for (String arenaName : arenas) {
                    worldManager.loadWorld(ArenaManager.getArenaWorld(arenaName), World.Environment.NORMAL);

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

    public void reloadArenas() { this.arenas = new ConfigManager(this, "arenas.yml").getConfig(); }

    public void reloadConfig() {
        this.config = new ConfigManager(this, "config.yml").getConfig();
        this.language = new ConfigManager(this, "language.yml").getConfig();
        this.arenas = new ConfigManager(this, "arenas.yml").getConfig();
        this.items = new ConfigManager(this, "items.yml").getConfig();
    }

    public static EggWarsReloaded getEggWarsMain() {
        return plugin;
    }
}
