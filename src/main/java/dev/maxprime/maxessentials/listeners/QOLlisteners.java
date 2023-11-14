package dev.maxprime.maxessentials.listeners;

import dev.maxprime.maxessentials.MaxEssentialsX;
import dev.maxprime.maxessentials.db.MaxDBupdate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.sql.SQLException;
import java.util.Arrays;

public class QOLlisteners implements Listener {

    private final MaxEssentialsX plugin;

    public QOLlisteners(MaxEssentialsX plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerSpawnEvent(PlayerSpawnLocationEvent e) {
        Player p = e.getPlayer();
        e.setSpawnLocation(plugin.getSpawn(p.getWorld()));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        try {
            MaxDBupdate playerUpdate = new MaxDBupdate(plugin, p, 5, 1, 0);
            playerUpdate.updateStats(true);
        } catch (SQLException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
}