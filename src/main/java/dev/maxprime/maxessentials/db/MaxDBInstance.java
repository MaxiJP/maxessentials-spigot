package dev.maxprime.maxessentials.db;

import dev.maxprime.maxessentials.models.PlayerStats;

import java.sql.*;

public class MaxDBInstance {

    private Connection connection;

    public Connection getConnection() throws SQLException {

        if(connection != null) {
            return connection;
        }

        String url = "jdbc:mysql://na05-sql.pebblehost.com/customer_600573_msx";
        String user = "customer_600573_msx";
        String password = "orTwmbT~bM0S$mVU3gzE";


        this.connection = DriverManager.getConnection(url, user, password);
        System.out.println("MaxDB init is good!");

        return this.connection;

    }

    public void initDB() throws SQLException {
        Statement statement = getConnection().createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS max_stats(uuid varchar(36) primary key, prefix varchar(30), suffix varchar(30), coins int, xp int, level int )";
        statement.execute(sql);

        System.out.println("MaxDB has connection the the max_stats() table. Yay! LOUIS!!!!!!");

        statement.close();
    }

    public PlayerStats getPlayerStatsByUUID(String uuid) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM max_stats WHERE uuid = ?");
        statement.setString(1, uuid);
        ResultSet results = statement.executeQuery();

        if (results.next()) {
            int coins = results.getInt("coins");
            int xp = results.getInt("xp");
            int level = results.getInt("level");
            String prefix = results.getString("prefix");
            String suffix = results.getString("suffix");

            PlayerStats playerStats = new PlayerStats(uuid, coins, xp, level, prefix, suffix);

            statement.close();

            return playerStats;
        }

        statement.close();
        return null;

    }

    public void createPlayerStats(PlayerStats stats) throws SQLException {

        PreparedStatement statement = getConnection()
                .prepareStatement("INSERT INTO max_stats(uuid, prefix, suffix, coins, xp, level) VALUES (?, ?, ?, ?, ?, ?)");

        statement.setString(1, stats.getUuid());
        statement.setInt(4, stats.getCoins());
        statement.setInt(5, stats.getXp());
        statement.setInt(6, stats.getLevel());
        statement.setString(2, stats.getPrefix());
        statement.setString(3, stats.getSuffix());

        statement.executeUpdate();

        statement.close();
    }

    public void updatePlayerStats(PlayerStats stats) throws SQLException {

        PreparedStatement statement = getConnection()
                .prepareStatement("UPDATE max_stats SET coins = ?, xp = ?, level = ?, prefix = ?, suffix = ? WHERE uuid = ?");

        statement.setInt(1, stats.getCoins());
        statement.setInt(2, stats.getXp());
        statement.setInt(3, stats.getLevel());
        statement.setString(4, stats.getPrefix());
        statement.setString(5, stats.getSuffix());
        statement.setString(6, stats.getUuid());

        statement.executeUpdate();

        statement.close();
    }

}
