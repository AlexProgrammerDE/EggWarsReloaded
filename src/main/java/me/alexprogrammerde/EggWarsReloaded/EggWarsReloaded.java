package me.alexprogrammerde.EggWarsReloaded;

import me.alexprogrammerde.EggWarsReloaded.admin.ArenaRepairer;
import me.alexprogrammerde.EggWarsReloaded.commands.EggCommand;
import me.alexprogrammerde.EggWarsReloaded.game.Game;
import me.alexprogrammerde.EggWarsReloaded.game.listeners.*;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.ConfigManager;
import me.alexprogrammerde.EggWarsReloaded.utils.EggwarsExpansion;
import me.alexprogrammerde.EggWarsReloaded.utils.world.FileWorldManager;
import me.alexprogrammerde.EggWarsReloaded.utils.world.WorldManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
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
    private Economy econ = null;

    public void onEnable() {
        plugin = this;
        Logger log = getLogger();

        log.info(ChatColor.LIGHT_PURPLE + "Loading config");
        this.config = new ConfigManager(this, "config.yml").getConfig();
        this.language = new ConfigManager(this, "language.yml").getConfig();
        this.arenas = new ConfigManager(this, "arenas.yml").getConfig();
        this.items = new ConfigManager(this, "items.yml").getConfig();

        log.info(ChatColor.LIGHT_PURPLE + "Looking for hooks");
        if (setupEconomy()) {
            log.info(ChatColor.LIGHT_PURPLE + "Hooked into Vault!");
        } else {
            log.info(ChatColor.LIGHT_PURPLE + "Vault not found! Install vault to enable ingame rewards!");
        }

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            log.info(ChatColor.LIGHT_PURPLE + "Hooked into PlaceholderAPI!");
            new EggwarsExpansion(this).register();
        }

        log.info(ChatColor.LIGHT_PURPLE + "Registering command");
        getServer().getPluginCommand("eggwarsreloaded").setExecutor(new EggCommand());
        getServer().getPluginCommand("eggwarsreloaded").setTabCompleter(new EggCommand());

        log.info(ChatColor.LIGHT_PURPLE + "Registering listeners");
        getServer().getPluginManager().registerEvents(new ArenaRepairer(), this);
        getServer().getPluginManager().registerEvents(new GameListener(), this);
        getServer().getPluginManager().registerEvents(new LobbyListener(), this);
        getServer().getPluginManager().registerEvents(new EggListener(), this);
        getServer().getPluginManager().registerEvents(new ShopListener(), this);
        getServer().getPluginManager().registerEvents(new ButtonListener(), this);

        log.info(ChatColor.LIGHT_PURPLE + "Loading arenas");
        ConfigurationSection section = ArenaManager.getArenas();

        if (section == null) {
            log.info(ChatColor.LIGHT_PURPLE + "No arenas found!");
        } else {
            Set<String> arenas = section.getKeys(false);

            if (arenas.size() == 0) {
                log.info(ChatColor.LIGHT_PURPLE + "No arenas found!");
            } else {
                log.info(ChatColor.LIGHT_PURPLE + "Preparing arenas");
                for (String arenaName : arenas) {
                    worldManager.loadWorld(ArenaManager.getArenaWorld(arenaName), World.Environment.NORMAL);

                    if (ArenaManager.isArenaRegistered(arenaName)) {
                        new Game(arenaName, this);
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

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            return false;
        }

        econ = rsp.getProvider();
        return true;
    }

    public Economy getEconomy() {
        return econ;
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

    public void reloadArenas() {
        this.arenas = new ConfigManager(this, "arenas.yml").getConfig();
    }

    public void reloadConfig() {
        this.config = new ConfigManager(this, "config.yml").getConfig();
        this.language = new ConfigManager(this, "language.yml").getConfig();
        this.arenas = new ConfigManager(this, "arenas.yml").getConfig();
        this.items = new ConfigManager(this, "items.yml").getConfig();
    }

    public static EggWarsReloaded get() {
        return plugin;
    }
}
