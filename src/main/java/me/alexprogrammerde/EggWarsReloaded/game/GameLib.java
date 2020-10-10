package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
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

public class GameLib {

    public GameLib(String arenaName) {
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();
        this.arenaName = arenaName;
        isLobby = true;
        teleportspawn = new TeleportSpawn(arenaName);

        for (String team : Objects.requireNonNull(arenas.getConfigurationSection("arenas." + arenaName + ".team")).getKeys(false)) {
            teleportspawn.addTeam(team);
        }

        tasks[3] = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.getEggWarsMain(), () -> {
            for (Player player : playerList) {
                if (!player.isOnline()) {
                    removePlayer(player);
                }

                if (playerList.contains(player) && isLobby) {
                    UtilCore.sendActionBar(player, "The game is starting soon!");
                }

                if (playerList.size() >= 2) {
                    lobbyTime--;
                    player.setLevel(lobbyTime);
                } else {
                    player.setLevel(0);
                    lobbyTime = 30;
                }

                if (lobbyTime == 1) {
                    start();
                }
            }
        }, 20, 20);
    }

    public void addPlayer(Player player) {
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

        if (!playerList.contains(player)) {
            playerList.add(player);
            playerGame.put(player, this);
            player.setExp(0);
            player.setGameMode(GameMode.SURVIVAL);
            player.setLevel(0);
            player.teleport(Objects.requireNonNull(arenas.getLocation("arenas." + arenaName + ".waitinglobby")));
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
        if (playerList.contains(player)) {
            playerList.remove(player);
            playerGame.remove(player);

            player.getInventory().clear();

            player.teleport(Objects.requireNonNull(EggWarsReloaded.getEggWarsMain().getArenas().getLocation("arenas." + arenaName + ".mainlobby")));
        }
    }
}