package me.alexprogrammerde.eggwarsreloaded.utils;

import me.alexprogrammerde.eggwarsreloaded.EggWarsReloaded;
import me.alexprogrammerde.eggwarsreloaded.game.Game;
import me.alexprogrammerde.eggwarsreloaded.game.GameControl;
import me.alexprogrammerde.eggwarsreloaded.game.collection.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignManager implements Listener {
    private final EggWarsReloaded plugin;
    private final Map<Location, Game> signs = new HashMap<>();

    public SignManager(EggWarsReloaded plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::updateSigns, 0, 10);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Game game = signs.get(Objects.requireNonNull(event.getClickedBlock()).getLocation());

            if (game.getState() == GameState.LOBBY) {
                GameControl.addPlayer(event.getPlayer(), game);
            }
        }
    }

    public void detectSigns() {
        FileConfiguration config = plugin.getSigns();
        signs.clear();

        for (String game : config.getKeys(false)) {
            for (String sign : config.getStringList(game)) {
                if (UtilCore.convertLocation(sign).getBlock().getType().name().contains("WALL_SIGN")) {
                    signs.put(UtilCore.convertLocation(sign), GameControl.getGame(game));
                }
            }
        }
    }

    public void updateSigns() {
        for (Map.Entry<Location, Game> entry : signs.entrySet()) {
            Sign sign = (Sign) entry.getKey().getBlock();

            sign.setLine(0, entry.getValue().arenaName);
            sign.setLine(2, entry.getValue().inGamePlayers.size() + "/" + entry.getValue().maxPlayers);
            sign.setLine(3, entry.getValue().getState().name());
        }
    }
}
