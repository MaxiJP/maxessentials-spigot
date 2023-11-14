package dev.maxprime.maxessentials.models;

public class PlayerStats {

    private String uuid;
    private int coins;
    private int xp;
    private int level;
    private String prefix;

    private String suffix;

    public PlayerStats(String uuid, int coins, int xp, int level, String prefix, String suffix) {
        this.uuid = uuid;
        this.coins = coins;
        this.xp = xp;
        this.level = level;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getUuid() {
        return uuid;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
