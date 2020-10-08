package me.alexprogrammerde.EggWarsReloaded.utils.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;

public class GUIItem {
    int slot;
    GUI gui;
    Inventory inv;
    Player player;
    private final GUIListener guiListener;

    public GUIItem(GUI gui, int slot, Inventory inv, Player player) {
        this.slot = slot;
        this.gui = gui;
        this.inv = inv;
        this.player = player;

        guiListener = new GUIListener(gui, inv, player, slot);

        Bukkit.getServer().getPluginManager().registerEvents(guiListener, gui.plugin);
    }

    public GUIItem addDefaultEvent(Runnable exec) {
        guiListener.defaultTask = exec;

        return this;
    }

    public GUIItem addEvent(InventoryAction action, Runnable exec) {
        guiListener.actions.put(action, exec);

        return this;
    }

    public void unregisterAllListeners() {
        HandlerList.unregisterAll(guiListener);
    }
}
