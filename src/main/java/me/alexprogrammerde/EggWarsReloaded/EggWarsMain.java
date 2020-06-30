package me.alexprogrammerde.EggWarsReloaded;

import me.alexprogrammerde.EggWarsReloaded.admin.MenuListener;
import me.alexprogrammerde.EggWarsReloaded.commands.MainCommand;
import me.alexprogrammerde.EggWarsReloaded.utils.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class EggWarsMain extends JavaPlugin {
    FileConfiguration config;
    FileConfiguration language;
    FileConfiguration arenas;
    FileConfiguration items;
    static EggWarsMain plugin;

    public void onEnable() {
        plugin = this;

        Logger logger = this.getLogger();

        logger.info("§aLoading config");
        this.config = new ConfigManager(this, "config.yml", "config").getConfig();
        this.language = new ConfigManager(this, "language.yml", "language").getConfig();
        this.arenas = new ConfigManager(this, "arenas.yml", "arenas").getConfig();
        this.items = new ConfigManager(this, "items.yml", "items").getConfig();

        logger.info("§aRegistering command");
        getServer().getPluginCommand("eggwarsreloaded").setExecutor(new MainCommand());
        getServer().getPluginCommand("eggwarsreloaded").setTabCompleter(new MainCommand());

        logger.info("§aRegistering listeners");
        getServer().getPluginManager().registerEvents(new MenuListener(), this);

        logger.info("§aLoading arenas");

        /*try {
            Set<String> arenas = ArenaManager.getArenas().getConfigurationSection("arenas").getKeys(false);

            for (String arenaname : arenas) {
                new Game(arenaname);
            }
        } catch (NullPointerException e) {
            logger.info("§aNo arenas found");
        }*/
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
    }

    public static EggWarsMain getEggWarsMain() {
        return plugin;
    }
}
