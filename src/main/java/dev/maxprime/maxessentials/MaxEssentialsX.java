package dev.maxprime.maxessentials;

// Bukkit/Spigot imports
import dev.maxprime.maxessentials.commands.*;
import dev.maxprime.maxessentials.db.MaxDBInstance;
import dev.maxprime.maxessentials.listeners.Listeners;
import dev.maxprime.maxessentials.models.PlayerStats;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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

    private MaxDBInstance db;

    public MaxDBInstance getDb() {
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
//		api = LuckPermsProvider.get();
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        msxPrint(prefix + "Settings up MaxDB...");

        try {
            this.db = new MaxDBInstance();
            db.initDB();
        } catch (SQLException e) {
            System.out.println("MaxDB couldn't do this:");
            e.printStackTrace();
        }

        msxPrint(prefix + "MaxDB is done!");

        getServer().getPluginManager().registerEvents(new Listeners(this), this);

        msxPrint(prefix + "Setting up commands...");

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("maxessentialsx").setExecutor(new TestCommand());
        getCommand("msx").setExecutor(new TestCommand());
        getCommand("gmc").setExecutor(new GamemodeCommand());
        getCommand("gms").setExecutor(new GamemodeCommand());
        getCommand("gma").setExecutor(new GamemodeCommand());
        getCommand("gmsp").setExecutor(new GamemodeCommand());
        getCommand("maxfly").setExecutor(new FlyCommand());
        getCommand("maxprefixset").setExecutor(new PrefixSet(this));
        getCommand("maxsuffixset").setExecutor(new SuffixSet(this));
        getCommand("setcoins").setExecutor(new StatsSet(this));
        getCommand("setxp").setExecutor(new SetXP(this));
        getCommand("setlevel").setExecutor(new SetLevel(this));


        msxPrint(prefix + "Done! Ur good to go Max!");
    }

    public static Scoreboard displayScoreboard(PlayerStats stats) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "      &1&lMAX&a&lPvP &r     "));
        Score score9 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&8(mpvp-l1)"));
        Score score8 = objective.getScore(ChatColor.translateAlternateColorCodes('&', " "));
        Score score7 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&fLevel: &9" + stats.getLevel() + "⚝"));
        Score score6 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "  "));
        Score score5 = objective.getScore(ChatColor.translateAlternateColorCodes('&', "&fProgress: &a" + stats.getXp()));
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

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onJoinEvent(PlayerJoinEvent e) throws SQLException {
        Player p = e.getPlayer();
        if (getDb().getPlayerStatsByUUID(p.getUniqueId().toString()) == null) {
            PlayerStats stats = new PlayerStats(p.getUniqueId().toString(), 0, 0, 0, "", "");
            getDb().createPlayerStats(stats);
        }
        if (isSlocked()) {
            p.kickPlayer(prefix + ChatColor.WHITE + "Sorry, MaxPvP is currently slocked for development.\n\n Does this work?");
        } else {
            PlayerStats stats = getDb().getPlayerStatsByUUID(p.getUniqueId().toString());
            String prefix = stats.getPrefix();
            String suffix = stats.getSuffix();
            p.setScoreboard(displayScoreboard(stats));
            p.teleport(getSpawn(p.getWorld()));
            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&9>&a>&f Hiya! &f" + prefix + p.getDisplayName() + suffix + "&f has joined the lobby. &a <&9<"));
            p.playSound(getSpawn(p.getWorld()), Sound.FIREWORK_TWINKLE, 1f, 1f);
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&9 Welcome, " + p.getDisplayName() + "!"), ChatColor.translateAlternateColorCodes('&', "&aTo the MaxPvP Server. Have fun!"));
        }
    }

    @EventHandler
    public void onQuitEvent(PlayerQuitEvent e) throws SQLException {
        Player p = e.getPlayer();
        PlayerStats stats = getDb().getPlayerStatsByUUID(p.getUniqueId().toString());
        String prefix = stats.getPrefix();
        String suffix = stats.getSuffix();
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&9>&a>&f " + prefix + p.getDisplayName() + suffix + "&f has left the lobby. &a <&9<"));
    }

    @EventHandler
    public void formatChat(AsyncPlayerChatEvent e) throws SQLException {
        Player p = e.getPlayer();
        PlayerStats stats = getDb().getPlayerStatsByUUID(p.getUniqueId().toString());
        int level = stats.getLevel();
        String prefix = stats.getPrefix();
        String suffix = stats.getSuffix();
        e.setFormat(ChatColor.translateAlternateColorCodes('&', "&7[" + Integer.toString(level) + "⚝] " + prefix + p.getDisplayName() + suffix + "&f: "  + "%2$s"));
    }

    @EventHandler
    public void onPlayerSpawnEvent(PlayerSpawnLocationEvent e) {
        Player p = e.getPlayer();
        e.setSpawnLocation(getSpawn(p.getWorld()));
    }

}