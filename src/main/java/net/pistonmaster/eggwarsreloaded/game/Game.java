package net.pistonmaster.eggwarsreloaded.game;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.milkbowl.vault.economy.EconomyResponse;
import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import net.pistonmaster.eggwarsreloaded.game.collection.GameState;
import net.pistonmaster.eggwarsreloaded.game.collection.RejectType;
import net.pistonmaster.eggwarsreloaded.game.collection.RewardType;
import net.pistonmaster.eggwarsreloaded.game.collection.TeamColor;
import net.pistonmaster.eggwarsreloaded.utils.ArenaManager;
import net.pistonmaster.eggwarsreloaded.utils.ItemBuilder;
import net.pistonmaster.eggwarsreloaded.utils.StatsManager;
import net.pistonmaster.eggwarsreloaded.utils.UtilCore;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Game {
    /**
     * Players currently in the arena/lobby
     */
    public final List<Player> inGamePlayers = new ArrayList<>();
    /**
     * Teams where people are in.
     */
    public final List<TeamColor> usedTeams = new ArrayList<>();
    public final List<Player> deadPlayers = new ArrayList<>();
    public final int maxPlayers;
    public final int maxTeamPlayers;
    @Getter
    public final String arenaName;
    public final GameLogics gameLogics;
    public final MatchMaker matchmaker;
    public final EggWarsReloaded plugin;
    public final Random random = new Random();
    /**
     * Player currently playing inside the arena.
     */
    protected final List<Player> livingPlayers = new ArrayList<>();
    protected final List<Integer> taskIds = new ArrayList<>();
    final FileConfiguration arenas = ArenaManager.getArenas();
    private final ScoreboardManager scoreboardManager = new ScoreboardManager(this);
    private int startingTime;
    private GameState state;
    private int clockTask;
    private @Getter
    boolean noFall = false;
    private @Getter
    Instant gameStart;

    public Game(String arenaName, EggWarsReloaded plugin) {
        this.arenaName = arenaName;
        this.plugin = plugin;

        state = GameState.UNREGISTERED;

        for (String team : Objects.requireNonNull(arenas.getConfigurationSection(arenaName + ".team")).getKeys(false)) {
            if (ArenaManager.isTeamRegistered(arenaName, TeamColor.fromString(team))) {
                usedTeams.add(TeamColor.fromString(team));
            }
        }

        prepareArena();

        maxTeamPlayers = arenas.getInt(arenaName + ".size");
        maxPlayers = usedTeams.size() * maxTeamPlayers;
        matchmaker = new MatchMaker(arenaName, this);
        matchmaker.readSpawns();
        gameLogics = new GameLogics(this);

        plugin.getLogger().info(ChatColor.GOLD + "Starting game for arena: " + arenaName);

        registerGame();

        state = GameState.LOBBY;

        taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (state == GameState.LOBBY || state == GameState.STARTING1) {
                inGamePlayers.forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(ChatColor.AQUA + "The game will start shortly!").create()));
            }
        }, 0, 20));

        new GeneratorManager(this, plugin);
    }

    public GameState getState() {
        return state;
    }

    public RejectType addPlayer(Player player) {
        if (inGamePlayers.contains(player))
            return RejectType.ALREADY_IN;

        if (!(state == GameState.LOBBY || state == GameState.STARTING1))
            return RejectType.ALREADY_PLAYING;

        if (inGamePlayers.size() >= maxPlayers)
            return RejectType.FULL;

        // Remove everything the player had
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);

        player.setVelocity(new Vector(0, 0, 0));

        player.teleport(ArenaManager.getWaitingLobby(arenaName));

        player.setVelocity(new Vector(0, 0, 0));

        player.setFallDistance(0);

        player.setExp(0);
        player.setFoodLevel(20);
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());

        player.sendTitle(ChatColor.GOLD + "You joined " + ChatColor.AQUA + arenaName + ChatColor.GOLD + "!", "Please wait till the game starts!", 5, 10, 5);

        player.sendMessage(ChatColor.GOLD + "You joined " + ChatColor.AQUA + arenaName + ChatColor.GOLD + "!");

        inGamePlayers.add(player);

        matchmaker.findTeamForPlayer(player);

        player.sendMessage(ChatColor.GOLD + "Your team: " + matchmaker.getTeamOfPlayer(player).getColor() + matchmaker.getTeamOfPlayer(player));

        Scoreboard playerScoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();

        Objective objective = playerScoreboard.registerNewObjective("scoreboard", "dummy", ChatColor.YELLOW + "" + ChatColor.BOLD + "Egg Wars");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score one = objective.getScore("");
        one.setScore(3);

        Score two = objective.getScore(ChatColor.GOLD + "Your team: " + matchmaker.getTeamOfPlayer(player).getColor() + matchmaker.getTeamOfPlayer(player));
        two.setScore(2);

        Score three = objective.getScore(ChatColor.GOLD + "Arena: " + ChatColor.AQUA + arenaName);
        three.setScore(1);

        player.setScoreboard(playerScoreboard);

        for (Player lobbyPlayer : inGamePlayers)
            lobbyPlayer.sendMessage(ChatColor.GOLD + "[ " + ChatColor.AQUA + "+" + ChatColor.GOLD + " ] " + player.getDisplayName() + " " + inGamePlayers.size() + "/" + maxPlayers);

        int minPlayers = 2;
        if (inGamePlayers.size() >= minPlayers && state == GameState.LOBBY)
            startGame1();

        return RejectType.NONE;
    }

    public void removePlayer(Player player) {
        livingPlayers.remove(player);

        player.getInventory().clear();

        player.setVelocity(new Vector(0, 0, 0));

        player.setGameMode(GameMode.SURVIVAL);

        player.teleport(ArenaManager.getMainLobby(arenaName));

        player.setVelocity(new Vector(0, 0, 0));

        player.setFallDistance(0);

        player.setGameMode(GameMode.SURVIVAL);
        player.setExp(0);
        player.setFoodLevel(20);
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());

        player.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());

        GameControl.removePlayerFromGame(player);
    }

    public RejectType kickPlayer(Player player) {
        if (!inGamePlayers.contains(player))
            return RejectType.NOT_IN;

        removePlayer(player);

        checkWin();

        return RejectType.NONE;
    }

    public void spectatorPlayer(Player player, boolean respawn) {
        player.getInventory().clear();

        player.setGameMode(GameMode.SPECTATOR);

        player.setVelocity(new Vector(0, 0, 0));

        player.teleport(ArenaManager.getSpectator(arenaName));

        player.setVelocity(new Vector(0, 0, 0));

        player.setFallDistance(0);

        if (respawn) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> respawnPlayer(player), 100);
        }
    }

    private void respawnPlayer(Player player) {
        deadPlayers.remove(player);

        player.setVelocity(new Vector(0, 0, 0));

        player.teleport(ArenaManager.getRespawn(arenaName, matchmaker.getTeamOfPlayer(player)));

        player.setVelocity(new Vector(0, 0, 0));

        player.setFallDistance(0);

        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.setExp(0);
        player.setFoodLevel(20);
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());

        checkWin();
    }

    public void killPlayer(Player killed, Player killer) {
        if (deadPlayers.contains(killed))
            return;

        deadPlayers.add(killed);

        rewardPlayer(killer, RewardType.KILL);

        killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);

        for (Player livePlayer : inGamePlayers) {
            livePlayer.sendMessage(ChatColor.RED + killer.getDisplayName() + ChatColor.GOLD + " killed " + ChatColor.AQUA + killed.getDisplayName() + ChatColor.GOLD + "!");
        }

        if (Boolean.TRUE.equals(matchmaker.hasTeamEgg.get(matchmaker.getTeamOfPlayer(killed)))) {
            killed.sendMessage(ChatColor.GOLD + "You got killed by " + ChatColor.AQUA + killer.getDisplayName() + ChatColor.GOLD + "! You will respawn in 5 seconds!");
            spectatorPlayer(killed, true);
        } else {
            killed.sendMessage(ChatColor.GOLD + "You got killed by " + killer.getDisplayName() + ChatColor.GOLD + "! Your team has no egg, so you will not respawn now. Please wait till the game ends...");
            livingPlayers.remove(killed);
            spectatorPlayer(killed, false);
        }

        StatsManager.rewardPlayer(killed, StatsManager.Type.DEATH);
        StatsManager.rewardPlayer(killer, StatsManager.Type.KILL);

        checkWin();
    }

    public void deathPlayer(Player player) {
        if (deadPlayers.contains(player))
            return;

        deadPlayers.add(player);

        for (Player livePlayer : inGamePlayers) {
            livePlayer.sendMessage(ChatColor.BLUE + player.getDisplayName() + ChatColor.GOLD + " died! :(");
        }

        if (Boolean.TRUE.equals(matchmaker.hasTeamEgg.get(matchmaker.getTeamOfPlayer(player)))) {
            player.sendMessage(ChatColor.GOLD + "You died! You will respawn in 5 seconds!");
            spectatorPlayer(player, true);
        } else {
            player.sendMessage(ChatColor.GOLD + "You died! Your team has no egg, so you will not respawn now. Please wait till the game ends...");
            livingPlayers.remove(player);
            spectatorPlayer(player, false);
        }

        StatsManager.rewardPlayer(player, StatsManager.Type.DEATH);

        checkWin();
    }

    private void rewardPlayer(Player player, RewardType reward) {
        if (plugin.getEconomy() == null)
            return;

        double amount = 0;

        if (reward == RewardType.GAME) {
            amount = plugin.getConfig().getDouble("rewards.game");
        } else if (reward == RewardType.KILL) {
            amount = plugin.getConfig().getDouble("rewards.kill");
        } else if (reward == RewardType.WIN) {
            amount = plugin.getConfig().getDouble("rewards.win");
        }

        EconomyResponse r = plugin.getEconomy().depositPlayer(player, amount);
        if (r.transactionSuccess()) {
            player.sendMessage(ChatColor.AQUA + "Added " + r.amount + " to your depot!");
        } else {
            player.sendMessage(ChatColor.RED + "An error occurred: " + r.errorMessage);
        }
    }

    public void checkWin() {
        if (gameLogics.isOnlyOneTeamLeft()) {
            for (Player player : inGamePlayers) {
                if (matchmaker.getPlayersInTeam(gameLogics.getLastTeam()).contains(player)) {
                    player.sendMessage(ChatColor.GOLD + "Your team won! gg");

                    rewardPlayer(player, RewardType.WIN);
                    StatsManager.rewardPlayer(player, StatsManager.Type.WIN);
                } else {
                    player.sendMessage(ChatColor.GOLD + "Team " + gameLogics.getLastTeam() + " won! gg");
                    StatsManager.rewardPlayer(player, StatsManager.Type.LOSE);
                }
            }

            endGame();
        }
    }

    private void registerGame() {
        GameControl.addGame(this);

        state = GameState.REGISTERED;
    }

    private void unregisterGame() {
        GameControl.removeGame(this);

        state = GameState.UNREGISTERED;
    }

    public void forceStart() {
        if (state == GameState.STARTING1) {
            Bukkit.getScheduler().cancelTask(clockTask);
            startGame2(5);
        }
    }

    private void startGame1() {
        state = GameState.STARTING1;

        startingTime = 20;

        for (Player player : inGamePlayers) {
            player.setLevel(startingTime);

            if (player.hasPermission("eggwarsreloaded.forcestart")) {
                player.getInventory().setItem(2, new ItemBuilder(Material.DIAMOND).name(ChatColor.AQUA + "Force start").build());
            }
        }

        clockTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            startingTime--;

            inGamePlayers.forEach(player -> {
                player.setLevel(startingTime);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 2);
            });

            if (startingTime == 10) {
                Bukkit.getScheduler().runTask(plugin, () -> startGame2(10));
            }
        }, 20, 20);
    }

    private void startGame2(int timeLeft) {
        state = GameState.STARTING2;

        Bukkit.getScheduler().cancelTask(clockTask);

        scoreboardManager.generateTemplate();

        matchmaker.teleportPlayers();

        startingTime = timeLeft;

        for (Player player : inGamePlayers) {
            player.getInventory().clear();
            player.setLevel(startingTime);
        }

        clockTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            startingTime--;

            inGamePlayers.forEach(player -> {
                player.setLevel(startingTime);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 2);
            });

            if (startingTime == 0) {
                Bukkit.getScheduler().runTask(plugin, this::runGame);
            }
        }, 20, 20);
    }


    private void runGame() {
        Bukkit.getScheduler().cancelTask(clockTask);

        state = GameState.RUNNING;

        gameStart = Instant.now();

        for (Player player : inGamePlayers) {
            player.getInventory().clear();
        }

        livingPlayers.addAll(inGamePlayers);

        taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> inGamePlayers.forEach(scoreboardManager::setScoreboard), 0L, 20L));

        noFall = true;

        setCages(Material.AIR, Material.AIR);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> noFall = false, 60L);

        for (Player player : livingPlayers) {
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    public void endGame() {
        state = GameState.ENDING;

        for (Player player : inGamePlayers) {
            player.sendMessage(ChatColor.GOLD + "The game ended!");
            rewardPlayer(player, RewardType.GAME);
            StatsManager.rewardPlayer(player, StatsManager.Type.GAME);

            removePlayer(player);
        }

        inGamePlayers.clear();

        for (int i : taskIds) {
            Bukkit.getScheduler().cancelTask(i);
        }

        resetArena();

        state = GameState.ENDED;

        unregisterGame();

        createNewGame();
    }

    private void createNewGame() {
        GameControl.addGame(new Game(arenaName, plugin));
    }

    private void prepareArena() {
        plugin.getWorldManager().loadWorld(ArenaManager.getArenaWorld(arenaName), World.Environment.NORMAL);

        World arena = Bukkit.getWorld(ArenaManager.getArenaWorld(arenaName));

        // DON'T save actions the players do in the game
        Objects.requireNonNull(arena).setAutoSave(false);

        setCages(Material.GLASS, Material.BARRIER);

        placeAllEggs();
    }

    private void resetArena() {
        plugin.getWorldManager().unloadWorld(ArenaManager.getArenaWorld(arenaName), false, ArenaManager.getMainLobby(arenaName));
    }

    public void setCages(Material material, Material topMaterial) {
        for (TeamColor team : usedTeams) {
            for (String spawn : ArenaManager.getArenas().getStringList(arenaName + ".team." + team + ".spawn")) {
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

                ground.setType(material);

                first1.setType(material);
                first2.setType(material);
                first3.setType(material);
                first4.setType(material);

                second1.setType(material);
                second2.setType(material);
                second3.setType(material);
                second4.setType(material);

                third1.setType(material);
                third2.setType(material);
                third3.setType(material);
                third4.setType(material);

                top.setType(topMaterial);
            }
        }
    }

    private void placeAllEggs() {
        List<Location> eggs = new ArrayList<>();

        for (TeamColor team : usedTeams) {
            eggs.add(UtilCore.convertLocation(ArenaManager.getArenas().getString(arenaName + ".team." + team + ".egg")));
        }

        for (Location loc : eggs) {
            loc.getBlock().setType(Material.DRAGON_EGG);
        }
    }

    public void eggDestroyed(TeamColor team) {
        matchmaker.hasTeamEgg.remove(team);
        matchmaker.hasTeamEgg.put(team, false);

        for (Player player : matchmaker.getPlayersInTeam(team)) {
            player.sendTitle(ChatColor.GOLD + "Your egg has been destroyed!", ChatColor.GOLD + "You will no longer respawn!", 10, 20, 10);
        }
    }
}
