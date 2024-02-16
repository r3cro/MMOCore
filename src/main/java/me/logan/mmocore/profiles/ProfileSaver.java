package me.logan.mmocore.profiles;

import lombok.AllArgsConstructor;
import me.logan.mmocore.MMOCore;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@AllArgsConstructor
public class ProfileSaver extends BukkitRunnable{

    private Profile profile;
    private MMOCore plugin;

    private static final String save = "UPDATE mmo_players SET health=?, defense=?, mana=?, combat=?, mining=?, farming=?, foraging=?, fishing=?, enchanting=?, alchemy=?, playerrank=? WHERE uuid=?";

    @Override
    public void run() {
        Connection connection = null;

        try {
            connection = plugin.getHikari().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(save);
            preparedStatement.setInt(1, profile.getHealth());
            preparedStatement.setInt(2, profile.getDefense());
            preparedStatement.setInt(3, profile.getMana());
            preparedStatement.setInt(4, profile.getCombat());
            preparedStatement.setInt(5, profile.getMining());
            preparedStatement.setInt(6, profile.getFarming());
            preparedStatement.setInt(7, profile.getForaging());
            preparedStatement.setInt(8, profile.getFishing());
            preparedStatement.setInt(9, profile.getEnchanting());
            preparedStatement.setInt(10, profile.getAlchemy());
            preparedStatement.setString(11, profile.getRank());
            preparedStatement.setString(12, profile.getUuid().toString());
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
