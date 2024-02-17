package me.logan.mmocore.listeners;

import lombok.AllArgsConstructor;
import me.logan.mmocore.Events.ProfileLoadedEvent;
import me.logan.mmocore.MMOCore;
import me.logan.mmocore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class ProfileLoad implements Listener {

    private MMOCore plugin;

    @EventHandler
    public void onProfileLoad(ProfileLoadedEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("test");
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                if(!player.isOnline()) {
//                    cancel();
//                }
//                player.sendActionBar(Utils.color("&c" + plugin.getProfile(player).getHealth() + "/" + plugin.getProfile(player).getHealth() + "♥      &a" + plugin.getProfile(player).getDefense() + "⛨"));
//            }
//        }.runTaskTimer(plugin, 5L, 5L);

    }

}
