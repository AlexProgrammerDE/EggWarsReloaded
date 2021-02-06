package net.pistonmaster.eggwarsreloaded.game.listeners;

import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import net.pistonmaster.eggwarsreloaded.game.Game;
import net.pistonmaster.eggwarsreloaded.game.GameControl;
import net.pistonmaster.eggwarsreloaded.game.collection.GameState;
import net.pistonmaster.eggwarsreloaded.game.guis.Shop;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ShopListener implements Listener {
    private final EggWarsReloaded plugin;

    public ShopListener(EggWarsReloaded plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (GameControl.isInGame(player)) {
            Game game = GameControl.getPlayerGame(player);

            if (game.getState() == GameState.RUNNING && event.getRightClicked().getType() == EntityType.VILLAGER) {
                event.setCancelled(true);
                Shop.setupShopMenu(game.matchmaker.getTeamOfPlayer(player), player, plugin);
            }
        }
    }
}
