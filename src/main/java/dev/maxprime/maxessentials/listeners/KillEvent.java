package dev.maxprime.maxessentials.listeners;

import dev.maxprime.maxessentials.MaxEssentialsX;
import dev.maxprime.maxessentials.db.MaxDBupdate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.SQLException;

public class KillEvent implements Listener {

    private final MaxEssentialsX plugin;

    public KillEvent(MaxEssentialsX plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKillEvent(PlayerDeathEvent e) throws SQLException {
        Player killer = e.getEntity().getPlayer().getKiller();
        Player victim = e.getEntity().getPlayer();

        e.setDeathMessage(victim.getDisplayName() + " was bullied by " + killer.getDisplayName());

        killer.sendMessage(ChatColor.GOLD + "Kill! (+15 coins)");
        killer.sendMessage(ChatColor.AQUA + "Kill! (+3 xp)");

        victim.sendMessage(ChatColor.GOLD + "Death. (-15 coins)");
        victim.sendMessage(ChatColor.AQUA + "Death (-3 xp)");

        MaxDBupdate updateKiller = new MaxDBupdate(plugin, killer, 15, 3, 0);
        MaxDBupdate updateVictim = new MaxDBupdate(plugin, victim, -15, -3, 0);

        updateKiller.updateStats(true);
        updateVictim.updateStats(true);
    }

}
