package online.spiralpoint.spigot.spiralparty.party;

import online.spiralpoint.spigot.spiralparty.SpiralPartyConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

//TODO: Add PartyInvites, PartyInventory and PartyExperience
public class SpiralParty {
    private final List<Player> partyMembers;

    SpiralParty() {
        this.partyMembers = new ArrayList<>();
    }

    public boolean addMember(Player member) {
        if(member == null) return false;
        return this.partyMembers.add(member);
    }

    public boolean removeMember(Player member) {
        if(member == null) return false;
        return this.partyMembers.remove(member);
    }

    public List<Player> getPartyMembers() {
        return this.partyMembers;
    }

    public int getPartySize() {
        return this.partyMembers.size();
    }

    public boolean isPartyFull() {
        return this.partyMembers.size() >= SpiralPartyConfig.getInstance().getPartySize();
    }

    public void sendMessage(String msg) {
        for(Player member : this.partyMembers) {
            member.sendMessage(ChatColor.AQUA.toString().concat("[Party] ").concat(msg));
        }
    }

}
