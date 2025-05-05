package it.scopped.nordregions.flag.impl;

import it.scopped.nordregions.NordRegionsPlugin;
import it.scopped.nordregions.flag.Flag;
import it.scopped.nordregions.flag.FlagType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakFlag extends Flag<Boolean> {

    public BlockBreakFlag(NordRegionsPlugin plugin, Boolean value, FlagType flagType) {
        super(plugin, value, flagType);
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
    }

}
