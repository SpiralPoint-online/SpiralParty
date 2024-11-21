package online.spiralpoint.spigot.spiralparty.party;

import online.spiralpoint.spigot.spiralparty.party.invite.SpiralInviteManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SpiralPartyManager {

    private static final ConcurrentMap<Player, SpiralParty> PARTY_MAP = new ConcurrentHashMap<>();

    public static boolean joinParty(Player invited, Player member) {
        if(invited == null || member == null) return false;
        if(invited.equals(member)) return false;
        if(!SpiralInviteManager.hasBeenInvitedBy(invited, member)) return false;
        if(SpiralPartyManager.hasParty(invited)) {
            invited.sendMessage(ChatColor.DARK_RED.toString().concat("YOU ARE ALREADY A MEMBER OF A PARTY"));
        } else if(SpiralPartyManager.hasParty(member)) {
            SpiralParty party = SpiralPartyManager.getParty(member);
            if(party.isPartyFull()) return false;
            if(party.addMember(invited)) SpiralPartyManager.PARTY_MAP.putIfAbsent(invited, party);
            for(Player player : party.getPartyMembers()) {
                SpiralInviteManager.removeInvite(invited, player);
            }
            party.sendMessage(invited.getDisplayName().concat(" joined the party!"));
        } else {
            SpiralParty party = new SpiralParty();
            if(party.addMember(member)) SpiralPartyManager.PARTY_MAP.putIfAbsent(member, party);
            if(party.addMember(invited)) SpiralPartyManager.PARTY_MAP.putIfAbsent(invited, party);
            SpiralInviteManager.removeInvite(invited, member);
            party.sendMessage(member.getDisplayName().concat(" joined the party!"));
            party.sendMessage(invited.getDisplayName().concat(" joined the party!"));
        }
        return true;
    }

    public static void leaveParty(Player member) {
        if(member == null) return;
        if(SpiralPartyManager.hasParty(member)) {
            SpiralParty party = SpiralPartyManager.getParty(member);
            if(party.removeMember(member)) SpiralPartyManager.PARTY_MAP.remove(member);
            party.sendMessage(member.getDisplayName().concat(" has left the party!"));
            member.sendMessage(ChatColor.DARK_BLUE.toString().concat("You have left the party!"));
        } else {
            member.sendMessage(ChatColor.DARK_RED.toString().concat("YOU ARE NOT A MEMBER OF A PARTY"));
        }
    }

    // Do I even need this method? I'll leave it until v1.0 release
    public static void disbandParty(SpiralParty party) {
        if(party == null) return;
        for(Player member : party.getPartyMembers()) {
            SpiralPartyManager.leaveParty(member);
        }
    }

    public static SpiralParty getParty(Player member) {
        if(member == null) return null;
        return SpiralPartyManager.PARTY_MAP.get(member);
    }

    public static boolean hasParty(Player member) {
        if(member == null) return false;
        return SpiralPartyManager.PARTY_MAP.containsKey(member);
    }

    private SpiralPartyManager() {}

}
