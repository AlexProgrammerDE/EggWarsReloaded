package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.game.collection.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class GameListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (GameControl.isInGame(player)) {
            Game game = GameControl.getPlayerGame(player);

            if (game.state == GameState.LOBBY || game.state == GameState.STARTING1) {
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

                if (game.state == GameState.LOBBY || game.state == GameState.STARTING1) {
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

                // TODO: Lobby protection

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

                // TODO: Lobby protection

                if (game.state == GameState.RUNNING) {
                    if((damaged.getHealth() - event.getDamage()) < 1) {
                        event.setCancelled(true);

                        game.killPlayer(damaged, damager);
                    }
                }
            }
        }
    }
}
