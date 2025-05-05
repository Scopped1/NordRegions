package it.scopped.nordregions.selector;

import it.scopped.nordregions.NordRegionsPlugin;
import it.scopped.nordregions.provider.regions.RegionProvider;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class RegionSelectorService {

    private final NordRegionsPlugin plugin;

    public RegionSelectorService(NordRegionsPlugin plugin) {
        this.plugin = plugin;
    }

    public void removeSelectorsWithTag(String inputTag) {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getScoreboardTags().contains("rgselector-" + inputTag)) {
                    plugin.workloadService().workload(entity::remove);
                }
            }
        }
    }

    public void removeAllSelectors() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                for (String tag : entity.getScoreboardTags()) {
                    if (tag.startsWith("rgselector-")) {
                        plugin.workloadService().workload(entity::remove);
                        break;
                    }
                }
            }
        }
    }

    public void removeTempTeams() {
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        for (Team team : board.getTeams()) {
            if (team.getName().startsWith("rgselector+")) {
                plugin.workloadService().workload(team::unregister);
            }
        }
    }

    public void drawSelector(RegionProvider regionProvider, World world, String id, NamedTextColor glowColor) {
        Location loc1 = regionProvider.corner1();
        Location loc2 = regionProvider.corner2();

        if (loc1.getWorld() == null || !loc1.getWorld().equals(loc2.getWorld())) {
            return;
        }

        int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        int highestNumber = board.getTeams().stream()
                .filter(team -> team.getName().startsWith("rgselector+"))
                .mapToInt(team -> {
                    try {
                        return Integer.parseInt(team.getName().substring(12));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max().orElse(0);

        Team team = board.registerNewTeam("rgselector+" + (highestNumber + 1));
        if (glowColor != null) {
            team.color(glowColor);
        }
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);

        for (int x = minX; x <= maxX; x++) {
            spawnSelectorEntity(world, team, id, x, minY, minZ);
            spawnSelectorEntity(world, team, id, x, maxY, minZ);
            spawnSelectorEntity(world, team, id, x, minY, maxZ);
            spawnSelectorEntity(world, team, id, x, maxY, maxZ);
        }

        for (int y = minY; y <= maxY; y++) {
            spawnSelectorEntity(world, team, id, minX, y, minZ);
            spawnSelectorEntity(world, team, id, maxX, y, minZ);
            spawnSelectorEntity(world, team, id, minX, y, maxZ);
            spawnSelectorEntity(world, team, id, maxX, y, maxZ);
        }

        for (int z = minZ; z <= maxZ; z++) {
            spawnSelectorEntity(world, team, id, minX, minY, z);
            spawnSelectorEntity(world, team, id, maxX, minY, z);
            spawnSelectorEntity(world, team, id, minX, maxY, z);
            spawnSelectorEntity(world, team, id, maxX, maxY, z);
        }
    }

    private void spawnSelectorEntity(World world, Team team, String id, int x, int y, int z) {
        plugin.workloadService().workload(() -> {
            Location location = new Location(world, x + 0.5, y, z + 0.5);
            Slime slime = (Slime) world.spawnEntity(location, EntityType.SLIME);

            slime.setSize(2);
            slime.setAI(false);
            slime.setGravity(false);
            slime.setCollidable(false);
            slime.setSilent(true);
            slime.setCanPickupItems(false);
            slime.setGlowing(true);
            slime.setInvulnerable(true);
            slime.setInvisible(true);
            slime.addScoreboardTag("rgselector-" + id);
            slime.setPersistent(true);

            team.addEntry(slime.getUniqueId().toString());
        });
    }
}