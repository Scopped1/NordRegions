package it.scopped.nordregions.listeners;

import it.scopped.nordregions.NordRegionsPlugin;
import it.scopped.nordregions.event.AsyncPlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerListeners implements Listener {

    private final NordRegionsPlugin plugin;

    public PlayerListeners(NordRegionsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void asyncMove(AsyncPlayerMoveEvent event) {
        Player player = event.getPlayer();
        //TODO ...
    }
}
