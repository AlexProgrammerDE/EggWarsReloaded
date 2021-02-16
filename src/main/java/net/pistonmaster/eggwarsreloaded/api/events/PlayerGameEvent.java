package net.pistonmaster.eggwarsreloaded.api.events;

import net.pistonmaster.eggwarsreloaded.game.Game;
import org.bukkit.entity.Player;

public abstract class PlayerGameEvent extends GameEvent {
    private final Player player;

    protected PlayerGameEvent(Player player, Game game) {
        super(game);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
