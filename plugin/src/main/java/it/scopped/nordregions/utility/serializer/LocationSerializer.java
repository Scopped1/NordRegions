package it.scopped.nordregions.utility.serializer;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationSerializer {

    public static String serialize(Location location) {
        return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ();
    }

    public static Location deserialize(String string) {
        String[] parts = string.split(";");
        if (parts.length != 4)
            throw new IllegalArgumentException("Invalid location string: " + string);

        return new Location(
                Bukkit.getWorld(parts[0]),
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3])
        );
    }

}
