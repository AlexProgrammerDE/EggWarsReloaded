package me.alexprogrammerde.EggWarsReloaded.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LobbyListener implements Listener {

    @EventHandler
    public void onItemClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (GameLib.playergame.containsKey(player) && GameLib.playergame.get(player).isLobby) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (player.getInventory().getHeldItemSlot() == 1) {
                    GameLib.playergame.get(player).start();
                }
            }
        }
    }
}
