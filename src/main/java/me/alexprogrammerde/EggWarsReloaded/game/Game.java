package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    protected final List<Player> players = new ArrayList<>();
    public GameState state = GameState.NONE;
    FileConfiguration arenas = ArenaManager.getArenas();
    public final String arenaName;
    protected final List<Integer> taskIds = new ArrayList<>();
    private int startingTime;
    private final MatchMaker matchmaker;
    final List<String> usedTeams = new ArrayList<>();
    int clockTask;
    public final int maxPlayers;
    public final int maxTeamPlayers;

    public Game(String arenaName) {
        this.arenaName = arenaName;
        state = GameState.UNREGISTERED;

        for (String team : arenas.getConfigurationSection("arenas." + arenaName + ".team").getKeys(false)) {
            if (ArenaManager.isTeamRegistered(arenaName, team)) {
                usedTeams.add(team);
            }
        }

        prepareArena();
        maxTeamPlayers = arenas.getInt("arenas." + arenaName + ".size");
        maxPlayers = usedTeams.size() * maxTeamPlayers;
        matchmaker = new MatchMaker(arenaName, this);
        matchmaker.readSpawns();

        EggWarsReloaded.getEggWarsMain().getLogger().info("Starting game for arena " + arenaName);

        registerGame();

        state = GameState.LOBBY;

        taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.getEggWarsMain(), () -> {
            if (state == GameState.LOBBY || state == GameState.STARTING1) {
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

        // Remove everything the player had
        player.getInventory().clear();
        player.setExp(0);
        player.setGameMode(GameMode.ADVENTURE);
        player.setLevel(0);
        player.setFoodLevel(20);
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());

        player.teleport(UtilCore.convertLocation(arenas.getString("arenas." + arenaName + ".waitinglobby")));

        player.sendTitle("You joined " + arenaName + "!", "Please wait till the game starts!", 5, 10, 5);

        player.sendMessage("You joined " + arenaName + "!");

        players.add(player);

        matchmaker.findTeamForPlayer(player);

        player.sendMessage("Your in the team: " + matchmaker.getTeamOfPlayer(player));

        // TODO: Make player requirement optional and don't run it again if someone joins
        if (players.size() >= 2) {
            startGame1();
        }

        return RejectType.NONE;
    }

    public RejectType kickPlayer(Player player) {
        if (!players.contains(player)) {
            return RejectType.NOTIN;
        }

        endPlayer(player);

        return RejectType.NONE;
    }

    private void endPlayer(Player player) {
        player.getInventory().clear();

        player.teleport(UtilCore.convertLocation(ArenaManager.getArenas().getString("arenas." + arenaName + ".mainlobby")));

        player.setGameMode(GameMode.SURVIVAL);
    }

    private void registerGame() {
        GameControl.addGame(this);

        state = GameState.REGISTERED;
    }

    private void unregisterGame() {
        GameControl.removeGame(this);

        state = GameState.UNREGISTERED;
    }

    private void startGame1() {
        state = GameState.STARTING1;

        startingTime = 20;
        for (Player player : players) {
            player.setLevel(startingTime);
        }

        clockTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.getEggWarsMain(), () -> {
            startingTime--;

            for (Player player : players) {
                player.setLevel(startingTime);
            }

            if (startingTime == 10) {
                Bukkit.getScheduler().runTask(EggWarsReloaded.getEggWarsMain(), this::startGame2);
            }
        }, 20, 20);
    }

    private void startGame2() {
        state = GameState.STARTING2;

        Bukkit.getScheduler().cancelTask(clockTask);

        matchmaker.teleportPlayers();

        startingTime = 10;
        for (Player player : players) {
            player.setLevel(startingTime);
        }

        clockTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.getEggWarsMain(), () -> {
            startingTime--;

            for (Player player : players) {
                player.setLevel(startingTime);
            }

            if (startingTime == 0) {
                Bukkit.getScheduler().runTask(EggWarsReloaded.getEggWarsMain(), this::runGame);
            }
        }, 20, 20);
    }

    private void runGame() {
        Bukkit.getScheduler().cancelTask(clockTask);

        state = GameState.RUNNING;

        destroyCages();
    }

    private void rewardPlayer(Player player, RewardType reward) {

    }

    public void endGame() {
        state = GameState.ENDING;

        for (Player player : players) {
            player.sendMessage("The game ended!");
            rewardPlayer(player, RewardType.GAME);

            endPlayer(player);
        }

        players.clear();

        for (int i : taskIds) {
            Bukkit.getScheduler().cancelTask(i);
        }

        resetArena();

        state = GameState.ENDED;

        unregisterGame();
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

    private void resetArena() {
        World arena = Bukkit.getWorld(ArenaManager.getArenas().getString("arenas." + arenaName + ".world"));

        // Unload world to remove actions done by players and not saving them
        Bukkit.unloadWorld(arena, false);

        // Reload world to make use of it and reenable autosaving
        Bukkit.createWorld(new WorldCreator(Objects.requireNonNull(ArenaManager.getArenas().getString("arenas." + arenaName + ".world")))).setAutoSave(true);
    }

    private void destroyCages() {
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
}
