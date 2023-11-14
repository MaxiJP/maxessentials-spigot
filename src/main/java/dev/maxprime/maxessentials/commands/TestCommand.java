package dev.maxprime.maxessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TestCommand implements CommandExecutor {

    private static final Plugin msx = Bukkit.getServer().getPluginManager().getPlugin("MaxEssentialsX");
    private static final String prefix = ChatColor.translateAlternateColorCodes('&', msx.getConfig().getString("prefix"));

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
        if (s instanceof Player) {
            Player p = (Player) s;
            if (p.hasPermission("maxessentialsx.test")) {
                p.sendMessage(prefix + "Hello Max! This plugin is working fine!");
            } else {
                p.sendMessage("Sorry! You don't have permission to run this MaxEssentialsX command.");
            }
        } else {
            s.sendMessage(prefix + "Hello Console! This plugin is working fine!");
        }
        return true;
    }
}