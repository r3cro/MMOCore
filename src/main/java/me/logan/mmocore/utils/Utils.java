package me.logan.mmocore.utils;

import lombok.AllArgsConstructor;
import me.logan.mmocore.MMOCore;
import org.bukkit.ChatColor;

import java.util.logging.Level;

public class Utils {


    public static String color(String message) { return ChatColor.translateAlternateColorCodes('&', message); }

}
