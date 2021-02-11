package net.pistonmaster.eggwarsreloaded.game.listeners;

import net.pistonmaster.eggwarsreloaded.game.GameControl;
import net.pistonmaster.eggwarsreloaded.utils.UtilCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.*;

/*
public class ArenaProtect implements Listener {
    Set<String> placedBlocks = new HashSet<>();

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (GameControl.isInGame(player)) {


            // Game game = GameControl.getPlayerGame(event.getPlayer());

            placedBlocks.add(UtilCore.convertString(event.getBlockPlaced().getLocation()));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

    }

    private void isANonArenaBlock() {

    }
}
*/