package it.scopped.nordregions.provider.message;

import it.scopped.nordregions.NordRegionsPlugin;
import it.scopped.nordregions.utility.StringsUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.time.Duration;

public class MessageProvider {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();
    private final NordRegionsPlugin plugin;

    public MessageProvider(NordRegionsPlugin plugin) {
        this.plugin = plugin;
    }

    public Component translate(String message, Object... params) {
        Component legacy = LEGACY_COMPONENT_SERIALIZER.deserialize(StringsUtil.replace(message, params));
        String miniMessage = MINI_MESSAGE.serialize(legacy);

        return MINI_MESSAGE.deserialize(miniMessage);
    }

    public void message(Player player, String message, Object... params) {
        player.sendMessage(translate(message, params));
    }

    public void actionBar(Player player, String message, Object... params) {
        player.sendActionBar(translate(message, params));
    }

    public void title(Player player, String title, String subtitle, Object... params) {
        player.showTitle(
                Title.title(
                        translate(title, params),
                        translate(subtitle, params),
                        Title.Times.times(
                                Duration.ofSeconds(2),
                                Duration.ofSeconds(2),
                                Duration.ofSeconds(2)
                        )
                )
        );
    }

    public void broadcast(String message, Object... params) {
        for (Player player : plugin.server().getOnlinePlayers()) {
            message(player, message, params);
        }
    }

}
