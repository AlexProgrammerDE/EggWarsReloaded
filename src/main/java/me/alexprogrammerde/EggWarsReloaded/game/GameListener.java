package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.game.collection.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

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


    }
}
