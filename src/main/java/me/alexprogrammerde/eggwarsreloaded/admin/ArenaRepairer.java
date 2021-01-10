package me.alexprogrammerde.eggwarsreloaded.admin;

import me.alexprogrammerde.eggwarsreloaded.EggWarsReloaded;
import me.alexprogrammerde.eggwarsreloaded.game.collection.TeamColor;
import me.alexprogrammerde.eggwarsreloaded.utils.ArenaManager;
import me.alexprogrammerde.eggwarsreloaded.utils.UtilCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

public class ArenaRepairer implements Listener {
    private final EggWarsReloaded plugin;

    public ArenaRepairer(EggWarsReloaded plugin) {
        this.plugin = plugin;
    }

    // TODO Add Egg too (low prio)
    @EventHandler
    public void onVillagerKill(EntityDeathEvent event) {
        FileConfiguration arenas = plugin.getArenaConfig();

        if (event.getEntity().getType() == EntityType.VILLAGER) {
            Villager villager = (Villager) event.getEntity();

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
}
