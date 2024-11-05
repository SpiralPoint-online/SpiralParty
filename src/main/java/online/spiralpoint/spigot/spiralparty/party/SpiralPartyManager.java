package online.spiralpoint.spigot.spiralparty.party;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SpiralPartyManager {
    private static final ConcurrentMap<Player, SpiralParty> PARTY_MAP = new ConcurrentHashMap<>();

    public static void joinParty(Player player, Player member) {
        if(player == null || member == null) return;
        if(SpiralPartyManager.hasParty(player)) {
            player.sendMessage(ChatColor.DARK_RED.toString().concat("YOU ARE ALREADY A MEMBER OF A PARTY"));
        } else if(SpiralPartyManager.hasParty(member)) {
            SpiralParty party = SpiralPartyManager.getParty(member);
            if(party.isPartyFull()) return;
            if(party.addMember(player)) SpiralPartyManager.PARTY_MAP.putIfAbsent(player, party);
            party.sendMessage(player.getDisplayName().concat(" joined the party!"));
        } else {
            SpiralParty party = new SpiralParty();
            if(party.addMember(member)) SpiralPartyManager.PARTY_MAP.putIfAbsent(member, party);
            if(party.addMember(player)) SpiralPartyManager.PARTY_MAP.putIfAbsent(player, party);
            party.sendMessage(member.getDisplayName().concat(" joined the party!"));
            party.sendMessage(player.getDisplayName().concat(" joined the party!"));
        }
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

    public static void disbandParty(SpiralParty party) {
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
}
