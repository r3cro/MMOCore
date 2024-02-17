package me.logan.mmocore.listeners;

import lombok.AllArgsConstructor;
import me.logan.mmocore.Events.ProfileLoadedEvent;
import me.logan.mmocore.MMOCore;
import me.logan.mmocore.profiles.Profile;
import me.logan.mmocore.profiles.ProfileLoader;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@AllArgsConstructor
public class PlayerJoin implements Listener {

    private MMOCore plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        final Profile PROFILE = new Profile(event.getPlayer());
        plugin.getProfiles().put(event.getPlayer().getUniqueId(), PROFILE);
        new ProfileLoader(PROFILE, plugin).runTaskAsynchronously(plugin);

    }

}
