package net.pistonmaster.eggwarsreloaded.admin.assistants;

import net.md_5.bungee.api.ChatColor;
import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import net.pistonmaster.eggwarsreloaded.admin.guis.TeamUnderMenu;
import net.pistonmaster.eggwarsreloaded.game.collection.TeamColor;
import net.pistonmaster.eggwarsreloaded.utils.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.HashMap;

public class ShopAssistant implements Listener {
    public static final String PREFIX = ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "EggAssistant" + ChatColor.GOLD + "] ";
    private static final HashMap<Player, ShopAssistant> assistants = new HashMap<>();
    private final Player player;
    private final String arenaName;
    private final TeamColor teamName;
    private final EggWarsReloaded plugin;

    public ShopAssistant(Player player, String arenaName, TeamColor teamName, EggWarsReloaded plugin) {
        assistants.put(player, this);

        this.player = player;
        this.arenaName = arenaName;
        this.teamName = teamName;
        this.plugin = plugin;

        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static boolean isAdding(Player player) {
        return assistants.containsKey(player);
    }

    public static void removePlayer(Player player) {
        HandlerList.unregisterAll(assistants.get(player));

        assistants.remove(player);
    }

    @EventHandler
    public void onArmorStandClick(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        if (event.getRightClicked() instanceof ArmorStand) {
            armorStandInteract(player, event.getRightClicked());
        }
    }

    @EventHandler
    public void onArmorStandAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            if (event.getEntity() instanceof ArmorStand) {
                armorStandInteract(player, event.getEntity());
            }
        }
    }

    public void armorStandInteract(Player player, Entity armorstand) {
        if (player == this.player && isAdding(player)) {
            Location villagerLocation = armorstand.getLocation();
            armorstand.remove();

            Villager shop = (Villager) player.getWorld().spawnEntity(villagerLocation, EntityType.VILLAGER);

            shop.setAI(false);
            shop.setAware(false);
            shop.setCollidable(false);
            shop.setInvulnerable(true);

            shop.setCustomName(ChatColor.GOLD + "Shop");
            shop.setCustomNameVisible(true);

            ArenaManager.setShop(arenaName, teamName, shop.getUniqueId().toString(), shop.getLocation());

            player.sendMessage(PREFIX + "Set shop of team " + teamName + " to: " + villagerLocation.getWorld().getName() + " " + villagerLocation.getBlockX() + " " + villagerLocation.getBlockY() + " " + villagerLocation.getBlockZ());

            TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);
        }

        removePlayer(player);
    }
}
