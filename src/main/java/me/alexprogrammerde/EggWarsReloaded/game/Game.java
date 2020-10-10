package me.alexprogrammerde.EggWarsReloaded.game;

import com.google.common.base.Preconditions;
import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Game {
    private final List<Player> players = new ArrayList<>();
    public GameState state = GameState.NONE;
    public final String arenaName;
    protected final List<Integer> taskIds = new ArrayList<>();
    protected final HashMap<Player, String> playerTeam = new HashMap<>();
    private int startingTime;
    private final TeleportSpawn teleportSpawn;

    public Game(String arenaName) {
        this.arenaName = arenaName;
        state = GameState.UNREGISTERED;
        teleportSpawn = new TeleportSpawn(arenaName);

        EggWarsReloaded.getEggWarsMain().getLogger().info("Starting game for arena " + arenaName);

        taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.getEggWarsMain(), () -> {
            if (state == GameState.LOBBY) {
                for (Player player : players) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("The game will start shortly!").create());
                }
            }
        }, 0, 20));

        new GeneratorManager(this);
    }

    public RejectType addPlayer(Player player) {
        if (players.contains(player)) {
            return RejectType.ALREADYIN;
        }

        if (state != GameState.LOBBY) {
            return RejectType.ALREADYPLAYING;
        }

        player.getInventory().clear();

        player.teleport(Objects.requireNonNull(ArenaManager.getArenas().getLocation("arenas." + arenaName + ".mainlobby")));

        player.sendTitle("You joined " + arenaName + "!", "Please wait till the game starts!", 5, 10, 5);

        player.sendMessage("You joined " + arenaName + "!");

        players.add(player);

        return RejectType.NONE;
    }

    public RejectType removePlayer(Player player) {
        if (!players.contains(player)) {
            return RejectType.NOTIN;
        }

        if (state != GameState.LOBBY) {
            return RejectType.ALREADYPLAYING;
        }

        players.remove(player);

        return RejectType.NONE;
    }

    private void registerGame() {
        GameControl.addGame(this);

        state = GameState.REGISTERED;
    }

    private void unregisterGame() {
        GameControl.removeGame(this);

        state = GameState.UNREGISTERED;
    }

    private void startGame() {

    }

    private void rewardPlayer(Player player, RewardType reward) {

    }

    private void endGame() {
        for (Player player : players) {
            player.getInventory().clear();

            rewardPlayer(player, RewardType.GAME);

            player.teleport(Objects.requireNonNull(ArenaManager.getArenas().getLocation("arenas." + arenaName + ".mainlobby")));
        }

        for (int i : taskIds) {
            Bukkit.getScheduler().cancelTask(i);
        }

        resetArena();
    }

    private void prepareArena() {
        World arena = Bukkit.getWorld(ArenaManager.getArenas().getString("arenas." + arenaName + ".world"));

        // Save world to prevent losing progress
        arena.save();

        // DON'T save actions the players do in the game
        arena.setAutoSave(false);

        for (String key : Objects.requireNonNull(ArenaManager.getArenas().getConfigurationSection("arenas." + arenaName + ".team")).getKeys(false)) {
            for (String spawn : ArenaManager.getArenas().getStringList("arenas." + arenaName + ".team." + key + ".spawn")) {
                Location loc = UtilCore.convertLocation(spawn);
                World world = loc.getWorld();
                double x = loc.getX();
                double y = loc.getY();
                double z = loc.getZ();

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

    }

    private void resetArena() {
        World arena = Bukkit.getWorld(ArenaManager.getArenas().getString("arenas." + arenaName + ".world"));

        // Unload world to remove actions done by players and not saving them
        Bukkit.unloadWorld(arena, false);

        // Reload world to make use of it and reenable autosaving
        Bukkit.createWorld(new WorldCreator(Objects.requireNonNull(ArenaManager.getArenas().getString("arenas." + arenaName + ".world")))).setAutoSave(true);
    }
}
