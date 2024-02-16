package me.logan.mmocore.listeners;

import lombok.AllArgsConstructor;
import me.logan.mmocore.MMOCore;
import me.logan.mmocore.profiles.Profile;
import me.logan.mmocore.profiles.ProfileSaver;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerQuit implements Listener {

    private MMOCore plugin;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        Profile profile = plugin.getRemovedProfile(event.getPlayer());
        new ProfileSaver(profile, plugin).runTaskAsynchronously(plugin);
        Bukkit.broadcastMessage("quit event save");
    }

}
