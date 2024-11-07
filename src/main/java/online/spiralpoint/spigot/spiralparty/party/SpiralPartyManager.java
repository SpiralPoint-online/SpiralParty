package online.spiralpoint.spigot.spiralparty.party;

import online.spiralpoint.spigot.spiralparty.SpiralPartyConfig;
import online.spiralpoint.spigot.spiralparty.SpiralPartyPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SpiralPartyManager extends BukkitRunnable {
    private static final ConcurrentMap<Player, SpiralParty> PARTY_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Player, List<Player>> INVITE_MAP = new ConcurrentHashMap<>();

    public static void sendInvite(Player invited, Player member) {
        if(invited == null || member == null) return;
        if(SpiralPartyManager.hasParty(member) && SpiralPartyManager.getParty(member).isPartyFull()) return;
        if(SpiralPartyManager.hasBeenInvitedBy(invited, member)) return;
        List<Player> inviteList = SpiralPartyManager.getInviteList(invited);
        inviteList.add(member);
        SpiralPartyManager.INVITE_MAP.put(invited, inviteList);
        new SpiralPartyManager(invited, member).runTaskLater(SpiralPartyPlugin.getPlugin(SpiralPartyPlugin.class), SpiralPartyConfig.get().getInviteExpireTime() * 20L);
        invited.sendMessage(ChatColor.DARK_PURPLE.toString().concat("You've been invited to join ").concat(member.getDisplayName()).concat("'s Party!"));
        member.sendMessage(ChatColor.DARK_PURPLE.toString().concat("You have invited ").concat(invited.getDisplayName()).concat(" to join a party!"));
    }

    public static void removeInvite(Player invited, Player member) {
        if(invited == null || member == null) return;
        if(!SpiralPartyManager.hasBeenInvitedBy(invited, member)) return;
        List<Player> inviteList = SpiralPartyManager.getInviteList(invited);
        inviteList.remove(member);
        if(inviteList.isEmpty()) {
            SpiralPartyManager.INVITE_MAP.remove(invited);
        } else {
            SpiralPartyManager.INVITE_MAP.put(invited, inviteList);
        }
    }

    public static List<Player> getInviteList(Player invited) {
        if(invited == null) return new ArrayList<>();
        return SpiralPartyManager.hasInvite(invited) ? SpiralPartyManager.INVITE_MAP.get(invited) : new ArrayList<>();
    }

    private static boolean hasInvite(Player invited) {
        if(invited == null) return false;
        return SpiralPartyManager.INVITE_MAP.containsKey(invited);
    }

    private static boolean hasBeenInvitedBy(Player invited, Player member) {
        if(invited == null || member == null) return false;
        List<Player> inviteList = SpiralPartyManager.getInviteList(invited);
        return inviteList.contains(member);
    }

    public static void joinParty(Player invited, Player member) {
        if(invited == null || member == null) return;
        if(!SpiralPartyManager.hasBeenInvitedBy(invited, member)) return;
        if(SpiralPartyManager.hasParty(invited)) {
            invited.sendMessage(ChatColor.DARK_RED.toString().concat("YOU ARE ALREADY A MEMBER OF A PARTY"));
        } else if(SpiralPartyManager.hasParty(member)) {
            SpiralParty party = SpiralPartyManager.getParty(member);
            if(party.isPartyFull()) return;
            if(party.addMember(invited)) SpiralPartyManager.PARTY_MAP.putIfAbsent(invited, party);
            for(Player player : party.getPartyMembers()) {
                SpiralPartyManager.removeInvite(invited, player);
            }
            party.sendMessage(invited.getDisplayName().concat(" joined the party!"));
        } else {
            SpiralParty party = new SpiralParty();
            if(party.addMember(member)) SpiralPartyManager.PARTY_MAP.putIfAbsent(member, party);
            if(party.addMember(invited)) SpiralPartyManager.PARTY_MAP.putIfAbsent(invited, party);
            SpiralPartyManager.removeInvite(invited, member);
            party.sendMessage(member.getDisplayName().concat(" joined the party!"));
            party.sendMessage(invited.getDisplayName().concat(" joined the party!"));
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

    private final Player invited, member;

    private SpiralPartyManager(Player invited, Player member) {
        this.invited = invited;
        this.member = member;
    }

    @Override
    public void run() {
        SpiralPartyManager.removeInvite(this.invited, this.member);
        this.invited.sendMessage(ChatColor.DARK_PURPLE.toString().concat("Invite from ").concat(this.member.getDisplayName()).concat(" has expired!"));
    }
}
