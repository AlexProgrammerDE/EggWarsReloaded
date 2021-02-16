package net.pistonmaster.eggwarsreloaded.api.events;

import net.pistonmaster.eggwarsreloaded.game.Game;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class GameEvent extends Event implements Cancellable {
    private final Game game;
    private boolean cancelled = false;

    public GameEvent(Game game) {
        this.game = game;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public Game getGame() {
        return game;
    }
}
