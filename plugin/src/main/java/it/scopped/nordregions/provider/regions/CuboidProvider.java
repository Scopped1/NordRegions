package it.scopped.nordregions.provider.regions;

import it.scopped.nordregions.utility.serializer.LocationSerializer;
import org.bukkit.Location;

public class CuboidProvider implements RegionProvider {

    private final Location corner1;
    private final Location corner2;

    public CuboidProvider(Location corner1, Location corner2) {
        this.corner1 = corner1;
        this.corner2 = corner2;
    }

    public CuboidProvider(String corner1, String corner2) {
        this.corner1 = LocationSerializer.deserialize(corner1);
        this.corner2 = LocationSerializer.deserialize(corner2);
    }

    @Override
    public Location corner1() {
        return corner1;
    }

    @Override
    public Location corner2() {
        return corner2;
    }

    @Override
    public boolean contains(Location location) {
        if (corner1 == null || corner2 == null || !location.getWorld().getName().equals(corner1.getWorld().getName())) {
            return false;
        }

        int
                minX = Math.min(corner1.getBlockX(), corner2.getBlockX()),
                minY = Math.min(corner1.getBlockY(), corner2.getBlockY()),
                minZ = Math.min(corner1.getBlockZ(), corner2.getBlockZ());

        int
                maxX = Math.max(corner1.getBlockX(), corner2.getBlockX()),
                maxY = Math.max(corner1.getBlockY(), corner2.getBlockY()),
                maxZ = Math.max(corner1.getBlockZ(), corner2.getBlockZ());

        int
                x = location.getBlockX(),
                y = location.getBlockY(),
                z = location.getBlockZ();

        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }
}