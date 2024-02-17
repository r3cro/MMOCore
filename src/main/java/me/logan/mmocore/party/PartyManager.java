package me.logan.mmocore.party;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.*;

public class PartyManager {

    @Getter
    private List<Party> parties;
    private Map<UUID, UUID> inviteQueue;

    public PartyManager() {
        parties = new ArrayList<Party>();
        inviteQueue = new HashMap<>();
    }

    public void addParty(Party party) { parties.add(party); }
    public void removeParty(Party party) { parties.remove(party); }

    public Party getParty(Player partyLeader) {
        for (Party party : parties) {
            if(party.getPartyLeaderUUID().equals(partyLeader.getUniqueId())) {
                return party;
            }
        }
        return null;
    }
    public Party getParty(UUID uuid) {
        for (Party party : parties) {
            if(party.isPartyMember(uuid)) {
                return party;
            }
        }
        return null;
    }

    public UUID getInvite(UUID playerUuid) {
        if (inviteQueue.containsKey(playerUuid)) {
            return inviteQueue.get(playerUuid);
        }
        return null;
    }
    public boolean addInvite(UUID uuid, Party party) {
        if (inviteQueue.containsKey(uuid)) {
            return false;
        }
        inviteQueue.put(uuid, party.getPartyID());
        return true;
    }
    public void removeInvite(UUID playerUuid) {
        inviteQueue.remove(playerUuid);
    }
    public boolean isInvited(UUID playerUuid) { return inviteQueue.containsKey(playerUuid); }

    public Party getInvitationParty(UUID playerUuid) {
        UUID partyId = inviteQueue.get(playerUuid);

        for (Party party : parties) {
            if (party.getPartyID().equals(partyId)) {
                return party;
            }
        }
        return null;
    }



}
