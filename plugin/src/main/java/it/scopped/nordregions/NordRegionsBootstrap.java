package it.scopped.nordregions;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class NordRegionsBootstrap extends JavaPlugin {

    private NordRegionsPlugin plugin;

    @Override
    public void onEnable() {
        try {
            this.plugin = new NordRegionsPlugin(this);
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to load plugin", e);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        if (this.plugin != null) this.plugin.disable();
    }
}
