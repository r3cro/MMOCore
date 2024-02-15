package me.logan.mmocore.listeners;

import lombok.AllArgsConstructor;
import me.logan.mmocore.MMOCore;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

@AllArgsConstructor
public class EntityDamage implements Listener {

    private MMOCore plugin;

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if(!(entity instanceof Player)) return;

        Player player = (Player) entity;

        double playerHealth = plugin.getProfiles().get(player.getUniqueId()).getHealth();
        double playerMaxHealth = plugin.getProfiles().get(player.getUniqueId()).getMaxHealth();
        double adjDamage = event.getDamage() * 5;

        if (adjDamage >= playerHealth || playerHealth <= 0) {
            //TODO player death animation
            plugin.getProfiles().get(player.getUniqueId()).setHealth(playerMaxHealth);
            player.sendMessage("you died");
        }

        plugin.getProfiles().get(player.getUniqueId()).setHealth(playerHealth-adjDamage);

        /*
         * TODO create a system to scale new health implementation to accurately display with vanilla hearts
         */

        event.setCancelled(true);

    }

    @EventHandler
    public void entityHealthRegen(EntityRegainHealthEvent event) {
        if(!(event.getEntity() instanceof Player)) event.setCancelled(false);

        //TODO create scaling health regeneration system based off skills/levels

        event.setCancelled(true);

    }

}