package me.logan.mmocore.utils;

import lombok.AllArgsConstructor;
import me.logan.mmocore.MMOCore;
import org.bukkit.ChatColor;

import java.util.logging.Level;

@AllArgsConstructor
public class Utils {

    public static MMOCore plugin;

    public static String color(String message) { return ChatColor.translateAlternateColorCodes('&', message); }

    public static void log(Level level, String message) {
        if(plugin.getConfigFile().getBoolean("debug")) {
            plugin.getLogger().log(level, message);
        }
    }

}
