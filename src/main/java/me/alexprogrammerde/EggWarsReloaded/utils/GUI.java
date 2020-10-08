package me.alexprogrammerde.EggWarsReloaded.utils;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUI {
    Player player;
    Inventory inv;
    EggWarsReloaded plugin;
    GUIListener listener;

    public GUI (String name, int lines, Player player, EggWarsReloaded plugin) {
        this.player = player;
        inv = Bukkit.createInventory(player, lines * 9, name);
        this.plugin = plugin;
        listener = new GUIListener(inv)
    }

    public void addItem(ItemStack item, int slot) {
        inv.setItem(slot, item);
        plugin.getServer().getPluginManager().registerEvents(new GUIListener(inv, slot), plugin);
    }

    public void addEvent(int slot, Runnable run1, Runnable run2) {
        plugin.getServer().getPluginManager().registerEvents(new GUIListener(inv, slot), plugin);
    }
}

class GUIListener implements Listener {
    Inventory inv;
    

    public GUIListener(Inventory inv) {
        this.inv = inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

        }
    }
}
