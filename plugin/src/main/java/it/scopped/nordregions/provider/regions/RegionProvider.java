package it.scopped.nordregions.provider.regions;

import org.bukkit.Location;

public interface RegionProvider {

    Location corner1();

    Location corner2();

    boolean contains(Location location);

}
