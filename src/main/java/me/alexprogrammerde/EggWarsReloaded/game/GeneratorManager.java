package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.game.collection.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class GeneratorManager {
    public GeneratorManager(Game game) {
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

        game.taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.getEggWarsMain(), () -> {
            if (game.state == GameState.RUNNING) {
                for (String generator : arenas.getStringList("arenas." + game.arenaName + ".iron")) {
                    String[] generatorsplit = generator.split(" ");

                    Location generatorlocation = new Location(Bukkit.getWorld(generatorsplit[0]), Double.parseDouble(generatorsplit[1]) + 0.5, Double.parseDouble(generatorsplit[2]) + 0.5, Double.parseDouble(generatorsplit[3]) + 0.5, 0, 90);

                    Bukkit.getWorld(generatorsplit[0]).dropItem(generatorlocation, new ItemStack(Material.IRON_INGOT));
                }
            }
        }, 20, 40));

        game.taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.getEggWarsMain(), () -> {
            if (game.state == GameState.RUNNING) {
                for (String generator : arenas.getStringList("arenas." + game.arenaName + ".gold")) {
                    String[] generatorsplit = generator.split(" ");

                    Location generatorlocation = new Location(Bukkit.getWorld(generatorsplit[0]), Double.parseDouble(generatorsplit[1]) + 0.5, Double.parseDouble(generatorsplit[2]) + 0.5, Double.parseDouble(generatorsplit[3]) + 0.5, 0, 90);

                    Bukkit.getWorld(generatorsplit[0]).dropItem(generatorlocation, new ItemStack(Material.GOLD_INGOT));
                }
            }
        }, 20, 200));

        game.taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.getEggWarsMain(), new Runnable() {
            @Override
            public void run() {
                if (game.state == GameState.RUNNING) {
                    for (String generator : arenas.getStringList("arenas." + game.arenaName + ".diamond")) {
                        String[] generatorsplit = generator.split(" ");

                        Location generatorlocation = new Location(Bukkit.getWorld(generatorsplit[0]), Double.parseDouble(generatorsplit[1]) + 0.5, Double.parseDouble(generatorsplit[2]) + 0.5, Double.parseDouble(generatorsplit[3]) + 0.5, 0, 90);

                        Bukkit.getWorld(generatorsplit[0]).dropItem(generatorlocation, new ItemStack(Material.DIAMOND));
                    }
                }
            }
        }, 20, 400));
    }
}
