package net.pistonmaster.eggwarsreloaded.game.shop;

import com.cryptomorin.xseries.XMaterial;
import net.md_5.bungee.api.ChatColor;
import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import net.pistonmaster.eggwarsreloaded.game.collection.TeamColor;
import net.pistonmaster.eggwarsreloaded.utils.ItemBuilder;
import net.pistonmaster.eggwarsreloaded.utils.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class Shop {
    private Shop() {
    }

    public static void setupShopMenu(TeamColor team, Player player, EggWarsReloaded plugin) {
        GUI gui = new GUI("Shop", 3, plugin, player);

        int i = 0;
        for (ShopItems shopItem : ShopItems.values()) {
            gui.addItem(new ItemBuilder(shopItem, team).build(), i)
                    .addEvent(InventoryAction.MOVE_TO_OTHER_INVENTORY, () -> {
                        boolean hasBulked = false;

                        while (ShopUtil.canPay(shopItem.getPrice(), player)) {
                            hasBulked = true;
                            ShopUtil.buyItem(shopItem, player, team);
                        }

                        if (hasBulked) {
                            player.sendMessage(ChatColor.GOLD + "Successfully bulk bought the item!");
                        } else {
                            player.sendMessage(ChatColor.GOLD + "You don't have enough money to buy this!");
                        }

                        setupShopMenu(team, player, plugin);
                    })
                    .addDefaultEvent(() -> {
                        if (ShopUtil.buyItem(shopItem, player, team)) {
                            player.sendMessage(ChatColor.GOLD + "Successfully bought the item!");
                        }

                        setupShopMenu(team, player, plugin);
                    });
            i++;
        }

        ItemStack closeButton = new ItemBuilder(XMaterial.BARRIER).build();

        gui.addItem(closeButton, gui.getSize() - 1).addDefaultEvent(player::closeInventory);

        gui.openGUI();
    }
}
