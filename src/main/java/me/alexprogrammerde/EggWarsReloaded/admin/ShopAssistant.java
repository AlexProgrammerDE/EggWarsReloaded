package me.alexprogrammerde.EggWarsReloaded.admin;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.HashMap;

public class ShopAssistant implements Listener {
    public static HashMap<Player, Boolean> shouldCreateShop = new HashMap<>();

    // [0] == arenaname, [1] == teamname
    public static HashMap<Player, String[]> playerdata = new HashMap<>();

    @EventHandler
    public void onArmorStandClick(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        if (event.getRightClicked() instanceof ArmorStand) {
            ArmorStandInteract(player, event.getRightClicked());
        }
    }

    @EventHandler
    public void onArmorStandAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            if (event.getEntity() instanceof ArmorStand) {
                ArmorStandInteract(player, event.getEntity());
            }
        }
    }

    public void ArmorStandInteract(Player player, Entity armorstand) {
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();

        if (shouldCreateShop.containsKey(player)) {
            if (shouldCreateShop.get(player)) {
                Location villagerlocation = armorstand.getLocation();
                armorstand.remove();

                Villager shop = (Villager) player.getWorld().spawnEntity(villagerlocation, EntityType.VILLAGER);

                shop.setAI(false);
                shop.setAware(false);
                shop.setCollidable(false);
                shop.setInvulnerable(true);

                ArenaManager.setShop(playerdata.get(player)[0], playerdata.get(player)[1], shop.getUniqueId().toString(), shop.getLocation());

                player.sendMessage("[ShopAssistant] Set shop of team " + playerdata.get(player)[1] + " to: " + villagerlocation.getWorld().getName() + " " + villagerlocation.getBlockX() + " " + villagerlocation.getBlockY() + " " + villagerlocation.getBlockZ());
            }

            shouldCreateShop.remove(player);
        }
    }
}
