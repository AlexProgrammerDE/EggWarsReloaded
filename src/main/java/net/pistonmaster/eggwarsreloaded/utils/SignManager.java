package net.pistonmaster.eggwarsreloaded.utils;

import net.pistonmaster.eggwarsreloaded.EggWarsReloaded;
import net.pistonmaster.eggwarsreloaded.game.Game;
import net.pistonmaster.eggwarsreloaded.game.GameControl;
import net.pistonmaster.eggwarsreloaded.game.collection.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SignManager implements Listener {
    private final EggWarsReloaded plugin;
    private final Map<Location, String> signs = new HashMap<>();

    public SignManager(EggWarsReloaded plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::updateSigns, 0, 10);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!Objects.requireNonNull(event.getClickedBlock()).getType().name().contains("WALL_SIGN")) return;

            Game game = GameControl.getGame(signs.get(Objects.requireNonNull(event.getClickedBlock()).getLocation()));

            if (game == null) return;

            if (game.getState() == GameState.LOBBY) {
                GameControl.addPlayer(event.getPlayer(), game);
            } else {
                event.getPlayer().sendMessage(ChatColor.RED + "This game is already running!");
            }
        }
    }

    @EventHandler
    public void onSignCreate(SignChangeEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("eggwarsreloaded.createsigns")) return;

        String[] lines = event.getLines();

        if (!event.getBlock().getType().name().contains("WALL_SIGN")) return;

        if (lines.length < 2) return;

        if (lines[0].equalsIgnoreCase("[EggWars]")) {
            if (GameControl.getRegisteredArenas().contains(lines[1])) {
                signs.put(event.getBlock().getLocation(), lines[1]);
                List<String> list = plugin.getSigns().getStringList(lines[1]);

                list.add(UtilCore.convertString(event.getBlock().getLocation()));

                plugin.getSigns().set(lines[1], list);

                try {
                    plugin.getSigns().save(plugin.getSignsFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                player.sendMessage(ChatColor.RED + "You have a invalid arena name!");
            }
        }
    }

    @EventHandler
    public void onSignDestroy(BlockBreakEvent event) {
        if (signs.containsKey(event.getBlock().getLocation())) {
            removeSign(event.getBlock());
        }
    }

    public void detectSigns() {
        FileConfiguration config = plugin.getSigns();
        signs.clear();

        for (String game : config.getKeys(false)) {
            for (String sign : config.getStringList(game)) {
                if (UtilCore.convertLocation(sign).getBlock().getType().name().contains("WALL_SIGN")) {
                    signs.put(UtilCore.convertLocation(sign), game);
                }
            }
        }
    }

    public void updateSigns() {
        for (Map.Entry<Location, String> entry : signs.entrySet()) {
            Block block = entry.getKey().getBlock();

            if (!block.getType().name().contains("WALL_SIGN")) {
                removeSign(block);
                continue;
            }

            Sign sign = (Sign) block.getState();
            Game game = GameControl.getGame(entry.getValue());

            if (game == null) {
                notFound(sign, entry.getValue());
                continue;
            }

            sign.setLine(0, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "EggWars");
            sign.setLine(1, ChatColor.GOLD + game.getArenaName());
            sign.setLine(2, ChatColor.WHITE + "" + game.inGamePlayers.size() + ChatColor.GOLD + "" + ChatColor.BOLD + "/" + ChatColor.RESET + "" + ChatColor.WHITE +  "" + game.maxPlayers);
            sign.setLine(3, ChatColor.GREEN + game.getState().name());

            sign.update();

            getBack((WallSign) sign.getBlock().getBlockData(), sign.getBlock()).setType(Material.WHITE_STAINED_GLASS);
        }
    }

    public void notFound(Sign sign, String arenaName) {
        sign.setLine(0, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "EggWars");
        sign.setLine(1, ChatColor.GOLD + arenaName);
        sign.setLine(2, ChatColor.RED + "Game");
        sign.setLine(3, ChatColor.RED + "not ready!");

        sign.update();

        getBack((WallSign) sign.getBlock().getBlockData(), sign.getBlock()).setType(Material.RED_STAINED_GLASS);
    }

    private Block getBack(WallSign wallSign, Block sign) {
        return sign.getRelative(wallSign.getFacing().getOppositeFace(), 1);
    }

    private void removeSign(Block sign) {
        Location loc = sign.getLocation();
        String arenaName = signs.get(sign.getLocation());

        signs.remove(loc);

        List<String> list = plugin.getSigns().getStringList(arenaName);

        list.remove(UtilCore.convertString(loc));

        plugin.getSigns().set(arenaName, list);

        try {
            plugin.getSigns().save(plugin.getSignsFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
