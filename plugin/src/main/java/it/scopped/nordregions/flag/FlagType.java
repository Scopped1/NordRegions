package it.scopped.nordregions.flag;

public enum FlagType {

    BLOCK_BREAK("block-break", Flag.State.ALLOW),
    BLOCK_PLACE("block-place", Flag.State.ALLOW),
    ;

    private final String identifier;
    private final Flag.State defaultState;

    FlagType(String identifier, Flag.State defaultState) {
        this.identifier = identifier;
        this.defaultState = defaultState;
    }

    public String identifier() {
        return identifier;
    }

    public Flag.State defaultState() {
        return defaultState;
    }
}
