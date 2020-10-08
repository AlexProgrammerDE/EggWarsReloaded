package me.alexprogrammerde.EggWarsReloaded.utils.gui;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GUI {
    final Inventory inv;
    final EggWarsReloaded plugin;
    final Player player;
    private final List<GUIItem> guiItems = new ArrayList<>();

    public GUI(String name, int lines, EggWarsReloaded plugin, Player player) {
        inv = Bukkit.createInventory(player, lines * 9, name);
        this.plugin = plugin;
        this.player = player;
    }

    public GUIItem addItem(ItemStack item, int slot) {
        inv.setItem(slot, item);

        GUIItem guiitem = new GUIItem(this, slot, inv, player);

        guiItems.add(guiitem);

        return guiitem;
    }

    public void openGUI() {
        player.openInventory(inv);
    }

    public void unregisterAllItems() {
        for (GUIItem guiItem : guiItems) {
            guiItem.unregisterAllListeners();
        }
    }
}

