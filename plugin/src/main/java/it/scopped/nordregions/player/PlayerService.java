package it.scopped.nordregions.player;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerService {

    private final Set<UUID> ignoredPlayers = new HashSet<>();

    public boolean toggle(Player player) {
        if (ignoredPlayers.contains(player.getUniqueId())) {
            ignoredPlayers.remove(player.getUniqueId());
            return false;
        } else {
            ignoredPlayers.add(player.getUniqueId());
            return true;
        }
    }

}
