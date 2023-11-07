package dev.maxprime.maxessentials.commands;

import dev.maxprime.maxessentials.MaxEssentialsX;
import dev.maxprime.maxessentials.models.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.SQLException;

public class SetLevel implements CommandExecutor {

    public MaxEssentialsX plugin;

    public SetLevel(MaxEssentialsX plugin) {
        this.plugin = plugin;
    }

    private final String prefix = ChatColor.translateAlternateColorCodes('&', Bukkit.getServer().getPluginManager().getPlugin("MaxEssentialsX").getConfig().getString("prefix"));

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {

        Player p = (Player) Bukkit.getOfflinePlayer(args[0]);
        try {
            PlayerStats oldStats = plugin.getDb().getPlayerStatsByUUID(p.getUniqueId().toString());
            PlayerStats stats = new PlayerStats(p.getUniqueId().toString(), oldStats.getCoins(), oldStats.getXp(), Integer.parseInt(args[1]), oldStats.getPrefix(), oldStats.getSuffix());
            plugin.getDb().updatePlayerStats(stats);
            p.setScoreboard(MaxEssentialsX.displayScoreboard(stats));
            s.sendMessage(prefix + "Changed " + p.getDisplayName() + "'s star/level from " + oldStats.getLevel() + " to " + stats.getLevel() + ".");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;

    }
}
