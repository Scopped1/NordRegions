package it.scopped.nordregions.region.impl;

import it.scopped.nordregions.flag.Flag;
import it.scopped.nordregions.flag.FlagType;
import it.scopped.nordregions.provider.regions.CuboidProvider;
import it.scopped.nordregions.region.Region;
import it.scopped.nordregions.region.type.RegionType;

import java.util.Map;

public class CuboidRegion extends Region {

    public CuboidRegion(String identifier, Map<FlagType, Flag.State> flags) {
        super(identifier, flags, RegionType.CUBOID, new CuboidProvider());
    }

}
