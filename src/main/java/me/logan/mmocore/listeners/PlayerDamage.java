package me.logan.mmocore.listeners;

import lombok.AllArgsConstructor;
import me.logan.mmocore.MMOCore;
import me.logan.mmocore.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;


public class PlayerDamage implements Listener {

    private MMOCore PLUGIN;

    public PlayerDamage(MMOCore plugin) {
        this.PLUGIN = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) return;

        final Player player = (Player) event.getEntity();

        Profile playerProfile = PLUGIN.getProfile(player);

        Bukkit.broadcastMessage(PLUGIN.getProfile(player).getCombat() + "c");

        if (playerProfile != null) playerProfile.setCombat(playerProfile.getCombat() + 1);
    }


}
