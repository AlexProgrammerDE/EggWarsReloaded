package me.alexprogrammerde.EggWarsReloaded.game.shop;

import me.alexprogrammerde.EggWarsReloaded.game.GameControl;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShopListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        List<ItemStack> diamond = new ArrayList<>();
        List<ItemStack> gold = new ArrayList<>();
        List<ItemStack> iron = new ArrayList<>();


        if (GameControl.isInGame(player)) {
            for (ItemStack item : player.getInventory().getContents()) {
                if (item.getType() == Material.DIAMOND) {

                } else if (item.getType() == Material.GOLD_INGOT) {

                } else if (item.getType() == Material.IRON_INGOT) {

                }
            }
        }
    }
}
