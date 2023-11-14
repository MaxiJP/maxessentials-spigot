package dev.maxprime.maxessentials.db;

import dev.maxprime.maxessentials.MaxEssentialsX;
import dev.maxprime.maxessentials.models.PlayerStats;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.SQLException;

public class MaxDBupdate {
    private MaxEssentialsX plugin;
    private Player player;
    private int coins;
    private int xp;
    private int level;

    public MaxDBupdate(MaxEssentialsX plugin, Player player, int coins, int xp, int level) throws SQLException {
        this.plugin = plugin;
        this.player = player;
        this.coins = coins;
        this.xp = xp;
        this.level = level;
    }

    public void updateStats(boolean updateScoreboard) throws SQLException {
        String uuid = player.getUniqueId().toString();
        PlayerStats stats = plugin.getDb().getPlayerStatsByUUID(uuid);

        stats.setCoins(stats.getCoins() + coins);
        stats.setXp(stats.getXp() + xp);
        stats.setLevel(stats.getLevel() + level);

        plugin.getDb().updatePlayerStats(stats);

        if (updateScoreboard) {
            player.setScoreboard(MaxEssentialsX.displayScoreboard(stats));
        }
    }

}
