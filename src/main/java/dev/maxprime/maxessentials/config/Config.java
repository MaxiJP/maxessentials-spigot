package dev.maxprime.maxessentials.config;

import dev.maxprime.maxessentials.MaxEssentialsX;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Config {

//    private FileConfiguration fileConfig;
//
//    public Config(MaxEssentialsX plugin) {
//        this.fileConfig = plugin.getConfig();
//    }

//    String prefix = fileConfig.getString("prefix");
//    public Location getSpawn(Player p) {
//        return new Location(p.getWorld(), fileConfig.getInt("spawnx"), fileConfig.getInt("spawny"), fileConfig.getInt("spawnz"));
//    }

    public String getPrefix() {
        return "MAX: ";
    }
}
