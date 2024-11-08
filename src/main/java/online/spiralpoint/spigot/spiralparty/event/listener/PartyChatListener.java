package online.spiralpoint.spigot.spiralparty.event.listener;

import online.spiralpoint.spigot.spiralparty.SpiralPartyConfig;
import online.spiralpoint.spigot.spiralparty.party.SpiralPartyManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.Set;

public final class PartyChatListener implements Listener {
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if(event.getMessage().charAt(0) != SpiralPartyConfig.get().getPartyChatPrefix()) return;
        if(!SpiralPartyConfig.get().isPartyChatEnabled()) return;
        Player sender = event.getPlayer();
        if(SpiralPartyManager.hasParty(sender)) {
            Set<Player> recipients = event.getRecipients();
            List<Player> partyMembers = SpiralPartyManager.getParty(sender).getPartyMembers();
            partyMembers.remove(sender);

            recipients.clear();
            recipients.addAll(partyMembers);

            event.setFormat(ChatColor.translateAlternateColorCodes('&', "&3&l[PARTY] &3%s: &b%s"));
            event.setMessage(event.getMessage().substring(1));
        } else {
            event.setCancelled(true);
            sender.sendMessage(ChatColor.DARK_RED.toString().concat("YOU ARE NOT PART OF A PARTY!"));
        }
    }
}
