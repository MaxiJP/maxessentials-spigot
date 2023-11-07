package dev.maxprime.maxessentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
        Player p = (Player) s;
        p.setAllowFlight(true);
        p.setFlying(true);
        return true;
    }

}