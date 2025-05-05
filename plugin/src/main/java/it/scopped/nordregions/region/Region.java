package it.scopped.nordregions.region;

import it.scopped.nordregions.flag.Flag;
import it.scopped.nordregions.flag.FlagType;
import it.scopped.nordregions.provider.regions.RegionProvider;
import it.scopped.nordregions.region.type.RegionType;

import java.util.Map;

public class Region {

    private final String identifier;
    private final Map<FlagType, Flag.State> flags;
    private final RegionType regionType;
    private final RegionProvider regionProvider;
    private int priority;

    public Region(String identifier, Map<FlagType, Flag.State> flags, RegionType regionType, RegionProvider regionProvider) {
        this.identifier = identifier;
        this.flags = flags;
        this.regionType = regionType;
        this.regionProvider = regionProvider;
    }

    public void flag(FlagType flagType, Flag.State flagState) {
        flags.put(flagType, flagState);
    }

    public Flag.State flagState(FlagType flagType) {
        return flags.get(flagType);
    }

    public String identifier() {
        return identifier;
    }

    public Map<FlagType, Flag.State> flags() {
        return flags;
    }

    public int priority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public RegionProvider regionProvider() {
        return regionProvider;
    }

    public RegionType regionType() {
        return regionType;
    }
}
