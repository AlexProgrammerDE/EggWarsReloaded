package me.alexprogrammerde.eggwarsreloaded.game.listeners;

import me.alexprogrammerde.eggwarsreloaded.game.Game;
import me.alexprogrammerde.eggwarsreloaded.game.GameControl;
import me.alexprogrammerde.eggwarsreloaded.game.collection.GameState;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (GameControl.isInGame(player)) {
                Game game = GameControl.getPlayerGame(player);

                if (game.state == GameState.RUNNING && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                    if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                        event.setCancelled(true);

                        if (player.getKiller() == null) {
                            game.deathPlayer(player);
                        } else {
                            game.killPlayer(player, player.getKiller());
                        }
                    } else {
                        if ((player.getHealth() - event.getDamage()) < 1) {
                            event.setCancelled(true);

                            if (player.getKiller() == null) {
                                game.deathPlayer(player);
                            } else {
                                game.killPlayer(player, player.getKiller());
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPVP(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();

            if (GameControl.isInGame(damager) && GameControl.isInGame(damaged)) {
                Game game = GameControl.getPlayerGame(damaged);

                if (game.state == GameState.RUNNING && (damaged.getHealth() - event.getDamage()) < 1) {
                    event.setCancelled(true);

                    game.killPlayer(damaged, damager);
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (GameControl.isInGame(player)) {
            event.setQuitMessage(null);
            Game game = GameControl.getPlayerGame(player);

            game.removePlayer(player);

            for (Player p : game.inGamePlayers) {
                p.sendMessage(ChatColor.GOLD + player.getDisplayName() + " left the match!");
            }

            game.checkWin();
        }
    }

    @EventHandler
    public void onDamage2(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (GameControl.isInGame(player)) {
                Game game = GameControl.getPlayerGame(player);

                if (game.noFall && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
