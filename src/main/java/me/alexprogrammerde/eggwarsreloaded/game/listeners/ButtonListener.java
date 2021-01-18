package me.alexprogrammerde.eggwarsreloaded.game.listeners;

import me.alexprogrammerde.eggwarsreloaded.game.Game;
import me.alexprogrammerde.eggwarsreloaded.game.GameControl;
import me.alexprogrammerde.eggwarsreloaded.game.collection.GameState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Listener for various buttons you may trigger.
 */
public class ButtonListener implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack hand = player.getInventory().getItemInMainHand();

            if (GameControl.isInGame(player)) {
                Game game = GameControl.getPlayerGame(player);

                if (game.getState() == GameState.LOBBY || game.getState() == GameState.STARTING1 && player.getInventory().getHeldItemSlot() == 2
                        && hand.getType() == Material.DIAMOND
                        && player.hasPermission("eggwarsreloaded.forcestart")) {
                    game.forceStart();
                }
            }
        }
    }

    @EventHandler
    public void onInvMove(InventoryMoveItemEvent event) {
        if (event.getInitiator() instanceof PlayerInventory) {
            Player player = (Player) event.getInitiator().getHolder();

            if (GameControl.isInGame(player)) {
                Game game = GameControl.getPlayerGame(player);

                if (game.getState() == GameState.LOBBY || game.getState() == GameState.STARTING1) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (GameControl.isInGame(player)) {
            Game game = GameControl.getPlayerGame(player);

            if (game.getState() == GameState.LOBBY || game.getState() == GameState.STARTING1) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSwamp(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        if (GameControl.isInGame(player)) {
            Game game = GameControl.getPlayerGame(player);

            if (game.getState() == GameState.LOBBY || game.getState() == GameState.STARTING1) {
                event.setCancelled(true);
            }
        }
    }
}
