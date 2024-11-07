package online.spiralpoint.spigot.spiralparty.event.listener;

import online.spiralpoint.spigot.spiralparty.event.PartyMemberAddedEvent;
import online.spiralpoint.spigot.spiralparty.party.SpiralParty;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PartyModeInventoryListener implements Listener {

    private static final ConcurrentMap<SpiralParty, Inventory> PARTY_INVENTORY_MAP = new ConcurrentHashMap<>();

    @EventHandler
    public void onPartyMemberAdded(PartyMemberAddedEvent event) {
        //TODO:
    }
}
