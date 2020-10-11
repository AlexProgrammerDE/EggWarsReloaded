package me.alexprogrammerde.EggWarsReloaded.admin.assistants;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.admin.guis.TeamUnderMenu;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.HashMap;

public class ShopAssistant implements Listener {
    private static final HashMap<Player, ShopAssistant> assistants = new HashMap<>();
    Player player;
    String arenaName;
    String teamName;

    public ShopAssistant(Player player, String arenaName, String teamName) {
        assistants.put(player, this);

        this.player = player;
        this.arenaName = arenaName;
        this.teamName = teamName;

        Bukkit.getServer().getPluginManager().registerEvents(this, EggWarsReloaded.getEggWarsMain());
    }

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

        if (player == this.player && isAdding(player)) {
            Location villagerLocation = armorstand.getLocation();
            armorstand.remove();

            Villager shop = (Villager) player.getWorld().spawnEntity(villagerLocation, EntityType.VILLAGER);

            shop.setAI(false);
            shop.setAware(false);
            shop.setCollidable(false);
            shop.setInvulnerable(true);

            ArenaManager.setShop(arenaName, teamName, shop.getUniqueId().toString(), shop.getLocation());

            player.sendMessage("[ShopAssistant] Set shop of team " + teamName + " to: " + villagerLocation.getWorld().getName() + " " + villagerLocation.getBlockX() + " " + villagerLocation.getBlockY() + " " + villagerLocation.getBlockZ());

            TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);
        }

        removePlayer(player);
    }

    public static boolean isAdding(Player player) {
        return assistants.containsKey(player);
    }

    public static void removePlayer(Player player) {
        HandlerList.unregisterAll(assistants.get(player));

        assistants.remove(player);
    }
}
