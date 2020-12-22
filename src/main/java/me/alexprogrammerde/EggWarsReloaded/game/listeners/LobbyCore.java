package me.alexprogrammerde.EggWarsReloaded.game.listeners;

import me.alexprogrammerde.EggWarsReloaded.game.GameControl;
import me.alexprogrammerde.EggWarsReloaded.game.collection.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class LobbyCore implements Listener {

    @EventHandler
    public void onItemClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (GameControl.isInGame(player) && (
                GameControl.getPlayerGame(player).state != GameState.RUNNING)) {
            event.setCancelled(true);
        }
    }
}
