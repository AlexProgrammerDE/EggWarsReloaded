package me.alexprogrammerde.EggWarsReloaded.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            if (GameLib.playergame.containsKey(player)) {
                GameLib gameLib = GameLib.playergame.get(player);
            }
        }
    }

}
