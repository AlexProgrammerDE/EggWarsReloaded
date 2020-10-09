package me.alexprogrammerde.EggWarsReloaded.admin;

import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class EggAssistant implements Listener {
    // TODO: Rework

    // String[0] == arena, String[1] == teamname, String[2] == state
    public static HashMap<Player, String[]> setup = new HashMap<>();

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();

            if (setup.containsKey(player) && !setup.get(player)[2].equals("none")) {
                // Ok it's time to assist the player with the egg

                if (setup.get(player)[2].equals("egg")) {
                    if (event.getClickedBlock().getType() == Material.DRAGON_EGG) {
                        event.setCancelled(true);
                        Location eggLocation = event.getClickedBlock().getLocation();

                        ArenaManager.setEgg(setup.get(player)[0], setup.get(player)[1], eggLocation);

                        setPlayer(player, setup.get(player)[0], setup.get(player)[1], "none");

                        player.sendMessage("[EggAssistant] Set dragon egg of team " + setup.get(player)[1] + " to: " + eggLocation.getWorld().getName() + " " + eggLocation.getBlockX() + " " + eggLocation.getBlockY() + " " + eggLocation.getBlockZ());
                    }
                }
            }
        }
    }

    public static void setPlayer(Player player, String arena, String teamName, String state) {
        String[] newarr = new String[3];
        newarr[0] = arena;
        newarr[1] = teamName;
        newarr[2] = state;

        setup.put(player, newarr);
    }
}
