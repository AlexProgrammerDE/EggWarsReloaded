package me.alexprogrammerde.eggwarsreloaded.game.collection;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;

public enum LobbyButtons {
    // TODO Use LobbyButtons
    FORCE_START(Material.DIAMOND, 2, ChatColor.AQUA  + "Force start");

    private final Material material;
    private final int slot;
    private final String name;

    LobbyButtons(Material material, int slot, String name) {
        this.material = material;
        this.slot = slot;
        this.name = name;
    }
}
