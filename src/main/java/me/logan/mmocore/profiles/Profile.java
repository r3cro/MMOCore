package me.logan.mmocore.profiles;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class Profile {

    private UUID uuid;
    private String name;

    private double health;
    private double maxHealth;
    private double armour;

    public Profile(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }



}
