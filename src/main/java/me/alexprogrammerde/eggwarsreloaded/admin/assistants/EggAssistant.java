package me.alexprogrammerde.eggwarsreloaded.admin.assistants;

import me.alexprogrammerde.eggwarsreloaded.EggWarsReloaded;
import me.alexprogrammerde.eggwarsreloaded.admin.guis.TeamUnderMenu;
import me.alexprogrammerde.eggwarsreloaded.game.collection.TeamColor;
import me.alexprogrammerde.eggwarsreloaded.utils.ArenaManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class EggAssistant implements Listener {
    public static final String prefix = ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "EggAssistant" + ChatColor.GOLD + "] ";
    private static final HashMap<Player, EggAssistant> assistants = new HashMap<>();
    private final Player player;
    private final String arenaName;
    private final TeamColor teamName;
    private final EggWarsReloaded plugin;

    public EggAssistant(Player player, String arenaName, TeamColor teamName, EggWarsReloaded plugin) {
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
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();

            if (player == this.player && isAdding(player)) {
                // Ok it's time to assist the player with the egg

                if (assistants.containsKey(player)) {
                    if (event.getClickedBlock().getType() == Material.DRAGON_EGG) {
                        event.setCancelled(true);
                        Location eggLocation = event.getClickedBlock().getLocation();

                        ArenaManager.setEgg(arenaName, teamName, eggLocation);

                        removePlayer(player);

                        player.sendMessage(prefix + "Set dragon egg of team " + teamName + " to: " + eggLocation.getWorld().getName() + " " + eggLocation.getBlockX() + " " + eggLocation.getBlockY() + " " + eggLocation.getBlockZ());

                        TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player, plugin);
                    }
                }
            }
        }
    }
}
