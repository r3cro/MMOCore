package me.logan.mmocore.utils;

import org.bukkit.ChatColor;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Timestamp;

public class Utils {

    public static String color(String message) { return ChatColor.translateAlternateColorCodes('&', message); }

    public static Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }



}
