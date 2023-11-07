package dev.maxprime.maxessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GamemodeCommand implements CommandExecutor {

    private static Plugin msx = Bukkit.getServer().getPluginManager().getPlugin("MaxEssentialsX");
    private static String prefix = ChatColor.translateAlternateColorCodes('&', msx.getConfig().getString("prefix"));

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
        if (l.equalsIgnoreCase("gmc")) {
            Player p = (Player) s;
            p.setGameMode(GameMode.CREATIVE);
            p.sendMessage(prefix + "You are now in creative mode!");
            return true;
        } else if (l.equalsIgnoreCase("gms")) {
            Player p = (Player) s;
            p.setGameMode(GameMode.SURVIVAL);
            p.sendMessage(prefix + "You are now in surival mode!");
            return true;
        } else if (l.equalsIgnoreCase("gma")) {
            Player p = (Player) s;
            p.setGameMode(GameMode.ADVENTURE);
            p.sendMessage(prefix + "You are now in adventure mode!");
            return true;
        } else if (l.equalsIgnoreCase("gmsp")) {
            Player p = (Player) s;
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage(prefix + "You are now in spectator mode!");
            return true;
        }
        return false;
    }

}