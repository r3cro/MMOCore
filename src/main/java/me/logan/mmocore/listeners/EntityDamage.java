package me.logan.mmocore.listeners;

import lombok.AllArgsConstructor;
import me.logan.mmocore.MMOCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.logging.Level;

@AllArgsConstructor
public class EntityDamage implements Listener {

    private MMOCore plugin;

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if(!(entity instanceof Player)) return;
        Player player = (Player) entity;

        if(plugin.getProfiles().get(player.getUniqueId()) == null) {
            player.kickPlayer("Error loading your profile, please reconnect");
        }

        event.setCancelled(true);

        double health = plugin.getProfiles().get(player.getUniqueId()).getHealth();
        double maxHealth = plugin.getProfiles().get(player.getUniqueId()).getMaxHealth();
        double damageAmount = event.getDamage();
        double adjDamageAmount = damageAmount*5;

        if (adjDamageAmount >= health) {
            if(plugin.getConfigFile().getBoolean("debug")) Bukkit.getLogger().log(Level.INFO, "Player should be dead - " + adjDamageAmount + " - " + damageAmount);

            //reset player back to max health
            plugin.getProfiles().get(player.getUniqueId()).setHealth(plugin.getProfiles().get(player.getUniqueId()).getMaxHealth());
            player.setHealth(20);

            //death animation
            player.sendMessage("you died");
            player.teleport(Bukkit.getWorld(player.getWorld().getUID()).getSpawnLocation());

            return;
        }

        if(plugin.getConfigFile().getBoolean("debug")) Bukkit.getLogger().log(Level.INFO, "Calculate damage done -" + adjDamageAmount + " - " + damageAmount);

        plugin.getProfiles().get(player.getUniqueId()).setHealth(health-adjDamageAmount);
        player.setHealth(player.getHealth()-damageAmount);

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void entityHealthRegen(EntityRegainHealthEvent event) {
        if(!(event.getEntity() instanceof Player)) event.setCancelled(false);

        //TODO create scaling health regeneration system based off skills/levels

        event.setCancelled(true);

    }

}
