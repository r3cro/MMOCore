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

    private static final String INSERT = "INSERT INTO mmo_players VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE name=?";
    private static final String SELECT = "SELECT health,defense,mana,combat,mining,farming,foraging,fishing,enchanting,alchemy,playerrank FROM mmo_players WHERE uuid=?";

    @Override
    public void run() {
        Connection connection = null;

        try {
            connection = plugin.getHikari().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, String.valueOf(profile.getUuid())); //UUID
            preparedStatement.setString(2, profile.getName()); //Name
            preparedStatement.setInt(3, 1);
            preparedStatement.setInt(4, 100); //Health
            preparedStatement.setInt(5, 9); //Defense
            preparedStatement.setInt(6, 8); //Mana
            preparedStatement.setInt(7, 7); //Combat
            preparedStatement.setInt(8, 6); //Mining
            preparedStatement.setInt(9, 5); //Farming
            preparedStatement.setInt(10, 4); //Foraging
            preparedStatement.setInt(11, 3); //Fishing
            preparedStatement.setInt(12, 2); //Enchanting
            preparedStatement.setInt(13, 1); //Alchemy
            preparedStatement.setString(14, "Hatch"); //Rank
            preparedStatement.setString(15, profile.getName());
            preparedStatement.execute();




            preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setString(1, profile.getUuid().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                profile.setHealth(resultSet.getInt("health"));
                profile.setDefense(resultSet.getInt("defense"));
                profile.setLevel(resultSet.getInt("level"));
                profile.setMana(resultSet.getInt("mana"));
                profile.setCombat(resultSet.getInt("combat"));
                profile.setMining(resultSet.getInt("mining"));
                profile.setFarming(resultSet.getInt("farming"));
                profile.setForaging(resultSet.getInt("foraging"));
                profile.setFishing(resultSet.getInt("fishing"));
                profile.setEnchanting(resultSet.getInt("enchanting"));
                profile.setAlchemy(resultSet.getInt("alchemy"));
                profile.setRank(resultSet.getString("playerrank"));
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
