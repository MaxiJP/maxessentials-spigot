package dev.maxprime.maxessentials;

// Bukkit/Spigot imports
import dev.maxprime.maxessentials.commands.*;
import dev.maxprime.maxessentials.db.MaxDB;
import dev.maxprime.maxessentials.listeners.*;
import dev.maxprime.maxessentials.models.PlayerStats;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.sql.SQLException;

public class MaxEssentialsX extends JavaPlugin implements Listener {

    public String prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"));
    public double spawnx = getConfig().getDouble("spawnx");
    public double spawny = getConfig().getDouble("spawny");
    public double spawnz = getConfig().getDouble("spawnz");
    public String ConfigSlock = getConfig().getString("slocked");
//	LuckPerms api;

    private MaxDB db;

    public MaxDB getDb() {
        return db;
    }

    public void msxPrint(String message) {
        getLogger().info(message);
    }

    public boolean isSlocked() {
        return ConfigSlock.equals("y");
    }

    public Location getSpawn(World world) {
        Location spawn;
        spawn = new Location(world, spawnx, spawny, spawnz, 180f, 0f);
        return spawn;
    }

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        msxPrint(prefix + "Settings up MaxDB...");

        try {
            this.db = new MaxDB();
            db.initDB();
        } catch (SQLException e) {
            System.out.println("MaxDB couldn't do this:");
            System.out.println();
        }

        msxPrint(prefix + "Registering events...");
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new QOLlisteners(this), this);
        getServer().getPluginManager().registerEvents(new KillEvent(this), this);
        getServer().getPluginManager().registerEvents(new JoinLeaveEvent(this), this);

        msxPrint(prefix + "Setting up commands...");
        getCommand("maxessentialsx").setExecutor(new TestCommand());
        getCommand("msx").setExecutor(new TestCommand());
        getCommand("gmc").setExecutor(new GamemodeCommand(this));
        getCommand("gms").setExecutor(new GamemodeCommand(this));
        getCommand("gma").setExecutor(new GamemodeCommand(this));
        getCommand("gmsp").setExecutor(new GamemodeCommand(this));
        getCommand("maxfly").setExecutor(new FlyCommand());
        getCommand("maxprefixset").setExecutor(new PrefixSet(this));
        getCommand("maxsuffixset").setExecutor(new SuffixSet(this));
        getCommand("setcoins").setExecutor(new StatsSet(this));
        getCommand("setxp").setExecutor(new SetXP(this));
        getCommand("setlevel").setExecutor(new SetLevel(this));
        getCommand("maxkick").setExecutor(new KickCommand());


        msxPrint(prefix + "Done! Ur good to go Max!");
    }

    public static Scoreboard displayScoreboard(PlayerStats stats) {

        float xptogo = (float) ((stats.getXp() % 5000) - (stats.getXp() % 100)) / 1000;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "      &1&lMAX&a&lPvP &r     "));
        Score score9 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&8(mpvp-l1)"));
        Score score8 = objective.getScore(ChatColor.translateAlternateColorCodes('&', " "));
        Score score7 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&fLevel: &9" + (stats.getXp() - (stats.getXp() % 5000)) / 5000 + "⚝"));
        Score score6 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "  "));
        Score score5 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&fProgress: &a" + xptogo +"k&f/&b5k"));
        Score score4 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "    "));
        Score score3 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "Coins: &6" + stats.getCoins()));
        Score score2 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "     "));
        Score score1 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&emaxprime.dev:25637"));
        score9.setScore(9);
        score8.setScore(8);
        score7.setScore(7);
        score6.setScore(6);
        score5.setScore(5);
        score4.setScore(4);
        score3.setScore(3);
        score2.setScore(2);
        score1.setScore(1);
        return board;
    }

    @Override
    public void onDisable() {
        msxPrint("Max says goodbye. =[");

    }

}