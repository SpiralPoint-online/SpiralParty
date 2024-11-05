package online.spiralpoint.spigot.spiralparty.event;

import online.spiralpoint.spigot.spiralparty.SpiralPartyConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class ChatListener implements Listener {
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if(event.getMessage().charAt(0) != SpiralPartyConfig.getInstance().getChatPrefix()) return;
        //TODO: check if player is in party, send parsed message to players in party
    }
}
