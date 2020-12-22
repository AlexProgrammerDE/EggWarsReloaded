package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.game.collection.GameState;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class GeneratorManager {
    public GeneratorManager(Game game) {
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

        game.taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.getEggWarsMain(), () -> {
            if (game.state == GameState.RUNNING) {
                for (String generator : arenas.getStringList(game.arenaName + ".iron")) {
                    UtilCore.convertLocation(generator).getWorld().dropItemNaturally(UtilCore.convertLocation(generator), new ItemStack(Material.IRON_INGOT));
                }
            }
        }, 11, 56));

        game.taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.getEggWarsMain(), () -> {
            if (game.state == GameState.RUNNING) {
                for (String generator : arenas.getStringList(game.arenaName + ".gold")) {
                    UtilCore.convertLocation(generator).getWorld().dropItemNaturally(UtilCore.convertLocation(generator), new ItemStack(Material.GOLD_INGOT));
                }
            }
        }, 32, 214));

        game.taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.getEggWarsMain(), () -> {
            if (game.state == GameState.RUNNING) {
                for (String generator : arenas.getStringList(game.arenaName + ".diamond")) {
                    UtilCore.convertLocation(generator).getWorld().dropItemNaturally(UtilCore.convertLocation(generator), new ItemStack(Material.DIAMOND));
                }
            }
        }, 53, 453));
    }
}
