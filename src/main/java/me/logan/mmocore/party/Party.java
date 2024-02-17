package me.logan.mmocore.party;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Party {

    public static final int MAX_PLAYERS = 5;
    private UUID partyID;
    private String partyName;
    private UUID partyLeaderUUID;
    private List<UUID> members = new ArrayList<>();

    public Party (Player partyLeader) {
        partyID = UUID.randomUUID();
        partyLeaderUUID = partyLeader.getUniqueId();
        setPartyName(Bukkit.getPlayer(partyLeaderUUID).getName() + "'s party");
        addPartyMember(partyLeader);
    }

    public void addPartyMember(Player player) {
        members.add(player.getUniqueId());
    }

    public void removePartyMember(Player player) {
        members.remove(player.getUniqueId());

        if(!checkPartySize()) {
            if(player.getUniqueId().equals(partyLeaderUUID)) {
                Player newPartyLeader = Bukkit.getServer().getPlayer(members.get(0));
                setPartyLeader(newPartyLeader);
            }
        }
        if (player.isOnline()) {
            player.sendMessage(ChatColor.YELLOW + "The party has been disbanded");
        }
    }

    public static void dissolve(Party party) {
        party.partyLeaderUUID = null;
        party.members.clear();
        party = null;
    }

    public UUID getMember(int i) { return this.members.get(i); }
    public boolean isPartyMember(UUID uuid) {
        return members.contains(uuid);
    }
    public boolean isLeader(Player player) {
        return player.getUniqueId().equals(partyLeaderUUID);
    }
    public Player getLeader() { return Bukkit.getServer().getPlayer(partyLeaderUUID); }
    public void setPartyLeader(Player partyLeader) {
        this.partyLeaderUUID = partyLeader.getUniqueId();
        partyLeader.sendMessage(ChatColor.YELLOW + partyLeader.getName() + " is the new party leader!");
    }

    public boolean checkPartySize() {
        if (getPartySize() == 1) {
            Party.dissolve(this);
            return true;
        }
        return false;
    }

    public int getPartySize() {
        return members.size();
    }

    public boolean isPartyFull() {
        return getPartySize() == MAX_PLAYERS;
    }
}
