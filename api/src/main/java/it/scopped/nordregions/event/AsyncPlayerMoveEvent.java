package it.scopped.nordregions.event;

import it.scopped.nordregions.tracker.PlayerLocation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AsyncPlayerMoveEvent extends Event implements Cancellable {

    private final PlayerLocation playerLocation;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public AsyncPlayerMoveEvent(PlayerLocation playerLocation) {
        super(true);
        this.playerLocation = playerLocation;
        this.isCancelled = false;
    }

    public Location getFrom() {
        return playerLocation.from();
    }

    public Location getTo() {
        return playerLocation.to();
    }

    public Player getPlayer() {
        return playerLocation.player();
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }
}