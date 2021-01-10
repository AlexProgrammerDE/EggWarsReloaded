package me.alexprogrammerde.eggwarsreloaded.game.guis;

import me.alexprogrammerde.eggwarsreloaded.EggWarsReloaded;
import me.alexprogrammerde.eggwarsreloaded.utils.ArenaManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TeamMenu {
    public static void setupTeamMenu(Inventory inventory, String arenaName, Player player, EggWarsReloaded plugin) {
        FileConfiguration items = plugin.getItems();
        FileConfiguration arenas = ArenaManager.getArenas();


    }
}
