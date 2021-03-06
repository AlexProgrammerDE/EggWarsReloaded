package net.pistonmaster.eggwarsreloaded.api.events;

import net.pistonmaster.eggwarsreloaded.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public class GameJoinEvent extends PlayerGameEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;

    public GameJoinEvent(Player player, Game game) {
        super(player, game);
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    @Override
    public @Nonnull
    HandlerList getHandlers() {
        return HANDLERS;
    }
}
