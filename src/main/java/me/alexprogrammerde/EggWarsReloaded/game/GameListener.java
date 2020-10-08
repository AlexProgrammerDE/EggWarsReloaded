package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.gui.Shop;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class GameListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (GameLib.playergame.containsKey(player)) {
            GameLib.playergame.get(player).removePlayer(player);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (GameLib.playergame.containsKey(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (GameLib.playergame.containsKey(player)) {
                event.setFoodLevel(20);
            }
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                Player player = (Player) event.getEntity();

                if (GameLib.playergame.containsKey(player)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPVP(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (GameLib.playergame.get(player).isLobby) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (GameLib.playergame.containsKey(player)) {
            GameLib.playergame.get(player).death(player);
        }
    }

    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (GameLib.playergame.containsKey(player)) {
            GameLib gameLib = GameLib.playergame.get(player);

            if (gameLib.isPlaying) {
                if (event.getRightClicked().getType() == EntityType.VILLAGER) {
                    Villager villager = (Villager) event.getRightClicked();

                    Inventory inv = Bukkit.createInventory(null, 3 * 9, "");

                    Shop.setupShopMenu(inv, 0);

                    player.openInventory(inv);
                }
            }
        }
    }
}
