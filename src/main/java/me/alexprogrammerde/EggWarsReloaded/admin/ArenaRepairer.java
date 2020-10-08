package me.alexprogrammerde.EggWarsReloaded.admin;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
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

    // TODO Add Egg too (low prio)
    @EventHandler(priority = EventPriority.L)
    public void onVillagerKill(EntityDeathEvent event) {
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

        if (event.getEntity().getType() == EntityType.VILLAGER) {
            Villager villager = (Villager) event.getEntity();
            EggWarsReloaded.getEggWarsMain().getLogger().info(villager.toString());

            if (arenas.contains("arenas")) {
                for (String arenaname : arenas.getConfigurationSection("arenas").getKeys(false)) {
                    for (String team : arenas.getConfigurationSection("arenas." + arenaname + ".team").getKeys(false)) {
                        if (arenas.contains("arenas." + arenaname + ".team." + team + ".shop")) {
                            String uuid = arenas.getString("arenas." + arenaname + ".team." + team + ".shop.uuid");
                            Location shoplocation = arenas.getLocation("arenas." + arenaname + ".team." + team + ".shop.location");

                            if (villager.getUniqueId().equals(UUID.fromString(uuid))) {
                                Villager newvillager = (Villager) Bukkit.getWorld(shoplocation.getWorld().getName()).spawnEntity(shoplocation, EntityType.VILLAGER);

                                newvillager.setAI(false);
                                newvillager.setAware(false);
                                newvillager.setCollidable(false);
                                newvillager.setInvulnerable(true);

                                ArenaManager.setShop(arenaname, team, newvillager.getUniqueId().toString(), villager.getLocation());

                                EggWarsReloaded.getEggWarsMain().getLogger().info("Respawned shop. If you wish to delete it use the edit menu.");
                            }
                        }
                    }
                }
            }
        }
    }
}
