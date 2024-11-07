package online.spiralpoint.spigot.spiralparty.event.listener;

import online.spiralpoint.spigot.spiralparty.event.PartyCreatedEvent;
import online.spiralpoint.spigot.spiralparty.event.PartyMemberAddedEvent;
import online.spiralpoint.spigot.spiralparty.event.PartyMemberRemovedEvent;
import online.spiralpoint.spigot.spiralparty.party.SpiralParty;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PartyModeInventoryListener implements Listener {
    private static final ConcurrentMap<SpiralParty, Inventory> PARTY_INVENTORY_MAP = new ConcurrentHashMap<>();

    @EventHandler
    public void onPartyCreated(PartyCreatedEvent event) {
        //TODO: Create Inventory for party
    }

    @EventHandler
    public void onPartyMemberAdded(PartyMemberAddedEvent event) {
        //TODO: Convert Player's Inventory to DataStream and store in PDC of the players entity.
    }

    @EventHandler
    public void onPartyMemberRemoved(PartyMemberRemovedEvent event) {

    }
}
