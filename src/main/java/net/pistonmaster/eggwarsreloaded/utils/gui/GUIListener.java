package net.pistonmaster.eggwarsreloaded.utils.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GUIListener implements Listener {
    private final Inventory inv;
    private final Player player;
    private final int slot;
    private final GUI gui;
    public Runnable defaultTask;
    public Map<InventoryAction, Runnable> actions = new HashMap<>();

    public GUIListener(GUI gui, Inventory inv, Player player, int slot) {
        this.gui = gui;
        this.inv = inv;
        this.player = player;
        this.slot = slot;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player eventPlayer = (Player) event.getWhoClicked();

            if (eventPlayer.equals(player) && Objects.equals(event.getClickedInventory(), inv) && event.getSlot() == slot) {
                boolean d = true;

                for (Map.Entry<InventoryAction, Runnable> entry : actions.entrySet()) {
                    if (event.getAction().equals(entry.getKey())) {
                        entry.getValue().run();
                        d = false;
                        break;
                    }
                }

                if (d && event.getAction() != InventoryAction.NOTHING && event.getAction() != InventoryAction.UNKNOWN) {
                    defaultTask.run();
                }

                event.setCancelled(true);

                gui.unregisterAllItems();
            }
        }
    }
}
