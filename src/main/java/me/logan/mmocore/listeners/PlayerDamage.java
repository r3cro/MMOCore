package me.logan.mmocore.listeners;

import lombok.AllArgsConstructor;
import me.logan.mmocore.MMOCore;
import me.logan.mmocore.profiles.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;


@AllArgsConstructor
public class PlayerDamage implements Listener {

    private MMOCore plugin;

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) return;

        final Player player = (Player) event.getEntity();
        Profile profile = plugin.getProfile(player);


    }


}
