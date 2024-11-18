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

public class SplitModeInventoryListener implements Listener {
    private static SplitModeInventoryListener INSTANCE;

    public static SplitModeInventoryListener getInstance() {
        if(SplitModeInventoryListener.INSTANCE == null) SplitModeInventoryListener.INSTANCE = new SplitModeInventoryListener();
        return SplitModeInventoryListener.INSTANCE;
    }

    private final Map<Item, SpiralParty> droppedItems = new HashMap<>();

    private SplitModeInventoryListener() {}

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        Item item = event.getItem();
        ItemStack itemStack = item.getItemStack();
        int totalAmount = itemStack.getAmount();
        if(event.getEntity() instanceof Player player) {
            if(this.droppedItems.containsKey(item) && this.droppedItems.remove(item).hasMember(player)) return;
            if(!SpiralPartyManager.hasParty(player)) return;
            SpiralParty party = SpiralPartyManager.getParty(player);
            int splitAmount = totalAmount / party.getPartySize();
            int splitRemainder = totalAmount % party.getPartySize();
            itemStack.setAmount(splitAmount);
            for(Player member : party.getPartyMembers()) {
                if(member.equals(player)) continue;
                PlayerInventory memberInventory = member.getInventory();
                memberInventory.addItem(itemStack);
            }
            itemStack.setAmount(splitAmount + splitRemainder);
            item.setItemStack(itemStack);
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
