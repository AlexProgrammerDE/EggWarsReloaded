package me.alexprogrammerde.EggWarsReloaded.admin;

import me.alexprogrammerde.EggWarsReloaded.admin.guis.EditMenu;
import org.bukkit.entity.Player;

public class EditManager {
    Player player;

    public EditManager(Player player) {
        this.player = player;
    }

    public void openMenu(String arenaname) {
        EditMenu.openEditMenu(arenaname, player);
    }
}
