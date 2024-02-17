package me.logan.mmocore;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import me.logan.mmocore.commands.DevCommand;
import me.logan.mmocore.commands.PartyCommand;
import me.logan.mmocore.commands.ProfileCommand;
import me.logan.mmocore.listeners.PlayerDamage;
import me.logan.mmocore.listeners.PlayerJoin;
import me.logan.mmocore.listeners.PlayerQuit;
import me.logan.mmocore.listeners.ProfileLoad;
import me.logan.mmocore.party.PartyManager;
import me.logan.mmocore.profiles.Profile;
import me.logan.mmocore.utils.DataFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public final class MMOCore extends JavaPlugin {

    private DataFile configFile;
    private Map<UUID, Profile> profiles = new HashMap<>();
    private PartyManager partyManager;

    private HikariDataSource hikari = new HikariDataSource();

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.configFile = new DataFile(this, "config");
        this.partyManager = new PartyManager();
        setupHikari(hikari);


        registerListeners();

        getCommand("dev").setExecutor(new DevCommand(this));
        getCommand("party").setExecutor(new PartyCommand(this));
        getCommand("profile").setExecutor(new ProfileCommand(this));

        getConfigFile().save();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(hikari != null) {
            hikari.close();
        }
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoin(this), this);
        pluginManager.registerEvents(new PlayerQuit(this), this);
        pluginManager.registerEvents(new PlayerDamage(this), this);
        pluginManager.registerEvents(new ProfileLoad(this), this);
    }

    public MMOCore getInstance() {
        return getPlugin(MMOCore.class);
    }

    public Profile getProfile(Player player) {
        return profiles.get(player.getUniqueId());
    }

    public Profile getRemovedProfile(Player player) {
        return profiles.remove(player.getUniqueId());
    }

    private void setupHikari(HikariDataSource hikari) {
        String address = configFile.getString("database.address");
        String database = configFile.getString("database.schema");
        String username = configFile.getString("database.username");
        String password = configFile.getString("database.password");

        hikari.setMaximumPoolSize(10);
        hikari.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", address.split(":")[0]);
        hikari.addDataSourceProperty("port", address.split(":")[1]);
        hikari.addDataSourceProperty("databaseName", database);
        hikari.addDataSourceProperty("user", username);
        hikari.addDataSourceProperty("password", password);

        Connection connection = null;

        try {
            connection = hikari.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `mmo_players` (`uuid` varchar(36) NOT NULL, `name` varchar(16) NOT NULL, `health` int(16) NOT NULL, `defense` int(16) NOT NULL, `mana` int(16) NOT NULL, `combat` int(16) NOT NULL, `mining` int(16) NOT NULL, `farming` int(16) NOT NULL, `foraging` int(16) NOT NULL, `fishing` int(16) NOT NULL, `enchanting` int(16) NOT NULL, `alchemy` int(16) NOT NULL, `playerrank` varchar(36) NOT NULL, PRIMARY KEY (`uuid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
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
