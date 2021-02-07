package net.pistonmaster.eggwarsreloaded.game.guis;

import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import net.pistonmaster.eggwarsreloaded.game.collection.TeamColor;
import net.pistonmaster.eggwarsreloaded.game.shop.ShopItems;
import net.pistonmaster.eggwarsreloaded.game.shop.ShopUtil;
import net.pistonmaster.eggwarsreloaded.utils.ItemBuilder;
import net.pistonmaster.eggwarsreloaded.utils.gui.GUI;
import com.cryptomorin.xseries.XMaterial;
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

        ItemStack closeButton = new ItemBuilder(XMaterial.BARRIER).build();

        gui.addItem(closeButton, gui.getSize() - 1).addDefaultEvent(player::closeInventory);

        gui.openGUI();
    }
}
