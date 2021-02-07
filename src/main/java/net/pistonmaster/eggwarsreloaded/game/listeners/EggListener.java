package net.pistonmaster.eggwarsreloaded.game.listeners;

import net.pistonmaster.eggwarsreloaded.game.Game;
import net.pistonmaster.eggwarsreloaded.game.GameControl;
import net.pistonmaster.eggwarsreloaded.game.collection.GameState;
import net.pistonmaster.eggwarsreloaded.game.collection.TeamColor;
import net.pistonmaster.eggwarsreloaded.utils.ArenaManager;
import net.pistonmaster.eggwarsreloaded.utils.UtilCore;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Objects;

public class EggListener implements Listener {
    @EventHandler
    public void onEggClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (GameControl.isInGame(player)) {
            Game game = GameControl.getPlayerGame(player);

            if (game.getState() == GameState.RUNNING
                    && player.getGameMode() == GameMode.SURVIVAL
                    && (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                    && Objects.requireNonNull(event.getClickedBlock()).getType() == XMaterial.DRAGON_EGG.parseMaterial()) {
                HashMap<Location, TeamColor> teamEggs = new HashMap<>();

                for (TeamColor team : game.usedTeams) {
                    if (ArenaManager.isTeamRegistered(game.arenaName, team)) {
                        teamEggs.put(UtilCore.convertLocation(ArenaManager.getArenas().getString(game.arenaName + ".team." + team + ".egg")), team);
                    }
                }

                for (Location loc : teamEggs.keySet()) {
                    if (UtilCore.compareBlock(loc.getBlock(), event.getClickedBlock())) {
                        event.setCancelled(true);

                        if (game.matchmaker.getTeamOfPlayer(player) == teamEggs.get(event.getClickedBlock().getLocation())) {
                            player.sendMessage(ChatColor.RED + "You can't destroy your own egg!");
                        } else {
                            game.eggDestroyed(teamEggs.get(event.getClickedBlock().getLocation()));
                            event.getClickedBlock().setType(XMaterial.AIR.parseMaterial());
                        }

                        break;
                    }
                }
            }
        }
    }
}
