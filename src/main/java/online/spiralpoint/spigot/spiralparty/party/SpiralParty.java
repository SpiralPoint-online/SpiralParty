package online.spiralpoint.spigot.spiralparty.party;

import online.spiralpoint.spigot.spiralparty.SpiralPartyConfig;
import online.spiralpoint.spigot.spiralparty.event.PartyMemberAddedEvent;
import online.spiralpoint.spigot.spiralparty.event.PartyMemberRemovedEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpiralParty {
    private final List<Player> partyMembers;

    SpiralParty() {
        this.partyMembers = new ArrayList<>();
    }

    public boolean addMember(Player member) {
        if(member == null) return false;
        this.partyMembers.add(member);
        Bukkit.getPluginManager().callEvent(new PartyMemberAddedEvent(this, member));
        return true;
    }

    public boolean removeMember(Player member) {
        if(member == null) return false;
        this.partyMembers.remove(member);
        Bukkit.getPluginManager().callEvent(new PartyMemberRemovedEvent(this, member));
        return true;
    }

    public List<Player> getPartyMembers() {
        return this.partyMembers;
    }

    public int getPartySize() {
        return this.partyMembers.size();
    }

    public boolean isPartyFull() {
        return this.partyMembers.size() >= SpiralPartyConfig.get().getPartySize();
    }

    public void sendMessage(String msg) {
        for(Player member : this.partyMembers) {
            member.sendMessage(ChatColor.AQUA.toString().concat("[Party] ").concat(msg));
        }
    }

}
