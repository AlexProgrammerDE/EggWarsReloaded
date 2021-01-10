package me.alexprogrammerde.eggwarsreloaded.utils.world;

import com.google.common.collect.Lists;
import me.alexprogrammerde.eggwarsreloaded.EggWarsReloaded;
import org.bukkit.*;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

/**
 * Stolen from https://github.com/lukasvdgaag/SkyWarsReloaded ;)
 * Thanks to the GCNT Devs for creating this.
 */
public class FileWorldManager implements WorldManager {
    private final EggWarsReloaded plugin;

    public FileWorldManager(EggWarsReloaded plugin) {
        this.plugin = plugin;
    }

    public World createEmptyWorld(String name, Environment environment) {
        if (Bukkit.getWorld(name) == null) {
            loadWorld(name, environment);
            return Bukkit.getWorld(name);
        } else {
            return Bukkit.getWorld(name);
        }
    }

    public boolean loadWorld(String worldName, Environment environment) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.environment(environment);
        worldCreator.generateStructures(false);
        worldCreator.generator(new ChunkGenerator() {
            public final ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid chunkGenerator) {
                ChunkGenerator.ChunkData chunkData = createChunkData(world);
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        chunkGenerator.setBiome(i, j, org.bukkit.block.Biome.valueOf("THE_VOID"));
                    }
                }
                return chunkData;
            }
        });

        World world = worldCreator.createWorld();

        world.setDifficulty(Difficulty.NORMAL);
        world.setSpawnFlags(true, true);
        world.setPVP(true);
        world.setStorm(false);
        world.setThundering(false);
        world.setWeatherDuration(Integer.MAX_VALUE);
        world.setKeepSpawnInMemory(false);
        world.setTicksPerAnimalSpawns(1);
        world.setTicksPerMonsterSpawns(1);
        world.setAutoSave(false);

        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("mobGriefing", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("showDeathMessages", "false");
        world.setGameRuleValue("announceAdvancements", "false");

        boolean loaded = false;
        for (World w : plugin.getServer().getWorlds()) {
            if (w.getName().equals(world.getName())) {
                loaded = true;
                break;
            }
        }

        return loaded;
    }

    public void unloadWorld(String w, boolean save, Location end) {
        World world = Bukkit.getServer().getWorld(w);

        if (world != null) {
            for (Player p : world.getPlayers()) {
                p.teleport(end);
            }
            Bukkit.getServer().unloadWorld(world, save);
        }
    }

    public void copyWorld(File source, File target) {
        try {
            List<String> ignore = Lists.newArrayList("uid.dat", "session.dat", "session.lock");
            if (!ignore.contains(source.getName())) {
                if (source.isDirectory()) {
                    if ((!target.exists()) &&
                            (target.mkdirs())) {
                        String[] files = source.list();
                        if (files != null) {
                            for (String file : files) {
                                File srcFile = new File(source, file);
                                File destFile = new File(target, file);
                                copyWorld(srcFile, destFile);
                            }
                        }
                    }
                } else {
                    OutputStream out;
                    try (InputStream in = new FileInputStream(source)) {
                        out = new FileOutputStream(target);
                        byte[] buffer = new byte['Ѐ'];
                        int length;
                        while ((length = in.read(buffer)) > 0)
                            out.write(buffer, 0, length);
                    }
                    out.close();
                }
            }
        } catch (FileNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to copy world as required! - file not found");
            e.printStackTrace();
        } catch (IOException e) {
            plugin.getLogger().info("Failed to copy world as required!");
            e.printStackTrace();
        }
    }

    public void deleteWorld(String name, boolean removeFile, Location end) {
        unloadWorld(name, false, end);
        File target = new File(Bukkit.getServer().getWorldContainer().getAbsolutePath(), name);
        deleteWorld(target);
    }

    public void deleteWorld(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteWorld(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }

        path.delete();
    }
}
