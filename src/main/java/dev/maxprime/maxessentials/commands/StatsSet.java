package dev.maxprime.maxessentials.commands;

import dev.maxprime.maxessentials.MaxEssentialsX;
import dev.maxprime.maxessentials.models.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class StatsSet implements CommandExecutor {

    private final MaxEssentialsX plugin;


    public StatsSet(MaxEssentialsX plugin) {
        this.plugin = plugin;
    }

    private final String prefix = ChatColor.translateAlternateColorCodes('&', Bukkit.getServer().getPluginManager().getPlugin("MaxEssentialsX").getConfig().getString("prefix"));

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
        Player p = Bukkit.getPlayer(args[0]);
        if (p == null) {
            s.sendMessage("This play doesn't exist or isn't online right now.");
            return true;
        }
        try {
            PlayerStats oldStats = plugin.getDb().getPlayerStatsByUUID(p.getUniqueId().toString());
            PlayerStats stats = new PlayerStats(p.getUniqueId().toString(), Integer.parseInt(args[1]), oldStats.getXp(), oldStats.getLevel(), oldStats.getPrefix(), oldStats.getSuffix());
            plugin.getDb().updatePlayerStats(stats);
            p.setScoreboard(MaxEssentialsX.displayScoreboard(stats));
            s.sendMessage(prefix + "Changed " + p.getDisplayName() + "'s coins from " + oldStats.getCoins() + " to " + stats.getCoins() + ".");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
