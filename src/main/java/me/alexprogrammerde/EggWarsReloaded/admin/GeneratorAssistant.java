package me.alexprogrammerde.EggWarsReloaded.admin;

import me.alexprogrammerde.EggWarsReloaded.EggWarsReloaded;
import me.alexprogrammerde.EggWarsReloaded.utils.UtilCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GeneratorAssistant implements Listener {
    private static final HashMap<Player, GeneratorAssistant> assistants = new HashMap<>();
    Player player;
    String arenaName;

    public GeneratorAssistant(Player player, String arenaName) {
        assistants.put(player, this);

        this.player = player;
        this.arenaName = arenaName;

        Bukkit.getServer().getPluginManager().registerEvents(this, EggWarsReloaded.getEggWarsMain());
    }

    @EventHandler
    public void onBlockInteract(BlockBreakEvent event) {
        FileConfiguration arenas = EggWarsReloaded.getEggWarsMain().getArenas();
        Player player = event.getPlayer();
        Block block = event.getBlock();
        String blockLocationString = UtilCore.convertString(block.getLocation());

        if (player == this.player && isAdding(player)) {
            if (block.getType() == Material.IRON_BLOCK) {
                event.setCancelled(true);
                List<String> generators = arenas.getStringList("arenas." + arenaName + ".iron");

                if (generators.contains(blockLocationString)) {
                    player.sendMessage("This block is already added!");
                } else {
                    generators.add(blockLocationString);
                    arenas.set("arenas." + arenaName + ".iron", generators);
                    player.sendMessage("Added block: " + blockLocationString);
                }
            } else if (block.getType() == Material.GOLD_BLOCK) {
                event.setCancelled(true);
                List<String> generators = arenas.getStringList("arenas." + arenaName + ".gold");

                if (generators.contains(blockLocationString)) {
                    player.sendMessage("This block is already added!");
                } else {
                    generators.add(blockLocationString);
                    arenas.set("arenas." + arenaName + ".gold", generators);
                    player.sendMessage("Added block: " + blockLocationString);
                }
            } else if (block.getType() == Material.DIAMOND_BLOCK) {
                event.setCancelled(true);
                List<String> generators = arenas.getStringList("arenas." + arenaName + ".diamond");

                if (generators.contains(blockLocationString)) {
                    player.sendMessage("This block is already added!");
                } else {
                    generators.add(blockLocationString);
                    arenas.set("arenas." + arenaName + ".diamond", generators);
                    player.sendMessage("Added block: " + blockLocationString);
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

    public static boolean isAdding(Player player) {
        return assistants.containsKey(player);
    }

    public static void removePlayer(Player player) {
        HandlerList.unregisterAll(assistants.get(player));

        assistants.remove(player);
    }
}
