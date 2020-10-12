package me.alexprogrammerde.EggWarsReloaded.game;

import me.alexprogrammerde.EggWarsReloaded.game.collection.GameState;
import me.alexprogrammerde.EggWarsReloaded.utils.ArenaManager;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class EggListener implements Listener {
    @EventHandler
    public void onEggClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (GameControl.isInGame(player)) {
            Game game = GameControl.getPlayerGame(player);

            if (game.state == GameState.RUNNING) {
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        if (event.getClickedBlock().getType() == Material.DRAGON_EGG) {
                            HashMap<Location, String> teamEggs = new HashMap<>();

                            for (String team : game.usedTeams) {
                                if (ArenaManager.isTeamRegistered(game.arenaName, team)) {
                                    teamEggs.put(UtilCore.convertLocation(ArenaManager.getArenas().getString("arenas." + game.arenaName + ".team." + team + ".egg")), team);
                                }
                            }

                            for (Location loc : teamEggs.keySet()) {
                                if (UtilCore.compareBlock(loc.getBlock(), event.getClickedBlock())) {
                                    // We know its a egg of a team
                                    game.eggDestroyed(teamEggs.get(event.getClickedBlock().getLocation()));

                                    event.setCancelled(true);

                                    event.getClickedBlock().setType(Material.AIR);

                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
