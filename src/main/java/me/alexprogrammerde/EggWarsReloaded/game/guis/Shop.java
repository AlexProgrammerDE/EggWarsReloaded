package me.alexprogrammerde.EggWarsReloaded.game.guis;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import org.bukkit.configuration.file.FileConfiguration;

public class Shop {
    public static void setupShopMenu(String arenaName, String teamName) {
        FileConfiguration items = EggWarsReloaded.getEggWarsMain().getItems();
        FileConfiguration arenas = ArenaManager.getArenas();


    }
}
