package net.pistonmaster.eggwarsreloaded.utils.world;

import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;

/**
 * Stolen from https://github.com/lukasvdgaag/SkyWarsReloaded ;)
 * Thanks to the GCNT Devs for creating this.
 */
public interface WorldManager {

    World createEmptyWorld(String name, World.Environment environment);

    boolean loadWorld(String worldName, World.Environment environment);

    void unloadWorld(String world, boolean save, Location end);

    void copyWorld(File source, File target);

    void deleteWorld(String name, boolean removeFile, Location end);

    void deleteWorld(File file);
}
