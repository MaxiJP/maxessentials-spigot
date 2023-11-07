package dev.maxprime.maxessentials.commands;

import dev.maxprime.maxessentials.MaxEssentialsX;
import dev.maxprime.maxessentials.models.PlayerStats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class PrefixSet implements CommandExecutor {

    private final MaxEssentialsX plugin;

    public PrefixSet(MaxEssentialsX plugin) {
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
        Player p = (Player) s;
        try {
            PlayerStats stats = this.plugin.getDb().getPlayerStatsByUUID(p.getUniqueId().toString());

            if (stats == null) {

                stats = new PlayerStats(p.getUniqueId().toString(), 0, 0, 0, args[0] + " ", "");

                this.plugin.getDb().createPlayerStats(stats);

            } else {

                stats.setPrefix(args[0] + " ");
                this.plugin.getDb().updatePlayerStats(stats);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }
}
