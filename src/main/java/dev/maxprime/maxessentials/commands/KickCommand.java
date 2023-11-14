package dev.maxprime.maxessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            s.sendMessage("Player not online.");
            return true;
        }

        int words = 1;
        String reason = "";

        while(words < args.length) {
            if (words + 1 == args.length) {
                reason = reason + args[words];
            } else {
                reason = reason + args[words] + " ";
            }
            words++;
        }
        target.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&9Max&aPvP&f AC &7 has kicked you. \n\n &f Reason: &7" + reason));

        return true;
    }
}
