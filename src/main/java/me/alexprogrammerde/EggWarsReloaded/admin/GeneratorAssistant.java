package me.alexprogrammerde.EggWarsReloaded.admin;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GeneratorAssistant implements Listener {
    public static HashMap<Player, Boolean> shouldTouchBlock = new HashMap<>();

    // [0] == arenaname, [1] == teamname
    public static HashMap<Player, String> playerdata = new HashMap<>();

    @EventHandler
    public void onBlockInteract(BlockBreakEvent event) {
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location blocklocation = block.getLocation();
        String blocklocationstring = blocklocation.getWorld().getName() + " " + blocklocation.getX() + " " + blocklocation.getY() + " " + blocklocation.getZ();

        if (shouldTouchBlock.containsKey(player)) {
            String arenaname = playerdata.get(player);

            if (block.getType() == Material.IRON_BLOCK && shouldTouchBlock.get(player)) {
                event.setCancelled(true);
                List<String> generators = arenas.getStringList("arenas." + arenaname + ".iron");

                if (generators.contains(blocklocationstring)) {
                    player.sendMessage("This block is already added!");
                } else {
                    generators.add(blocklocationstring);
                    arenas.set("arenas." + arenaname + ".iron", generators);
                    player.sendMessage("Added block: " + blocklocationstring);
                }
            } else if (block.getType() == Material.GOLD_BLOCK && shouldTouchBlock.get(player)) {
                event.setCancelled(true);
                List<String> generators = arenas.getStringList("arenas." + arenaname + ".gold");

                if (generators.contains(blocklocationstring)) {
                    player.sendMessage("This block is already added!");
                } else {
                    generators.add(blocklocationstring);
                    arenas.set("arenas." + arenaname + ".gold", generators);
                    player.sendMessage("Added block: " + blocklocationstring);
                }
            } else if (block.getType() == Material.DIAMOND_BLOCK && shouldTouchBlock.get(player)) {
                event.setCancelled(true);
                List<String> generators = arenas.getStringList("arenas." + arenaname + ".diamond");

                if (generators.contains(blocklocationstring)) {
                    player.sendMessage("This block is already added!");
                } else {
                    generators.add(blocklocationstring);
                    arenas.set("arenas." + arenaname + ".diamond", generators);
                    player.sendMessage("Added block: " + blocklocationstring);
                }
            }

            try {
                EggWarsReloaded.getEggWarsMain().getArenas().save(EggWarsReloaded.getEggWarsMain().getArenasFile());
            } catch (IOException e) {
                e.printStackTrace();
            }

            EggWarsReloaded.getEggWarsMain().reloadArenas();
        }
    }
}
