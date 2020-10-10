package me.alexprogrammerde.EggWarsReloaded.admin;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.admin.guis.TeamUnderMenu;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
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
    private static final HashMap<Player, EggAssistant> assistants = new HashMap<>();
    Player player;
    String arenaName;
    String teamName;

    public EggAssistant(Player player, String arenaName, String teamName) {
        assistants.put(player, this);

        this.player = player;
        this.arenaName = arenaName;
        this.teamName = teamName;

        Bukkit.getServer().getPluginManager().registerEvents(this, EggWarsReloaded.getEggWarsMain());
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();

            if (player == this.player) {
                // Ok it's time to assist the player with the egg

                if (assistants.containsKey(player)) {
                    if (event.getClickedBlock().getType() == Material.DRAGON_EGG) {
                        event.setCancelled(true);
                        Location eggLocation = event.getClickedBlock().getLocation();

                        ArenaManager.setEgg(arenaName, teamName, eggLocation);

                        removePlayer(player);

                        player.sendMessage("[EggAssistant] Set dragon egg of team " + teamName + " to: " + eggLocation.getWorld().getName() + " " + eggLocation.getBlockX() + " " + eggLocation.getBlockY() + " " + eggLocation.getBlockZ());

                        TeamUnderMenu.setupTeamUnderMenu(arenaName, teamName, player);
                    }
                }
            }
        }
    }

    public static boolean isAdding(Player player) {
        return assistants.containsKey(player);
    }

    public static void removePlayer(Player player) {
        HandlerList.unregisterAll(assistants.get(player));

        assistants.remove(player);
    }
}
