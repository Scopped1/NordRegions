package it.scopped.nordregions.flag;

import it.scopped.nordregions.NordRegionsPlugin;
import org.bukkit.event.Listener;

public class Flag<T> implements Listener {

    protected final NordRegionsPlugin plugin;
    private final T value;
    private final FlagType flagType;

    public Flag(NordRegionsPlugin plugin, T value, FlagType flagType) {
        this.plugin = plugin;
        this.value = value;
        this.flagType = flagType;
        plugin.server().getPluginManager().registerEvents(this, plugin.bootstrap());
    }

    public T value() {
        return value;
    }

    public FlagType flagType() {
        return flagType;
    }

    public enum State {
        ALLOW,
        DENY;

        public boolean value() {
            return this == ALLOW;
        }
    }

}
