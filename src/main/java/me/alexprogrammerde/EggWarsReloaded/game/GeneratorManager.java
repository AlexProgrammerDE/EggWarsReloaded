package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class GeneratorManager {
    int[] tasks = new int[3];

    public GeneratorManager(GameLib gameLib) {
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

        tasks[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.getEggWarsMain(), new Runnable() {
            @Override
            public void run() {
                if (gameLib.isPlaying) {
                    for (String generator : arenas.getStringList("arenas." + gameLib.arenaName + ".iron")) {
                        String[] generatorsplit = generator.split(" ");

                        Location generatorlocation = new Location(Bukkit.getWorld(generatorsplit[0]), Double.parseDouble(generatorsplit[1]) + 0.5, Double.parseDouble(generatorsplit[2]) + 0.5, Double.parseDouble(generatorsplit[3]) + 0.5, 0, 90);

                        Bukkit.getWorld(generatorsplit[0]).dropItem(generatorlocation, new ItemStack(Material.IRON_INGOT));
                    }
                }
            }
        }, 20, 20);

        tasks[1] = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.getEggWarsMain(), new Runnable() {
            @Override
            public void run() {
                if (gameLib.isPlaying) {
                    for (String generator : arenas.getStringList("arenas." + gameLib.arenaName + ".gold")) {
                        String[] generatorsplit = generator.split(" ");

                        Location generatorlocation = new Location(Bukkit.getWorld(generatorsplit[0]), Double.parseDouble(generatorsplit[1]) + 0.5, Double.parseDouble(generatorsplit[2]) + 0.5, Double.parseDouble(generatorsplit[3]) + 0.5, 0, 90);

                        Bukkit.getWorld(generatorsplit[0]).dropItem(generatorlocation, new ItemStack(Material.GOLD_INGOT));
                    }
                }
            }
        }, 20, 60);

        tasks[2] = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsReloaded.getEggWarsMain(), new Runnable() {
            @Override
            public void run() {
                if (gameLib.isPlaying) {
                    for (String generator : arenas.getStringList("arenas." + gameLib.arenaName + ".diamond")) {
                        String[] generatorsplit = generator.split(" ");

                        Location generatorlocation = new Location(Bukkit.getWorld(generatorsplit[0]), Double.parseDouble(generatorsplit[1]) + 0.5, Double.parseDouble(generatorsplit[2]) + 0.5, Double.parseDouble(generatorsplit[3]) + 0.5, 0, 90);

                        Bukkit.getWorld(generatorsplit[0]).dropItem(generatorlocation, new ItemStack(Material.DIAMOND));
                    }
                }
            }
        }, 20, 100);
    }

    public void kill() {
        for (int id : tasks) {
            Bukkit.getScheduler().cancelTask(id);
        }
    }
}
