package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.game.collection.GameState;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GameListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (GameControl.isInGame(player)) {
            Game game = GameControl.getPlayerGame(player);

            if (game.state != GameState.RUNNING) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (GameControl.isInGame(player)) {
                Game game = GameControl.getPlayerGame(player);

                if (game.state != GameState.RUNNING) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (GameControl.isInGame(player)) {
                Game game = GameControl.getPlayerGame(player);

                if (game.state != GameState.RUNNING || event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    event.setCancelled(true);
                }

            }
        }
    }

    @EventHandler
    public void onPVP(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();

            if (GameControl.isInGame(damager)) {
                Game game = GameControl.getPlayerGame(damager);

                if (game.state != GameState.RUNNING) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (GameControl.isInGame(player)) {
            Game game = GameControl.getPlayerGame(player);
            if (game.state == GameState.RUNNING) {
                if (player.getKiller() == null) {
                    game.deathPlayer(player);
                } else {
                    game.killPlayer(player, player.getKiller());
                }

                event.setDeathMessage(null);
            }

        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (GameControl.isInGame(player)) {
            Game game = GameControl.getPlayerGame(player);

            if (game.state == GameState.RUNNING) {
                if (game.matchmaker.hasTeamEgg.get(game.matchmaker.getTeamOfPlayer(player))) {
                    event.setRespawnLocation(UtilCore.convertLocation(ArenaManager.getArenas().getString("arenas." + game.arenaName + ".team." + game.matchmaker.getTeamOfPlayer(player) + ".respawn")));

                    ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);

                    ItemMeta swordMeta = sword.getItemMeta();

                    swordMeta.setDisplayName(ChatColor.AQUA + "Just a normal sword...");
                    swordMeta.setLore(new ArrayList<String>() {
                        {
                            add("uwu its a me Loreio");
                        }
                    });

                    sword.setItemMeta(swordMeta);

                    player.getInventory().setItem(0, sword);
                } else {
                    event.setRespawnLocation(UtilCore.convertLocation(ArenaManager.getArenas().getString("arenas." + game.arenaName + ".spectator")));

                    player.setGameMode(GameMode.SPECTATOR);
                }
            }
        }
    }
}
