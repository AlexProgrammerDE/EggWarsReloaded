package net.pistonmaster.eggwarsreloaded.game.listeners;


import net.pistonmaster.eggwarsreloaded.game.GameControl;
import net.pistonmaster.eggwarsreloaded.utils.UtilCore;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashSet;
import java.util.Set;

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

    private boolean isArenaBlock(Block block) {
        return !placedBlocks.contains(UtilCore.convertString(block.getLocation()));
    }
}
