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

    private int health;
    private int defense;
    private int mana;
    private int combat;
    private int mining;
    private int farming;
    private int foraging;
    private int fishing;
    private int enchanting;
    private int alchemy;
    private String rank;
    private int level;


    private boolean loaded;

    public Profile(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.rank = "hatch";
    }

}
