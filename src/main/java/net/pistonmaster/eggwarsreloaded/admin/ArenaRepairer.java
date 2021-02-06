package net.pistonmaster.eggwarsreloaded.admin;

import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import net.pistonmaster.eggwarsreloaded.game.collection.TeamColor;
import net.pistonmaster.eggwarsreloaded.utils.ArenaManager;
import net.pistonmaster.eggwarsreloaded.utils.UtilCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class ArenaRepairer implements Listener {
    private final EggWarsReloaded plugin;

    public ArenaRepairer(EggWarsReloaded plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVillagerKill(EntityDeathEvent event) {
        if (event.getEntity().getType() == EntityType.VILLAGER) {
            Villager villager = (Villager) event.getEntity();
            FileConfiguration arenas = plugin.getArenaConfig();

            for (String arenaName : arenas.getKeys(false)) {
                for (String team : arenas.getConfigurationSection(arenaName + ".team").getKeys(false)) {
                    if (arenas.contains(arenaName + ".team." + team + ".shop")) {
                        String uuid = arenas.getString(arenaName + ".team." + team + ".shop.uuid");
                        Location shopLocation = UtilCore.convertLocation(arenas.getString(arenaName + ".team." + team + ".shop.location"));

                        if (villager.getUniqueId().equals(UUID.fromString(uuid))) {
                            Villager newVillager = (Villager) Bukkit.getWorld(shopLocation.getWorld().getName()).spawnEntity(shopLocation, EntityType.VILLAGER);

                            newVillager.setAI(false);
                            newVillager.setAware(false);
                            newVillager.setCollidable(false);
                            newVillager.setInvulnerable(true);

                            ArenaManager.setShop(arenaName, TeamColor.fromString(team), newVillager.getUniqueId().toString(), villager.getLocation());

                            plugin.getLogger().info("Respawned shop. If you wish to delete it use the edit menu.");
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onTouch(PlayerInteractEvent event) {
        if (event.hasBlock()) {
            Block block = event.getClickedBlock();

            if (block != null && block.getType() == Material.DRAGON_EGG) {
                FileConfiguration arenas = plugin.getArenaConfig();

                for (String arenaName : arenas.getKeys(false)) {
                    for (String team : arenas.getConfigurationSection(arenaName + ".team").getKeys(false)) {
                        if (arenas.contains(arenaName + ".team." + team + ".egg")) {
                            Location eggLocation = UtilCore.convertLocation(arenas.getString(arenaName + ".team." + team + ".egg"));

                            if (block.getLocation().equals(eggLocation)) {
                                event.setCancelled(true);

                                plugin.getLogger().info("Respawned shop. If you wish to delete it use the edit menu.");
                            }
                        }
                    }
                }
            }
        }
    }
}
