package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsMain;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Game {
    String arenaname;
    int[] taskID = new int[6];
    List<Player> playerlist = new ArrayList<>();
    public boolean isPlaying = false;
    public boolean isPreGame = false;
    public boolean isLobby;
    public static HashMap<Player, Game> playergame = new HashMap<>();
    BossBar lobbybar = Bukkit.createBossBar("The game starts soon!", BarColor.RED, BarStyle.SOLID);
    int lobbytime = 0;
    int pregametime = 0;
    public TeleportSpawn teleportspawn;

    public Game(String arenaname) {
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();
        this.arenaname = arenaname;
        isLobby = true;
        teleportspawn = new TeleportSpawn(arenaname);

        for (String team : Objects.requireNonNull(arenas.getConfigurationSection("arenas." + arenaname + ".team")).getKeys(false)) {
            teleportspawn.addTeam(team);
        }

        prepareArena();

        new GeneratorManager(this);

        this.taskID[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsMain.getEggWarsMain(), () -> {
            for (Player player : playerlist) {
                if (!player.isOnline()) {
                    removePlayer(player);
                }

                if (playerlist.contains(player) && isLobby) {
                    UtilCore.sendActionBar(player, "The game is starting soon!");
                }

                if (playerlist.size() >= 2) {
                    player.setLevel(lobbytime);
                    lobbytime--;
                } else {
                    player.setLevel(0);
                    lobbytime = 30;
                }

                if (lobbytime == 1) {
                    start();
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
            player.teleport(Objects.requireNonNull(arenas.getLocation("arenas." + arenaname + ".waitinglobby")));
            PlayerInventory playerinv = player.getInventory();
            player.setFoodLevel(20);
            player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
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
        isPreGame = true;
        pregametime = 5;

        EggWarsMain.getEggWarsMain().getLogger().info("Starting game.");

        for (Player player : playerlist) {
            player.getInventory().clear();
            lobbybar.removePlayer(player);
            player.setLevel(0);

            // TODO Implement team item
            teleportspawn.teleportPlayer(player, "white");
        }

        this.taskID[5] = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsMain.getEggWarsMain(), () -> {
            if (pregametime == 0) {
                isPlaying = true;
                Bukkit.getScheduler().cancelTask(taskID[5]);

                for (String key : Objects.requireNonNull(arenas.getConfigurationSection("arenas." + arenaname + ".team")).getKeys(false)) {
                    for (String spawn : arenas.getStringList("arenas." + arenaname + ".team." + key + ".spawn")) {
                        String[] split = spawn.split(" ");
                        World world = Bukkit.getWorld(split[0]);
                        double x = Double.parseDouble(split[1]);
                        double y = Double.parseDouble(split[2]);
                        double z = Double.parseDouble(split[3]);

                        Block ground = new Location(world, x, y - 1, z).getBlock();

                        Block first1 = new Location(world, x - 1, y, z).getBlock();
                        Block first2 = new Location(world, x + 1, y, z).getBlock();
                        Block first3 = new Location(world, x, y, z + 1).getBlock();
                        Block first4 = new Location(world, x, y, z - 1).getBlock();

                        Block second1 = new Location(world, x - 1, y + 1, z).getBlock();
                        Block second2 = new Location(world, x + 1, y + 1, z).getBlock();
                        Block second3 = new Location(world, x, y + 1, z + 1).getBlock();
                        Block second4 = new Location(world, x, y + 1, z - 1).getBlock();

                        Block third1 = new Location(world, x - 1, y + 2, z).getBlock();
                        Block third2 = new Location(world, x + 1, y + 2, z).getBlock();
                        Block third3 = new Location(world, x, y + 2, z + 1).getBlock();
                        Block third4 = new Location(world, x, y + 2, z - 1).getBlock();

                        Block top = new Location(world, x, y + 3, z).getBlock();

                        ground.setType(Material.AIR);

                        first1.setType(Material.AIR);
                        first2.setType(Material.AIR);
                        first3.setType(Material.AIR);
                        first4.setType(Material.AIR);

                        second1.setType(Material.AIR);
                        second2.setType(Material.AIR);
                        second3.setType(Material.AIR);
                        second4.setType(Material.AIR);

                        third1.setType(Material.AIR);
                        third2.setType(Material.AIR);
                        third3.setType(Material.AIR);
                        third4.setType(Material.AIR);

                        top.setType(Material.AIR);
                    }
                }

                taskID[4] = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsMain.getEggWarsMain(), () -> {
                    if (isPlaying && playerlist.size() == 1) {
                        // Only one person is playing. So the one won
                        UtilCore.sendTitle(playerlist.get(0), "You won!");
                        removePlayer(playerlist.get(0));
                        restart();
                    }
                }, 100, 20);
            }

            if (pregametime > 0) {
                for (Player player : playerlist) {
                    UtilCore.sendTitle(player, String.valueOf(pregametime));
                }

                pregametime--;
            }
        }, 20, 20);
    }

    public void stop() {
        for (int task : taskID) {
            Bukkit.getScheduler().cancelTask(task);
        }

        for (Player player : playerlist) {
            removePlayer(player);
        }
    }

    public void restart() {
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();
        World arena = Bukkit.getWorld(Objects.requireNonNull(arenas.getString("arenas." + arenaname + ".world")));

        stop();

        Bukkit.unloadWorld(Objects.requireNonNull(arenas.getString("arenas." + arenaname + ".world")), false);

        Bukkit.createWorld(new WorldCreator(Objects.requireNonNull(arenas.getString("arenas." + arenaname + ".world"))));

        Objects.requireNonNull(arena).setAutoSave(true);

        GameRegisterer.addGame(arenaname);
    }

    public void death(Player player) {
        removePlayer(player);
    }

    public void prepareArena() {
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

        World arena = Bukkit.getWorld(Objects.requireNonNull(arenas.getString("arenas." + arenaname + ".world")));
        Objects.requireNonNull(arena).setAutoSave(false);

        for (String key : Objects.requireNonNull(arenas.getConfigurationSection("arenas." + arenaname + ".team")).getKeys(false)) {
            for (String spawn : arenas.getStringList("arenas." + arenaname + ".team." + key + ".spawn")) {
                String[] split = spawn.split(" ");

                World world = Bukkit.getWorld(split[0]);
                double x = Double.parseDouble(split[1]);
                double y = Double.parseDouble(split[2]);
                double z = Double.parseDouble(split[3]);

                Block ground = new Location(world, x, y - 1, z).getBlock();

                Block first1 = new Location(world, x - 1, y, z).getBlock();
                Block first2 = new Location(world, x + 1, y, z).getBlock();
                Block first3 = new Location(world, x, y, z + 1).getBlock();
                Block first4 = new Location(world, x, y, z - 1).getBlock();

                Block second1 = new Location(world, x - 1, y + 1, z).getBlock();
                Block second2 = new Location(world, x + 1, y + 1, z).getBlock();
                Block second3 = new Location(world, x, y + 1, z + 1).getBlock();
                Block second4 = new Location(world, x, y + 1, z - 1).getBlock();

                Block third1 = new Location(world, x - 1, y + 2, z).getBlock();
                Block third2 = new Location(world, x + 1, y + 2, z).getBlock();
                Block third3 = new Location(world, x, y + 2, z + 1).getBlock();
                Block third4 = new Location(world, x, y + 2, z - 1).getBlock();

                Block top = new Location(world, x, y + 3, z).getBlock();

                ground.setType(Material.GLASS);

                first1.setType(Material.GLASS);
                first2.setType(Material.GLASS);
                first3.setType(Material.GLASS);
                first4.setType(Material.GLASS);

                second1.setType(Material.GLASS);
                second2.setType(Material.GLASS);
                second3.setType(Material.GLASS);
                second4.setType(Material.GLASS);

                third1.setType(Material.GLASS);
                third2.setType(Material.GLASS);
                third3.setType(Material.GLASS);
                third4.setType(Material.GLASS);

                top.setType(Material.BARRIER);
            }
        }
    }
}