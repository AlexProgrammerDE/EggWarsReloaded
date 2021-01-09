package me.alexprogrammerde.EggWarsReloaded.game.listeners;

import me.alexprogrammerde.EggWarsReloaded.game.Game;
import me.alexprogrammerde.EggWarsReloaded.game.GameControl;
import me.alexprogrammerde.EggWarsReloaded.game.collection.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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

                if (game.state == GameState.LOBBY || game.state == GameState.STARTING1) {

                }
            }
        }


    }
}
