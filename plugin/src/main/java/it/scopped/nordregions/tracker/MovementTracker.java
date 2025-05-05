package it.scopped.nordregions.tracker;

import it.scopped.nordregions.NordRegionsPlugin;
import it.scopped.nordregions.event.AsyncPlayerMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MovementTracker extends BukkitRunnable {

    private final NordRegionsPlugin plugin;
    private final ConcurrentMap<UUID, PlayerLocation> playersLocation = new ConcurrentHashMap<>();

    public MovementTracker(NordRegionsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        this.playersLocation.forEach((key, value) -> {
            Player player = Bukkit.getPlayer(key);
            if (player == null) return;

            Location from = value.to();
            Location to = player.getLocation();

            if (from.getWorld().getName().equals(to.getWorld().getName())
                    && from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ())
                return;

            PlayerLocation frame = new PlayerLocation(player, from, to);
            playersLocation.put(player.getUniqueId(), frame);

            plugin.server().getPluginManager().callEvent(new AsyncPlayerMoveEvent(frame));
        });
    }

    public void track(Player player) {
        playersLocation.put(player.getUniqueId(),
                new PlayerLocation(player, player.getLocation(), player.getLocation()));
    }

    public void untrack(Player player) {
        playersLocation.remove(player.getUniqueId());
    }

    public void clear() {
        playersLocation.clear();
        this.cancel();
    }

}
