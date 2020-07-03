package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsMain;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Game {
    String arenaname;
    int[] taskID;
    List<Player> playerlist = new ArrayList<>();
    public boolean isPlaying = false;
    public static HashMap<Player, Game> playergame = new HashMap<>();
    BossBar lobbybar = Bukkit.createBossBar("The game starts soon!", BarColor.RED, BarStyle.SOLID);
    int time = 0;

    public Game(String arenaname) {
        this.arenaname = arenaname;

        this.taskID[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsMain.getEggWarsMain(), new Runnable() {
            @Override
            public void run() {
                for (Player player : playerlist) {
                    if (!player.isOnline()) {
                        removePlayer(player);
                    }

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("The game is starting soon!"));
                }
            }
        }, 20, 20);
    }

    public int[] getTaskID() {
        return taskID;
    }

    public void addPlayer(Player player) {
        if (!playerlist.contains(player)) {
            playerlist.add(player);
            playergame.put(player, this);
            player.setExp(0);
            player.setGameMode(GameMode.SURVIVAL);
            player.setLevel(0);
            lobbybar.addPlayer(player);
            player.teleport(Objects.requireNonNull(EggWarsMain.getEggWarsMain().getArenas().getLocation("arenas." + arenaname + ".waitinglobby")));
        }
    }

    public void removePlayer(Player player) {
        if (playerlist.contains(player)) {
            playerlist.remove(player);
            lobbybar.removePlayer(player);
            playergame.remove(player);
        }
    }
}
