package it.scopped.nordregions;

import it.scopped.nordregions.region.RegionService;
import it.scopped.nordregions.selector.RegionSelectorService;
import it.scopped.nordregions.tracker.MovementTracker;
import it.scopped.nordregions.tracker.listeners.MovementListeners;
import it.scopped.nordregions.workload.WorkloadService;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class NordRegionsPlugin {

    private final NordRegionsBootstrap bootstrap;

    private final WorkloadService workloadService;
    private final RegionSelectorService regionSelectorService;
    private final RegionService regionService;

    private final ScheduledExecutorService scheduledExecutor;
    private final MovementTracker movementTracker;

    public NordRegionsPlugin(NordRegionsBootstrap bootstrap) {
        this.bootstrap = bootstrap;

        this.workloadService = new WorkloadService();
        this.workloadService.register(this);

        this.scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        this.movementTracker = new MovementTracker(this);

        this.regionSelectorService = new RegionSelectorService(this);
        this.regionService = new RegionService(this);

        listeners(
                new MovementListeners(this)
        );
        scheduledExecutor.scheduleAtFixedRate(this.movementTracker, 0L, 50L, TimeUnit.MILLISECONDS);
    }

    public void disable() {
        this.regionSelectorService.removeAllSelectors();

        this.movementTracker.clear();
        if (this.scheduledExecutor == null || this.scheduledExecutor.isShutdown()) return;
        this.scheduledExecutor.shutdown();
    }

    public void commands() {

    }

    public void listeners(Listener... listeners) {
        for (Listener listener : listeners) {
            bootstrap.getServer().getPluginManager().registerEvents(listener, bootstrap);
        }
    }

    public Logger logger() {
        return bootstrap.getLogger();
    }

    public Server server() {
        return bootstrap.getServer();
    }

    public Plugin bootstrap() {
        return bootstrap;
    }

    public RegionSelectorService regionSelectorService() {
        return regionSelectorService;
    }

    public WorkloadService workloadService() {
        return workloadService;
    }

    public MovementTracker movementTracker() {
        return movementTracker;
    }

    public RegionService regionService() {
        return regionService;
    }
}
