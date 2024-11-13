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
    private static PartyModeInventoryListener INSTANCE;
    
    public static PartyModeInventoryListener getInstance() {
        if(PartyModeInventoryListener.INSTANCE == null) PartyModeInventoryListener.INSTANCE = new PartyModeInventoryListener();
        return PartyModeInventoryListener.INSTANCE;
    }
    
    private final ConcurrentMap<SpiralParty, Inventory> partyInventoryMap;

    private PartyModeInventoryListener() {
        this.partyInventoryMap = new ConcurrentHashMap<>();
    }

    public ConcurrentMap<SpiralParty, Inventory> getPartyInventoryMap() {
        return this.partyInventoryMap;
    }

    public Inventory getPartyInventory(@NotNull final SpiralParty party) {
        return this.getPartyInventoryMap().get(party);
    }
    
    public void addPartyInventory(@NotNull final SpiralParty party, @NotNull final Inventory inventory) {
        this.getPartyInventoryMap().putIfAbsent(party, inventory);
    }

    public void removePartyInventory(@NotNull final SpiralParty party) {
        this.getPartyInventoryMap().remove(party);
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if(event.getEntity() instanceof Player player) {
            if(!SpiralPartyManager.hasParty(player)) return;
            event.setCancelled(true);
            //TODO: Put into PartyInventory instead of player inventory.
        }
    }
    
    @EventHandler
    public void onPartyCreated(PartyCreatedEvent event) {
        this.addPartyInventory(event.getParty(), Bukkit.createInventory(null, 36, "Party Inventory"));
    }

    @EventHandler
    public void onPartyMemberAdded(PartyMemberAddedEvent event) {
        //TODO: Convert Player's Inventory to DataStream and store in PDC of the players entity.
    }

    @EventHandler
    public void onPartyMemberRemoved(PartyMemberRemovedEvent event) {
        //TODO: Pull Players Inventory from PDC DataStream and restore it.
    }
}
