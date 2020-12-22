package me.alexprogrammerde.EggWarsReloaded.game.guis;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TeamMenu {
    public static void setupTeamMenu(Inventory inventory, String arenaName, Player player) {
        FileConfiguration items = EggWarsReloaded.getEggWarsMain().getItems();
        FileConfiguration arenas = ArenaManager.getArenas();


    }
}
