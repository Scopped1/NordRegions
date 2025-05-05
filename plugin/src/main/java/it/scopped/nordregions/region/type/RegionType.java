package it.scopped.nordregions.region.type;

public enum RegionType {

    POLYGON("polygon"),
    CUBOID("cuboid");

    private final String identifier;

    RegionType(String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }
}
