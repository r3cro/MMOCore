package me.logan.mmocore.Events;

import lombok.Getter;
import me.logan.mmocore.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;

@Getter
public class ProfileLoadedEvent extends Event {

    private Profile profile;

    public ProfileLoadedEvent(Profile profile) {
        this.profile = profile;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(profile.getUuid());
    }
}