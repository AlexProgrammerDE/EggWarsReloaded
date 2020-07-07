package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsMain;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Game {
    String arenaname;
    int[] taskID = new int[5];
    List<Player> playerlist = new ArrayList<>();
    public boolean isPlaying = false;
    public boolean isLobby;
    public static HashMap<Player, Game> playergame = new HashMap<>();
    BossBar lobbybar = Bukkit.createBossBar("The game starts soon!", BarColor.RED, BarStyle.SOLID);
    int time = 0;
    TeleportSpawn teleportspawn;

    public Game(String arenaname) {
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();
        this.arenaname = arenaname;
        isLobby = true;
        teleportspawn = new TeleportSpawn(arenaname);

        for (String team : arenas.getConfigurationSection("arenas." + arenaname + ".team").getKeys(false)) {
            teleportspawn.addTeam(team);
        }

        prepareArena();

        new GeneratorManager(this);

        this.taskID[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsMain.getEggWarsMain(), new Runnable() {
            @Override
            public void run() {
                FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

                for (Player player : playerlist) {
                    if (!player.isOnline()) {
                        removePlayer(player);
                    }

                    if (playerlist.contains(player) && isLobby) {
                        UtilCore.sendActionBar(player, "The game is starting soon!");
                    }

                    if (playerlist.size() >= 2) {
                        player.setLevel(time);
                        time--;
                    } else {
                        player.setLevel(0);
                        time = 30;
                    }

                    if (time == 1) {
                        start();
                    }
                }
            }
        }, 20, 20);

        this.taskID[4] = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsMain.getEggWarsMain(), new Runnable() {
            @Override
            public void run() {
                FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

                if (isPlaying && playerlist.size() == 1) {
                    // Only one person is playing. So the one won
                    UtilCore.sendTitle(playerlist.get(0), "You won!");
                    removePlayer(playerlist.get(0));
                    restart();
                }
            }
        }, 20, 20);
    }

    public void addPlayer(Player player) {
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

        if (!playerlist.contains(player)) {
            playerlist.add(player);
            playergame.put(player, this);
            player.setExp(0);
            player.setGameMode(GameMode.SURVIVAL);
            player.setLevel(0);
            lobbybar.addPlayer(player);
            player.teleport(arenas.getLocation("arenas." + arenaname + ".waitinglobby"));
            PlayerInventory playerinv = player.getInventory();

            playerinv.clear();

            // TODO Add forcestart item permission
            if (player.hasPermission("eggwarsreloaded.admin")) {
                playerinv.setItem(1, new ItemStack(Material.DIAMOND));
            }
        }
    }

    public void removePlayer(Player player) {
        if (playerlist.contains(player)) {
            playerlist.remove(player);
            lobbybar.removePlayer(player);
            playergame.remove(player);

            player.getInventory().clear();

            player.teleport(Objects.requireNonNull(EggWarsMain.getEggWarsMain().getArenas().getLocation("arenas." + arenaname + ".mainlobby")));
        }
    }

    public void start() {
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

        isLobby = false;
        isPlaying = true;

        EggWarsMain.getEggWarsMain().getLogger().info("Starting game.");

        for (Player player : playerlist) {
            teleportspawn.teleportPlayer(player);
        }
    }

    public void stop() {
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

        for (int task : taskID) {
            Bukkit.getScheduler().cancelTask(task);
        }

        for (Player player : playerlist) {
            removePlayer(player);
        }
    }

    public void restart() {
        stop();

        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

        Bukkit.unloadWorld(Objects.requireNonNull(arenas.getString("arenas." + arenaname + ".world")), false);

        Bukkit.createWorld(new WorldCreator(Objects.requireNonNull(arenas.getString("arenas." + arenaname + ".world"))));

        GameRegisterer.addGame(arenaname);
    }

    public void prepareArena() {
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

        World arena = Bukkit.getWorld(Objects.requireNonNull(arenas.getString("arenas." + arenaname + ".world")));

        arena.save();
        arena.setAutoSave(false);

        for (String key : Objects.requireNonNull(arenas.getConfigurationSection("arenas." + arenaname + ".team")).getKeys(false)) {
            for (String spawn : arenas.getStringList("arenas." + arenaname + ".team." + key + ".spawn")) {
                String[] split = spawn.split(" ");
                Location loc = new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
                Block block =  loc.getBlock();

                block.setType(Material.GLASS);
            }
        }
    }
}