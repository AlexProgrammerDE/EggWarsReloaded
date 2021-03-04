package net.pistonmaster.eggwarsreloaded.utils.gui;

import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;

public class GUIItem {
    private final GUIListener guiListener;

    public GUIItem(GUI gui, int slot, Inventory inv, Player player, EggWarsReloaded plugin) {
        guiListener = new GUIListener(gui, inv, player, slot);

        Bukkit.getServer().getPluginManager().registerEvents(guiListener, plugin);
    }

    public void addDefaultEvent(Runnable exec) {
        guiListener.setDefaultTask(exec);
    }

    public GUIItem addEvent(InventoryAction action, Runnable exec) {
        guiListener.getActions().put(action, exec);

        return this;
    }

    public void unregisterAllListeners() {
        HandlerList.unregisterAll(guiListener);
    }
}
