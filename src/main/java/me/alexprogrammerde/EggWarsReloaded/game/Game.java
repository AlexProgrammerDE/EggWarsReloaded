package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.game.collection.GameState;
import me.alexprogrammerde.EggWarsReloaded.game.collection.RejectType;
import me.alexprogrammerde.EggWarsReloaded.game.collection.RewardType;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

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
    protected final MatchMaker matchmaker;
    final List<String> usedTeams = new ArrayList<>();
    private int clockTask;
    public final int maxPlayers;
    public final int maxTeamPlayers;
    protected final List<Player> livingPlayers = new ArrayList<>();
    private final GameLogics gameLogics;

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
        gameLogics = new GameLogics(this);

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

        if (!(state == GameState.LOBBY || state == GameState.STARTING1)) {
            return RejectType.ALREADYPLAYING;
        }

        if (players.size() >= maxPlayers) {
            return RejectType.FULL;
        }

        // Remove everything the player had
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);

        player.teleport(UtilCore.convertLocation(arenas.getString("arenas." + arenaName + ".waitinglobby")));

        player.setLevel(0);
        player.setFoodLevel(20);
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());

        player.sendTitle("You joined " + arenaName + "!", "Please wait till the game starts!", 5, 10, 5);

        player.sendMessage("You joined " + arenaName + "!");

        players.add(player);

        matchmaker.findTeamForPlayer(player);

        player.sendMessage("Your in the team: " + matchmaker.getTeamOfPlayer(player));

        Scoreboard playerScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = playerScoreboard.registerNewObjective("scoreboard", "dummy", "EggWarsReloaded");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score one = objective.getScore("");
        one.setScore(3);

        Score two = objective.getScore("Your team: " + matchmaker.getTeamOfPlayer(player));
        two.setScore(2);

        Score three = objective.getScore("Your arena: " + arenaName);
        three.setScore(1);

        player.setScoreboard(playerScoreboard);

        for (Player lobbyPlayer : players) {
            lobbyPlayer.sendMessage("[ + ] " + player.getDisplayName() + " " + players.size() + "/" + maxPlayers);
        }

        // TODO: Make player requirement optional and don't run it again if someone joins
        int minPlayers = 2;
        if (players.size() >= minPlayers) {
            if (state == GameState.LOBBY) {
                startGame1();
            }
        }

        return RejectType.NONE;
    }

    public void removePlayer(Player player) {
        players.remove(player);
        livingPlayers.remove(player);

        player.getInventory().clear();

        player.teleport(UtilCore.convertLocation(ArenaManager.getArenas().getString("arenas." + arenaName + ".mainlobby")));

        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.setLevel(0);
        player.setFoodLevel(20);
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());

        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public RejectType kickPlayer(Player player) {
        if (!players.contains(player)) {
            return RejectType.NOTIN;
        }

        spectatorPlayer(player);

        return RejectType.NONE;
    }

    private void spectatorPlayer(Player player) {
        player.getInventory().clear();

        player.teleport(UtilCore.convertLocation(ArenaManager.getArenas().getString("arenas." + arenaName + ".spectator")));

        player.setGameMode(GameMode.SPECTATOR);

        Bukkit.getScheduler().runTaskLater(EggWarsReloaded.getEggWarsMain(), () -> {
            respawnPlayer(player);
        }, 100);
    }

    public void killPlayer(Player killed, Player killer) {
        if (!matchmaker.hasTeamEgg.get(matchmaker.getTeamOfPlayer(killed)))  {
            livingPlayers.remove(killed);
        }

        rewardPlayer(killed, RewardType.KILL);

        checkWin();

        for (Player player : livingPlayers) {
            player.sendMessage(killer.getDisplayName() + " killed " + killed.getDisplayName() + "!");
        }

        spectatorPlayer(killed);
    }

    private void respawnPlayer(Player player) {
        player.teleport(UtilCore.convertLocation(ArenaManager.getArenas().getString("arenas." + arenaName + ".team." + matchmaker.getTeamOfPlayer(player) + ".respawn")));

        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.setLevel(0);
        player.setFoodLevel(20);
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());

        player.getInventory().setItem(0, getNetheriteSword());
    }

    public void deathPlayer(Player player) {
        if (!matchmaker.hasTeamEgg.get(matchmaker.getTeamOfPlayer(player)))  {
            livingPlayers.remove(player);
        }

        for (Player livePlayer : livingPlayers) {
            livePlayer.sendMessage(player.getDisplayName() + " died! :(");
        }

        checkWin();

        spectatorPlayer(player);
    }

    private void checkWin() {
        if (gameLogics.isOnlyOneTeamLeft()) {
            for (Player player : matchmaker.getPlayersInTeam(gameLogics.getLastTeam())) {
                rewardPlayer(player, RewardType.WIN);
                player.sendMessage("You won! gg");
            }
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

        livingPlayers.addAll(players);

        destroyCages();

        for (Player player : livingPlayers) {
            player.setGameMode(GameMode.SURVIVAL);
        }



        for (Player player : players) {
            player.getInventory().setItem(0, getNetheriteSword());
        }
    }

    private void rewardPlayer(Player player, RewardType reward) {

    }

    public void endGame() {
        state = GameState.ENDING;

        for (Player player : players) {
            player.sendMessage("The game ended!");
            rewardPlayer(player, RewardType.GAME);

            removePlayer(player);
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

        placeCages();

        placeAllEggs();
    }

    private void resetArena() {
        World arena = Bukkit.getWorld(ArenaManager.getArenas().getString("arenas." + arenaName + ".world"));

        // Unload world to remove actions done by players and not saving them
        Bukkit.unloadWorld(arena, false);

        // Reload world to make use of it and reenable autosaving
        Bukkit.createWorld(new WorldCreator(Objects.requireNonNull(ArenaManager.getArenas().getString("arenas." + arenaName + ".world")))).setAutoSave(true);
    }

    private void placeCages() {
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

    private void placeAllEggs() {
        List<Location> eggs = new ArrayList<>();

        for (String team : usedTeams) {
            eggs.add(UtilCore.convertLocation(ArenaManager.getArenas().getString("arenas." + arenaName + ".team." + team + ".egg")));
        }

        for (Location loc : eggs) {
            loc.getBlock().setType(Material.DRAGON_EGG);
        }
    }

    public void eggDestroyed(String team) {
        matchmaker.hasTeamEgg.remove(team);
        matchmaker.hasTeamEgg.put(team, false);

        for (Player player : matchmaker.getPlayersInTeam(team)) {
            player.sendTitle("Your egg has been destroyed!", "You will no longer respawn!", 10, 20, 10);
        }
    }

    private ItemStack getNetheriteSword() {
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);

        ItemMeta swordMeta = sword.getItemMeta();

        swordMeta.setDisplayName(ChatColor.AQUA + "Just a normal sword...");
        swordMeta.setLore(new ArrayList<String>() {
            {
                add("uwu its a me Loreio");
            }
        });

        sword.setItemMeta(swordMeta);

        return sword;
    }
}
