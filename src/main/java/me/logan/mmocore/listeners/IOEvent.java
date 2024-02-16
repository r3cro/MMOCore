package me.logan.mmocore.listeners;

import lombok.AllArgsConstructor;
import me.logan.mmocore.Events.ProfileLoadedEvent;
import me.logan.mmocore.MMOCore;
import me.logan.mmocore.profiles.Profile;
import me.logan.mmocore.profiles.ProfileLoader;
import me.logan.mmocore.profiles.ProfileSaver;
import me.logan.mmocore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

@AllArgsConstructor
public class IOEvent implements Listener {

    private MMOCore plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Profile PROFILE = new Profile(event.getPlayer());
        plugin.getProfiles().put(player.getUniqueId(), PROFILE);
        new ProfileLoader(PROFILE, plugin).runTaskAsynchronously(plugin);

        new BukkitRunnable() {
            @Override
            public void run() {
                if(!player.isOnline()) {
                    cancel();
                }
                player.sendActionBar(Utils.color("&c" + PROFILE.getHealth() + "/" + PROFILE.getMaxHealth() + "♥      &a" + PROFILE.getArmour() + "⛨"));
            }
        }.runTaskTimer(plugin, 20*10L, 20L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Profile profile = plugin.getRemovedProfile(event.getPlayer());
        new ProfileSaver(profile, plugin).runTaskAsynchronously(plugin);

    }

}
