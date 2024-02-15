package me.logan.mmocore;

import lombok.Getter;
import me.logan.mmocore.commands.DevCommand;
import me.logan.mmocore.listeners.IOEvent;
import me.logan.mmocore.profiles.Profile;
import me.logan.mmocore.utils.DataFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public final class MMOCore extends JavaPlugin {

    @Getter
    private DataFile configFile;
    private Map<UUID, Profile> profiles = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

        registerListeners();

        getCommand("dev").setExecutor(new DevCommand());

        this.configFile = new DataFile(this, "config");
        getConfigFile().save();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new IOEvent(this), this);
    }

    public MMOCore getInstance() {
        return getPlugin(MMOCore.class);
    }
}
