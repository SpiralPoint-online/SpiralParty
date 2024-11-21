package online.spiralpoint.spigot.spiralparty.party.invite;

import online.spiralpoint.spigot.spiralparty.SpiralPartyConfig;
import online.spiralpoint.spigot.spiralparty.SpiralPartyPlugin;
import online.spiralpoint.spigot.spiralparty.party.SpiralPartyManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SpiralInviteManager {

    private static final ConcurrentMap<Player, List<Player>> INVITE_MAP = new ConcurrentHashMap<>();

    public static boolean sendInvite(Player invited, Player member) {
        if(invited == null || member == null) return false;
        if(invited.equals(member)) return false;
        if(SpiralPartyManager.hasParty(member) && SpiralPartyManager.getParty(member).isPartyFull()) return false;
        if(SpiralInviteManager.hasBeenInvitedBy(invited, member)) return false;
        List<Player> inviteList = SpiralInviteManager.getInviteList(invited);
        inviteList.add(member);
        SpiralInviteManager.INVITE_MAP.put(invited, inviteList);
        new BukkitRunnable() {
            @Override
            public void run() {
                SpiralInviteManager.removeInvite(invited, member);
                invited.sendMessage(ChatColor.DARK_PURPLE.toString().concat("Invite from ").concat(member.getDisplayName()).concat(" has expired!"));
            }
        }.runTaskLater(SpiralPartyPlugin.getPlugin(SpiralPartyPlugin.class), SpiralPartyConfig.getInstance().getInviteExpireTime() * 20L);
        invited.sendMessage(ChatColor.DARK_PURPLE.toString().concat("You've been invited to join ").concat(member.getDisplayName()).concat("'s Party!"));
        member.sendMessage(ChatColor.DARK_PURPLE.toString().concat("You have invited ").concat(invited.getDisplayName()).concat(" to join a party!"));
        return true;
    }

    public static void removeInvite(Player invited, Player member) {
        if(invited == null || member == null) return;
        if(!SpiralInviteManager.hasBeenInvitedBy(invited, member)) return;
        List<Player> inviteList = SpiralInviteManager.getInviteList(invited);
        inviteList.remove(member);
        if(inviteList.isEmpty()) {
            SpiralInviteManager.INVITE_MAP.remove(invited);
        } else {
            SpiralInviteManager.INVITE_MAP.put(invited, inviteList);
        }
    }

    public static List<Player> getInviteList(Player invited) {
        if(invited == null) return new ArrayList<>();
        return SpiralInviteManager.hasInvite(invited) ? SpiralInviteManager.INVITE_MAP.get(invited) : new ArrayList<>();
    }

    public static boolean hasInvite(Player invited) {
        if(invited == null) return false;
        return SpiralInviteManager.INVITE_MAP.containsKey(invited);
    }

    public static boolean hasBeenInvitedBy(Player invited, Player member) {
        if(invited == null || member == null) return false;
        List<Player> inviteList = SpiralInviteManager.getInviteList(invited);
        return inviteList.contains(member);
    }

    private SpiralInviteManager() {}

}
