package it.scopped.nordregions.tracker;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public record PlayerLocation(Player player, Location from, Location to) {
}