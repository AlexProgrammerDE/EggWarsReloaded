package me.alexprogrammerde.eggwarsreloaded.game.guis;

import me.alexprogrammerde.eggwarsreloaded.EggWarsReloaded;
import me.alexprogrammerde.eggwarsreloaded.game.collection.TeamColor;
import me.alexprogrammerde.eggwarsreloaded.game.shop.ShopItems;
import me.alexprogrammerde.eggwarsreloaded.game.shop.ShopUtil;
import me.alexprogrammerde.eggwarsreloaded.utils.ItemBuilder;
import me.alexprogrammerde.eggwarsreloaded.utils.gui.GUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Shop {
    private Shop() {
    }

    public static void setupShopMenu(TeamColor team, Player player, EggWarsReloaded plugin) {
        GUI gui = new GUI("Shop", 3, plugin, player);

        int i = 0;
        for (ShopItems shopItem : ShopItems.values()) {
            gui.addItem(new ItemBuilder(shopItem, team).build(), i).addDefaultEvent(() -> {
                ShopUtil.buyItem(shopItem, player, team);

                setupShopMenu(team, player, plugin);
            });
            i++;
        }

        ItemStack closeButton = new ItemBuilder(Material.BARRIER).build();

        gui.addItem(closeButton, gui.getSize() - 1).addDefaultEvent(player::closeInventory);

        gui.openGUI();
    }
}
