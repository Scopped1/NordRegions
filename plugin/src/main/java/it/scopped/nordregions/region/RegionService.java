package it.scopped.nordregions.region;

import com.google.gson.*;
import it.scopped.nordregions.NordRegionsPlugin;
import it.scopped.nordregions.flag.Flag;
import it.scopped.nordregions.flag.FlagType;
import it.scopped.nordregions.provider.regions.CuboidProvider;
import it.scopped.nordregions.provider.regions.RegionProvider;
import it.scopped.nordregions.region.type.RegionType;
import it.scopped.nordregions.utility.generic.Reloadable;
import it.scopped.nordregions.utility.serializer.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.logging.Level;

public class RegionService implements Reloadable {

    private final NordRegionsPlugin plugin;
    private final List<Region> loadedRegions = new ArrayList<>();
    private final File regionsFile;

    public RegionService(NordRegionsPlugin plugin) {
        this.plugin = plugin;
        this.regionsFile = new File(plugin.bootstrap().getDataFolder(), "regions.json");
        reload();
    }

    @Override
    public void reload() {
        loadedRegions.clear();

        if (!regionsFile.exists()) {
            plugin.logger().severe("regions.json not found.");
            return;
        }

        try (FileReader reader = new FileReader(regionsFile)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            JsonObject regionMap = json.getAsJsonObject("regions");

            for (Map.Entry<String, JsonElement> entry : regionMap.entrySet()) {
                String id = entry.getKey();
                JsonObject regionJson = entry.getValue().getAsJsonObject();

                RegionType type = RegionType.valueOf(regionJson.get("type").getAsString().toUpperCase());

                Location corner1 = LocationSerializer.deserialize(regionJson.get("corner1").getAsString());
                Location corner2 = LocationSerializer.deserialize(regionJson.get("corner2").getAsString());

                RegionProvider provider = new CuboidProvider(corner1, corner2); // TODO: temporary - implement other providers
                int priority = regionJson.get("priority").getAsInt();

                Map<FlagType, Flag.State> flags = new EnumMap<>(FlagType.class);
                JsonArray flagsArray = regionJson.getAsJsonArray("flags");

                for (JsonElement flagElement : flagsArray) {
                    JsonObject flagObj = flagElement.getAsJsonObject();
                    String flagId = flagObj.get("id").getAsString();
                    String stateStr = flagObj.get("state").getAsString();

                    FlagType flagType = Arrays.stream(FlagType.values())
                            .filter(f -> f.identifier().equalsIgnoreCase(flagId))
                            .findFirst()
                            .orElse(null);

                    if (flagType != null) {
                        Flag.State state = Flag.State.valueOf(stateStr.toUpperCase());
                        flags.put(flagType, state);
                    }
                }

                Region region = new Region(id, flags, type, provider);
                region.setPriority(priority);

                loadedRegions.add(region);
            }

            plugin.logger().info("Loaded " + loadedRegions.size() + " regions.");

        } catch (Exception e) {
            plugin.logger().log(Level.SEVERE, "Failed to load regions.json.", e);
        }
    }

    public void create(Region region) {
        loadedRegions.add(region);
        save();
    }

    public void delete(String identifier) {
        loadedRegions.removeIf(region -> region.identifier().equals(identifier));
        save();
    }

    public Optional<Region> region(String identifier) {
        return loadedRegions.stream().filter(region -> region.identifier().equals(identifier)).findFirst();
    }

    public Optional<Region> regionByPlayer(Player player) {
        return loadedRegions.stream().filter(region -> region.regionProvider().contains(player.getLocation())).findFirst();
    }

    public Optional<Region> highPriorityRegion(List<Region> regions) {
        return regions.stream().max(Comparator.comparingInt(Region::priority));
    }

    public void save() {
        JsonObject root = new JsonObject();
        JsonObject regionData = new JsonObject();

        for (Region region : loadedRegions) {
            JsonObject regionJson = new JsonObject();

            regionJson.addProperty("type", region.regionType().identifier());
            regionJson.addProperty("corner1", LocationSerializer.serialize(region.regionProvider().corner1()));
            regionJson.addProperty("corner2", LocationSerializer.serialize(region.regionProvider().corner2()));
            regionJson.addProperty("priority", region.priority());

            JsonArray flagsArray = new JsonArray();
            for (Map.Entry<FlagType, Flag.State> entry : region.flags().entrySet()) {
                JsonObject flagJson = new JsonObject();
                flagJson.addProperty("id", entry.getKey().identifier());
                flagJson.addProperty("state", entry.getValue().name().toLowerCase());

                flagsArray.add(flagJson);
            }

            regionJson.add("flags", flagsArray);
            regionData.add(region.identifier(), regionJson);
        }

        root.add("regions", regionData);

        try (FileWriter writer = new FileWriter(regionsFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);
        } catch (Exception e) {
            plugin.logger().log(Level.SEVERE, "Failed to save regions.json.", e);
        }
    }

    public List<Region> regions() {
        return loadedRegions;
    }

    public List<String> regionNames() {
        return loadedRegions.stream().map(Region::identifier).toList();
    }
}