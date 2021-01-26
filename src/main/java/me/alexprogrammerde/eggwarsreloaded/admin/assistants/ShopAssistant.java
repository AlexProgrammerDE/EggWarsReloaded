package me.alexprogrammerde.eggwarsreloaded.admin.assistants;

import me.alexprogrammerde.eggwarsreloaded.EggWarsReloaded;
import me.alexprogrammerde.eggwarsreloaded.admin.guis.TeamUnderMenu;
import me.alexprogrammerde.eggwarsreloaded.game.collection.TeamColor;
import me.alexprogrammerde.eggwarsreloaded.utils.ArenaManager;
import net.md_5.bungee.api.ChatColor;
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
    private static final HashMap<Player, ShopAssistant> assistants = new HashMap<>();
    private final Player player;
    private final String arenaName;
    private final TeamColor teamName;
    public static final String prefix = ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "EggAssistant" + ChatColor.GOLD + "] ";
    private final EggWarsReloaded plugin;

    public ShopAssistant(Player player, String arenaName, TeamColor teamName, EggWarsReloaded plugin) {
        assistants.put(player, this);

        this.player = player;
        this.arenaName = arenaName;
        this.teamName = teamName;
        this.plugin = plugin;

        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
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

            ArenaManager.setShop(arenaName, teamName, shop.getUniqueId().toString(), shop.getLocation());

            player.sendMessage(prefix + "Set shop of team " + teamName + " to: " + villagerLocation.getWorld().getName() + " " + villagerLocation.getBlockX() + " " + villagerLocation.getBlockY() + " " + villagerLocation.getBlockZ());

            TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);
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
