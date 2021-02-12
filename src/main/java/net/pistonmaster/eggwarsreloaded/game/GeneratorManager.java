package net.pistonmaster.eggwarsreloaded.game;

import com.cryptomorin.xseries.XMaterial;
import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import net.pistonmaster.eggwarsreloaded.game.collection.GameState;
import net.pistonmaster.eggwarsreloaded.utils.UtilCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class GeneratorManager {
    private final Map<Generator, Integer> hashMap = new EnumMap<>(Generator.class);
    private final Game game;
    private final EggWarsReloaded plugin;

    public GeneratorManager(Game game, EggWarsReloaded plugin) {
        this.game = game;
        this.plugin = plugin;

        for (Generator generator : Generator.values()) {
            makeGenerator(generator);
        }
    }

    public void newPeriod(Generator generator, int newPeriod) {
        int i = hashMap.get(generator);

        Bukkit.getScheduler().cancelTask(i);
        game.taskIds.remove(i);

        addID(generator, Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, getRun(generator), 0, newPeriod));
    }

    private void addID(Generator generator, int id) {
        game.taskIds.add(id);
        hashMap.put(generator, id);
    }

    private void makeGenerator(Generator generator) {
        addID(generator, Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, getRun(generator), generator.getDelay(), generator.getPeriod()));
    }

    private Runnable getRun(Generator generator) {
        FileConfiguration arenas = plugin.getArenaConfig();

        return () -> {
            if (game.getState() == GameState.RUNNING) {
                for (String str : arenas.getStringList(game.arenaName + "." + generator.toString())) {
                    Objects.requireNonNull(UtilCore.convertLocation(str).getWorld()).dropItem(UtilCore.convertLocation(str).add(0.5, 1, 0.5), new ItemStack(generator.getMaterial().parseMaterial())).setVelocity(new Vector(0, 0.2, 0));
                }
            }
        };
    }

    public enum Generator {
        IRON(XMaterial.IRON_INGOT, 20, 64),

        GOLD(XMaterial.GOLD_INGOT, 40, 160),

        DIAMOND(XMaterial.DIAMOND, 60, 320);

        private final XMaterial material;
        private final int delay;
        private final int period;

        Generator(XMaterial material, int delay, int period) {
            this.material = material;
            this.delay = delay;
            this.period = period;
        }

        public XMaterial getMaterial() {
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
}
