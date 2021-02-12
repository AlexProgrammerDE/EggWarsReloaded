package net.pistonmaster.eggwarsreloaded.admin.assistants;

import com.cryptomorin.xseries.XMaterial;
import net.md_5.bungee.api.ChatColor;
import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import net.pistonmaster.eggwarsreloaded.utils.UtilCore;
import org.bukkit.Bukkit;
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
    public static final String PREFIX = ChatColor.GOLD + "[" + ChatColor.DARK_PURPLE + "GeneratorAssistant" + ChatColor.GOLD + "] ";
    private static final HashMap<Player, GeneratorAssistant> assistants = new HashMap<>();
    private final Player player;
    private final String arenaName;
    private final EggWarsReloaded plugin;

    public GeneratorAssistant(Player player, String arenaName, EggWarsReloaded plugin) {
        assistants.put(player, this);

        this.player = player;
        this.arenaName = arenaName;
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
    public void onBlockInteract(BlockBreakEvent event) {
        FileConfiguration arenas = plugin.getArenaConfig();
        Player player = event.getPlayer();
        Block block = event.getBlock();
        String blockLocationString = UtilCore.convertString(block.getLocation());

        if (player == this.player && isAdding(player)) {
            if (block.getType() == XMaterial.IRON_BLOCK.parseMaterial()) {
                event.setCancelled(true);
                List<String> generators = arenas.getStringList(arenaName + ".iron");

                if (generators.contains(blockLocationString)) {
                    player.sendMessage(PREFIX + "This block is already added!");
                } else {
                    generators.add(blockLocationString);
                    arenas.set(arenaName + ".iron", generators);
                    player.sendMessage(PREFIX + "Added iron block: " + blockLocationString);
                }
            } else if (block.getType() == XMaterial.GOLD_BLOCK.parseMaterial()) {
                event.setCancelled(true);
                List<String> generators = arenas.getStringList(arenaName + ".gold");

                if (generators.contains(blockLocationString)) {
                    player.sendMessage(PREFIX + "This block is already added!");
                } else {
                    generators.add(blockLocationString);
                    arenas.set(arenaName + ".gold", generators);
                    player.sendMessage(PREFIX + "Added gold block: " + blockLocationString);
                }
            } else if (block.getType() == XMaterial.DIAMOND_BLOCK.parseMaterial()) {
                event.setCancelled(true);
                List<String> generators = arenas.getStringList(arenaName + ".diamond");

                if (generators.contains(blockLocationString)) {
                    player.sendMessage(PREFIX + "This block is already added!");
                } else {
                    generators.add(blockLocationString);
                    arenas.set(arenaName + ".diamond", generators);
                    player.sendMessage(PREFIX + "Added diamond block: " + blockLocationString);
                }
            }

            try {
                plugin.getArenaConfig().save(plugin.getArenasFile());
            } catch (IOException e) {
                e.printStackTrace();
            }

            plugin.loadConfig();
        }
    }
}
