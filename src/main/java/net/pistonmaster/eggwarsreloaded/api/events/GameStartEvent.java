package net.pistonmaster.eggwarsreloaded.api.events;

import net.pistonmaster.eggwarsreloaded.game.Game;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends GameEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    public GameStartEvent(Game game) {
        super(game);
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
