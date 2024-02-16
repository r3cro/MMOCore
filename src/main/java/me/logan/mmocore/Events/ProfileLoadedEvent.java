package me.logan.mmocore.Events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.logan.mmocore.profiles.Profile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class ProfileLoadedEvent extends Event {

    private Profile profile;

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
