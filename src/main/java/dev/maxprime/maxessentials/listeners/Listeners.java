package dev.maxprime.maxessentials.listeners;

import dev.maxprime.maxessentials.MaxEssentialsX;
import dev.maxprime.maxessentials.models.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.sql.SQLException;

public class Listeners implements Listener {

    private final MaxEssentialsX plugin;

    public Listeners(MaxEssentialsX plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player p = e.getPlayer();

        try {
            PlayerStats stats = this.plugin.getDb().getPlayerStatsByUUID(p.getUniqueId().toString());

            if (stats == null) {

                stats = new PlayerStats(p.getUniqueId().toString(), 5, 1, 0, "[MAX] ", "");

                this.plugin.getDb().createPlayerStats(stats);
                p.setScoreboard(MaxEssentialsX.displayScoreboard(stats));
            } else {

                stats.setCoins(stats.getCoins() + 5);
                stats.setXp(stats.getXp() + 1);
                this.plugin.getDb().updatePlayerStats(stats);
                p.setScoreboard(MaxEssentialsX.displayScoreboard(stats));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

}