package me.logan.mmocore.profiles;

import lombok.AllArgsConstructor;
import me.logan.mmocore.MMOCore;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@AllArgsConstructor
public class ProfileSaver extends BukkitRunnable{

    private Profile profile;
    private MMOCore plugin;

    private static final String save = "UPDATE mmo_players SET health=?, armour=? WHERE uuid=?";

    @Override
    public void run() {
        Connection connection = null;

        try {
            connection = plugin.getHikari().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(save);
            preparedStatement.setDouble(1, profile.getHealth());
            preparedStatement.setDouble(2, profile.getArmour());
            preparedStatement.setString(3, profile.getUuid().toString());
            preparedStatement.execute();
            preparedStatement.close();
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
    }

}
