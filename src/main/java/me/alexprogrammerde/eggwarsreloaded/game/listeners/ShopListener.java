package me.alexprogrammerde.eggwarsreloaded.game.listeners;

import me.alexprogrammerde.eggwarsreloaded.EggWarsReloaded;
import me.alexprogrammerde.eggwarsreloaded.game.Game;
import me.alexprogrammerde.eggwarsreloaded.game.GameControl;
import me.alexprogrammerde.eggwarsreloaded.game.collection.GameState;
import me.alexprogrammerde.eggwarsreloaded.game.guis.Shop;
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

            if (game.state == GameState.RUNNING && event.getRightClicked().getType() == EntityType.VILLAGER) {
                event.setCancelled(true);
                Shop.setupShopMenu(game.matchmaker.getTeamOfPlayer(player), player, plugin);
            }
        }
    }
}
