package it.scopped.nordregions.tracker.listeners;

import it.scopped.nordregions.NordRegionsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MovementListeners implements Listener {

    private final NordRegionsPlugin plugin;

    public MovementListeners(NordRegionsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        plugin.movementTracker().track(event.getPlayer());
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        plugin.movementTracker().untrack(event.getPlayer());
    }
}
