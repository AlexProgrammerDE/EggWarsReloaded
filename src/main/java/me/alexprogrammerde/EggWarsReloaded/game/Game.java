package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsMain;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    String arenaname;
    int taskID;
    List<Player> playerlist = new ArrayList<>();
    public boolean isPlaying = false;

    public Game(String arenaname) {
        this.arenaname = arenaname;
        GameRegisterer.addGame(arenaname, this);
        EggWarsMain.getEggWarsMain().getServer().getPluginManager().registerEvents(new GameListener(), EggWarsMain.getEggWarsMain());

        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsMain.getEggWarsMain(), new Runnable() {
            @Override
            public void run() {
                for (Player player : playerlist) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("The game is starting soon!"));
                }
            }
        }, 20, 20);
    }

    public int getTaskID() {
        return taskID;
    }

    public void addPlayer(Player player) {
        if (!playerlist.contains(player)) {
            playerlist.add(player);
            player.teleport(Objects.requireNonNull(EggWarsMain.getEggWarsMain().getArenas().getLocation("arenas." + arenaname + ".waitinglobby")));
        }
    }

    public void removePlayer(Player player) {
        if (playerlist.contains(player)) {
            playerlist.remove(player);
        }
    }
}
