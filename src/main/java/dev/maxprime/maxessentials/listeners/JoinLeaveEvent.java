package dev.maxprime.maxessentials.listeners;

import dev.maxprime.maxessentials.MaxEssentialsX;
import dev.maxprime.maxessentials.models.PlayerStats;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

import static dev.maxprime.maxessentials.MaxEssentialsX.displayScoreboard;

public class JoinLeaveEvent implements Listener {

    private final MaxEssentialsX plugin;

    public JoinLeaveEvent(MaxEssentialsX plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent e) throws SQLException {
        Player p = e.getPlayer();
        if (plugin.getDb().getPlayerStatsByUUID(p.getUniqueId().toString()) == null) {
            PlayerStats stats = new PlayerStats(p.getUniqueId().toString(), 0, 0, 0, "", "");
            plugin.getDb().createPlayerStats(stats);
        }
        if (plugin.isSlocked()) {
            p.kickPlayer(plugin.prefix + ChatColor.WHITE + "Sorry, MaxPvP is currently slocked for development.\n\n Does this work?");
        } else {
            PlayerStats stats = plugin.getDb().getPlayerStatsByUUID(p.getUniqueId().toString());
            String prefix = stats.getPrefix();
            String suffix = stats.getSuffix();
            p.setScoreboard(displayScoreboard(stats));
            p.teleport(plugin.getSpawn(p.getWorld()));
            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&9>&a>&f Hiya! &f" + prefix + p.getDisplayName() + suffix + "&f has joined the lobby. &a <&9<"));
            p.playSound(plugin.getSpawn(p.getWorld()), Sound.FIREWORK_TWINKLE, 1f, 1f);
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&9 Welcome, " + p.getDisplayName() + "!"), ChatColor.translateAlternateColorCodes('&', "&aTo the MaxPvP Server. Have fun!"));
        }
    }

    @EventHandler
    public void onQuitEvent(PlayerQuitEvent e) throws SQLException {
        Player p = e.getPlayer();
        PlayerStats stats = plugin.getDb().getPlayerStatsByUUID(p.getUniqueId().toString());
        String prefix = stats.getPrefix();
        String suffix = stats.getSuffix();
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&9>&a>&f " + prefix + p.getDisplayName() + suffix + "&f has left the lobby. &a <&9<"));
    }

}
