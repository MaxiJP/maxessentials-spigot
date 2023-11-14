package dev.maxprime.maxessentials.listeners;

import dev.maxprime.maxessentials.MaxEssentialsX;
import dev.maxprime.maxessentials.models.PlayerStats;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.SQLException;

public class ChatEvent implements Listener {

    private final MaxEssentialsX plugin;

    public ChatEvent(MaxEssentialsX plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void formatChat(AsyncPlayerChatEvent e) throws SQLException {
        Player p = e.getPlayer();
        PlayerStats stats = plugin.getDb().getPlayerStatsByUUID(p.getUniqueId().toString());
        int level = stats.getLevel();
        String prefix = stats.getPrefix();
        String suffix = stats.getSuffix();
        e.setFormat(ChatColor.translateAlternateColorCodes('&', "&7[" + level + "⚝] " + prefix + p.getDisplayName() + suffix + "&f: "  + "%2$s"));
    }

}
