package me.logan.mmocore.profiles;

import lombok.AllArgsConstructor;
import me.logan.mmocore.Events.ProfileLoadedEvent;
import me.logan.mmocore.MMOCore;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

@AllArgsConstructor
public class ProfileLoader extends BukkitRunnable {

    private Profile profile;
    private MMOCore plugin;

    private static final String INSERT = "INSERT INTO mmo_players VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE name=?";
    private static final String SELECT = "SELECT health,armour FROM mmo_players WHERE uuid=?";

    @Override
    public void run() {
        Connection connection = null;

        try {
            connection = plugin.getHikari().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, profile.getUuid().toString());
            preparedStatement.setString(2, profile.getName());
            preparedStatement.setDouble(3, 100);
            preparedStatement.setDouble(4, 10);
            preparedStatement.setString(5, profile.getName());
            preparedStatement.execute();




            preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setString(1,profile.getName());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                profile.setHealth(resultSet.getDouble("health"));
                profile.setArmour(resultSet.getDouble("armour"));
                profile.setLoaded(true);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if (profile.isLoaded()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getPluginManager().callEvent(new ProfileLoadedEvent(profile));
                }
            }.runTask(plugin);
        }
    }

}
