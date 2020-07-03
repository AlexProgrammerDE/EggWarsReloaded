package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class GeneratorManager {

    public GeneratorManager(Game game) {
        FileConfiguration arenas = EggWarsMain.getEggWarsMain().getArenas();

        game.taskID[1] = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsMain.getEggWarsMain(), new Runnable() {
            @Override
            public void run() {
                if (game.isPlaying) {
                    for (String generator : arenas.getStringList("arenas." + game.arenaname + ".iron")) {
                        String[] generatorsplit = generator.split(" ");

                        Location generatorlocation = new Location(Bukkit.getWorld(generatorsplit[0]), Double.parseDouble(generatorsplit[1]), Double.parseDouble(generatorsplit[2]), Double.parseDouble(generatorsplit[3]));

                        Bukkit.getWorld(generatorsplit[0]).dropItem(generatorlocation, new ItemStack(Material.IRON_INGOT));
                    }
                }
            }
        }, 20, 20);

        game.taskID[2] = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsMain.getEggWarsMain(), new Runnable() {
            @Override
            public void run() {
                if (game.isPlaying) {
                    for (String generator : arenas.getStringList("arenas." + game.arenaname + ".gold")) {
                        String[] generatorsplit = generator.split(" ");

                        Location generatorlocation = new Location(Bukkit.getWorld(generatorsplit[0]), Double.parseDouble(generatorsplit[1]), Double.parseDouble(generatorsplit[2]), Double.parseDouble(generatorsplit[3]));

                        Bukkit.getWorld(generatorsplit[0]).dropItem(generatorlocation, new ItemStack(Material.GOLD_INGOT));
                    }
                }
            }
        }, 20, 60);

        game.taskID[3] = Bukkit.getScheduler().scheduleSyncRepeatingTask(EggWarsMain.getEggWarsMain(), new Runnable() {
            @Override
            public void run() {
                if (game.isPlaying) {
                    for (String generator : arenas.getStringList("arenas." + game.arenaname + ".diamond")) {
                        String[] generatorsplit = generator.split(" ");

                        Location generatorlocation = new Location(Bukkit.getWorld(generatorsplit[0]), Double.parseDouble(generatorsplit[1]), Double.parseDouble(generatorsplit[2]), Double.parseDouble(generatorsplit[3]));

                        Bukkit.getWorld(generatorsplit[0]).dropItem(generatorlocation, new ItemStack(Material.DIAMOND));
                    }
                }
            }
        }, 20, 100);
    }
}
