package net.pistonmaster.eggwarsreloaded.game.listeners;

import net.md_5.bungee.api.ChatColor;
import net.pistonmaster.eggwarsreloaded.game.Game;
import net.pistonmaster.eggwarsreloaded.game.GameControl;
import net.pistonmaster.eggwarsreloaded.game.collection.GameState;
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

                if (game.getState() == GameState.RUNNING && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
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

                if (game.getState() == GameState.RUNNING && (damaged.getHealth() - event.getDamage()) < 1) {
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
            Game game = GameControl.getPlayerGame(player);
            event.setQuitMessage(null);

            game.removePlayer(player);

            for (Player p : game.inGamePlayers) {
                if (game.getState() == GameState.LOBBY || game.getState() == GameState.STARTING1) {
                    p.sendMessage(ChatColor.GOLD + "[ " + ChatColor.RED + "-" + ChatColor.GOLD + " ] " + player.getDisplayName() + " " + game.inGamePlayers.size() + "/" + game.maxPlayers);
                } else {
                    p.sendMessage(ChatColor.GOLD + player.getDisplayName() + " left the match!");
                }
            }

            if (game.getState() == GameState.RUNNING)
                game.checkWin();
        }
    }

    @EventHandler
    public void onDamage2(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (GameControl.isInGame(player)) {
                Game game = GameControl.getPlayerGame(player);

                if (game.isNoFall() && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
