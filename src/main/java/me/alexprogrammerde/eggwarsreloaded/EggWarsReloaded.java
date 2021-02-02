package me.alexprogrammerde.eggwarsreloaded;

import lombok.Getter;
import me.alexprogrammerde.eggwarsreloaded.admin.ArenaRepairer;
import me.alexprogrammerde.eggwarsreloaded.commands.EggCommand;
import me.alexprogrammerde.eggwarsreloaded.game.Game;
import me.alexprogrammerde.eggwarsreloaded.game.listeners.*;
import me.alexprogrammerde.eggwarsreloaded.utils.*;
import me.alexprogrammerde.eggwarsreloaded.utils.world.FileWorldManager;
import me.alexprogrammerde.eggwarsreloaded.utils.world.WorldManager;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Set;
import java.util.logging.Logger;

public class EggWarsReloaded extends JavaPlugin {
    @Getter
    private final WorldManager worldManager = new FileWorldManager(this);
    FileConfiguration config;
    @Getter
    FileConfiguration language;
    @Getter
    FileConfiguration arenaConfig;
    @Getter
    FileConfiguration items;
    @Getter
    FileConfiguration signs;
    @Getter
    FileConfiguration stats;
    private Economy econ = null;

    @Getter
    private SignManager signManager;

    @Override
    public void onEnable() {
        ArenaManager.setEggwarsMain(this);
        Logger log = getLogger();
        signManager = new SignManager(this);

        log.info(ChatColor.LIGHT_PURPLE + "Loading config");
        loadConfig();

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

        new StatsManager().setInstance(this);
        new LeaderboardManager().setInstance(this);

        log.info(ChatColor.LIGHT_PURPLE + "Registering command");
        getServer().getPluginCommand("eggwarsreloaded").setExecutor(new EggCommand(this));
        getServer().getPluginCommand("eggwarsreloaded").setTabCompleter(new EggCommand(this));

        log.info(ChatColor.LIGHT_PURPLE + "Registering listeners");
        getServer().getPluginManager().registerEvents(new ArenaRepairer(this), this);
        getServer().getPluginManager().registerEvents(new GameListener(), this);
        getServer().getPluginManager().registerEvents(new LobbyListener(this), this);
        getServer().getPluginManager().registerEvents(new EggListener(), this);
        getServer().getPluginManager().registerEvents(new ShopListener(this), this);
        getServer().getPluginManager().registerEvents(new ButtonListener(), this);

        log.info(ChatColor.LIGHT_PURPLE + "Loading arenas");
        ConfigurationSection section = ArenaManager.getArenas();

        if (section == null) {
            log.info(ChatColor.LIGHT_PURPLE + "No arenas found!");
        } else {
            Set<String> arenas = section.getKeys(false);

            if (arenas.isEmpty()) {
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

        signManager.detectSigns();
        getServer().getPluginManager().registerEvents(signManager, this);

        log.info(ChatColor.LIGHT_PURPLE + "Loading metrics");
        new Metrics(this, 10135);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null)
            return false;

        econ = rsp.getProvider();
        return true;
    }

    public Economy getEconomy() {
        return econ;
    }

    @Override
    public @NotNull FileConfiguration getConfig() {
        return config;
    }

    public File getArenasFile() {
        return new File(getDataFolder(), "arenas.yml");
    }

    public File getStatsFile() {
        return new File(getDataFolder(), "stats.yml");
    }

    public void reloadArenas() {
        this.arenaConfig = new ConfigManager(this, "arenas.yml").getConfig();
    }

    public void reloadStats() {
        this.stats = new ConfigManager(this, "stats.yml").getConfig();
    }

    public void loadConfig() {
        this.config = new ConfigManager(this, "config.yml").getConfig();
        this.language = new ConfigManager(this, "language.yml").getConfig();
        this.arenaConfig = new ConfigManager(this, "arenas.yml").getConfig();
        this.items = new ConfigManager(this, "items.yml").getConfig();
        this.signs = new ConfigManager(this, "signs.yml").getConfig();
        this.stats = new ConfigManager(this, "stats.yml").getConfig();
    }
}
