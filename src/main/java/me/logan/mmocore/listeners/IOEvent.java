package me.logan.mmocore.listeners;

import lombok.AllArgsConstructor;
import me.logan.mmocore.MMOCore;
import me.logan.mmocore.profiles.Profile;
import me.logan.mmocore.utils.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class IOEvent implements Listener {

    private MMOCore plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        final Profile PROFILE = new Profile(event.getPlayer());
        plugin.getProfiles().put(player.getUniqueId(), PROFILE);

        PROFILE.setHealth(100d);
        PROFILE.setMaxHealth(100d);
        PROFILE.setArmour(10d);

        new BukkitRunnable() {
            @Override
            public void run() {
                if(!player.isOnline()) {
                    cancel();
                }
                player.sendActionBar(Utils.color("&c" + PROFILE.getHealth() + "/" + PROFILE.getMaxHealth() + "♥      &a" + PROFILE.getArmour() + "⛨"));
            }
        }.runTaskTimer(plugin, 5L, 5L);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

    }

}
