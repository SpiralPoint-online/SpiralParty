package online.spiralpoint.spigot.spiralparty.event.listener;

import online.spiralpoint.spigot.spiralparty.party.SpiralParty;
import online.spiralpoint.spigot.spiralparty.party.SpiralPartyManager;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public class EqualModeInventoryListener implements Listener {
    private static EqualModeInventoryListener INSTANCE;

    public static EqualModeInventoryListener getInstance() {
        if(EqualModeInventoryListener.INSTANCE == null) EqualModeInventoryListener.INSTANCE = new EqualModeInventoryListener();
        return EqualModeInventoryListener.INSTANCE;
    }

    private final Map<Item, SpiralParty> droppedItems = new HashMap<>();

    private EqualModeInventoryListener() {}

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        Item item = event.getItem();
        ItemStack itemStack = item.getItemStack();
        if(event.getEntity() instanceof Player player) {
            if(this.droppedItems.containsKey(item) && this.droppedItems.remove(item).hasMember(player)) return;
            if(!SpiralPartyManager.hasParty(player)) return;
            SpiralParty party = SpiralPartyManager.getParty(player);
            for(Player member : party.getPartyMembers()) {
                if(member.equals(player)) continue;
                PlayerInventory memberInventory = member.getInventory();
                memberInventory.addItem(itemStack);
            }
        }
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        Item item = event.getEntity();
        if(this.droppedItems.containsKey(item)) this.droppedItems.remove(item);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Item itemDrop = event.getItemDrop();
        Player player = event.getPlayer();
        if(!SpiralPartyManager.hasParty(player)) return;
        SpiralParty party = SpiralPartyManager.getParty(player);
        this.droppedItems.put(itemDrop, party);
    }

}
