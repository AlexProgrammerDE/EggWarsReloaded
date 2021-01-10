package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.game.collection.GameState;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class GeneratorManager {
    public HashMap<Generator, Integer> hashMap = new HashMap<>();
    private final Game game;
    private final EggWarsReloaded plugin;
    
    public GeneratorManager(Game game, EggWarsReloaded plugin) {
        this.game = game;
        this.plugin = plugin;
        
        for (Generator generator : Generator.values()) {
            makeGenerator(generator);
        }
    }

    public enum Generator {
        IRON(Material.IRON_INGOT, 20, 64),

        GOLD(Material.GOLD_INGOT, 40, 160),

        DIAMOND(Material.DIAMOND, 60, 320);

        private final Material material;
        private final int delay;
        private final int period;

        Generator(Material material, int delay, int period) {
            this.material = material;
            this.delay = delay;
            this.period = period;
        }

        public Material getMaterial() {
            return material;
        }

        public int getDelay() {
            return delay;
        }

        public int getPeriod() {
            return period;
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public void newPeriod(Generator generator, int newPeriod) {
        int i = hashMap.get(generator);

        Bukkit.getScheduler().cancelTask(i);
        game.taskIds.remove(i);

        FileConfiguration arenas = plugin.getArenas();

        addID(generator, Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (game.state == GameState.RUNNING) {
                for (String str : arenas.getStringList(game.arenaName + "." + generator.toString())) {
                    UtilCore.convertLocation(str).getWorld().dropItem(UtilCore.convertLocation(str).add(0.5, 1, 0.5), new ItemStack(generator.getMaterial())).setVelocity(new Vector(0, 0.2, 0));
                }
            }
        }, 0, newPeriod));
    }

    private void addID(Generator generator, int id) {
        game.taskIds.add(id);
        hashMap.put(generator, id);
    }

    private void makeGenerator(Generator generator) {
        FileConfiguration arenas = plugin.getArenas();

        addID(generator, Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (game.state == GameState.RUNNING) {
                for (String str : arenas.getStringList(game.arenaName + "." + generator.toString())) {
                    UtilCore.convertLocation(str).getWorld().dropItem(UtilCore.convertLocation(str).add(0.5, 1, 0.5), new ItemStack(generator.getMaterial())).setVelocity(new Vector(0, 0.2, 0));
                }
            }
        }, generator.getDelay(), generator.getPeriod()));
    }
}
