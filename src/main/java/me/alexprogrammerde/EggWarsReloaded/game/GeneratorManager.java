package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.game.collection.GameState;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class GeneratorManager {
    public GeneratorManager(Game game) {
        FileConfiguration arenas = EggWarsReloaded.get().getArenas();

        game.taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.get(), () -> {
            if (game.state == GameState.RUNNING) {
                for (String generator : arenas.getStringList(game.arenaName + ".iron")) {
                    UtilCore.convertLocation(generator).getWorld().dropItem(UtilCore.convertLocation(generator).add(0.5, 1.5, 0.5), new ItemStack(Material.IRON_INGOT)).setVelocity(new Vector(0, 0.2, 0));
                }
            }
        }, 11, 56));

        game.taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.get(), () -> {
            if (game.state == GameState.RUNNING) {
                for (String generator : arenas.getStringList(game.arenaName + ".gold")) {
                    UtilCore.convertLocation(generator).getWorld().dropItem(UtilCore.convertLocation(generator).add(0.5, 1.5, 0.5), new ItemStack(Material.GOLD_INGOT)).setVelocity(new Vector(0, 0.2, 0));
                }
            }
        }, 32, 214));

        game.taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.get(), () -> {
            if (game.state == GameState.RUNNING) {
                for (String generator : arenas.getStringList(game.arenaName + ".diamond")) {
                    UtilCore.convertLocation(generator).getWorld().dropItem(UtilCore.convertLocation(generator).add(0.5, 1.5, 0.5), new ItemStack(Material.DIAMOND)).setVelocity(new Vector(0, 0.2, 0));
                }
            }
        }, 53, 453));
    }
}
