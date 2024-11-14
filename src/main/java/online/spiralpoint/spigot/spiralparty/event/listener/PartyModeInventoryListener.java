package online.spiralpoint.spigot.spiralparty.event.listener;

import online.spiralpoint.spigot.spiralparty.SpiralPartyPlugin;
import online.spiralpoint.spigot.spiralparty.event.PartyCreatedEvent;
import online.spiralpoint.spigot.spiralparty.event.PartyMemberAddedEvent;
import online.spiralpoint.spigot.spiralparty.event.PartyMemberRemovedEvent;
import online.spiralpoint.spigot.spiralparty.party.SpiralParty;
import online.spiralpoint.spigot.spiralparty.party.SpiralPartyManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PartyModeInventoryListener implements Listener {
    private static PartyModeInventoryListener INSTANCE;
    
    public static PartyModeInventoryListener getInstance() {
        if(PartyModeInventoryListener.INSTANCE == null) PartyModeInventoryListener.INSTANCE = new PartyModeInventoryListener();
        return PartyModeInventoryListener.INSTANCE;
    }

    public static void saveSerializedInventory(@NotNull final Player player) {
        YamlConfiguration yaml = new YamlConfiguration();
        UUID playerUUID = player.getUniqueId();
        PlayerInventory playerInventory = player.getInventory();
        SpiralPartyPlugin plugin = SpiralPartyPlugin.getPlugin(SpiralPartyPlugin.class);
        File file = new File(plugin.getDataFolder(), player.getUniqueId().toString().concat(".yml"));
        Map<String, ItemStack> items = new HashMap<>();
        int index = 0;
        for(ItemStack item : playerInventory.getStorageContents()) {
            if(item == null) continue;
            items.put(String.valueOf(index), item);
            playerInventory.getStorageContents()[index] = null;
            index++;
        }
        yaml.createSection("inventory", items);
        try {
            yaml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadSerializedInventory(@NotNull final Player player) {
        UUID playerUUID = player.getUniqueId();
        SpiralPartyPlugin plugin = SpiralPartyPlugin.getPlugin(SpiralPartyPlugin.class);
        File file = new File(plugin.getDataFolder(), player.getUniqueId().toString().concat(".yml"));
        if(!file.exists()) return;
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        PlayerInventory playerInventory = player.getInventory();
        Map<String, Object> items = yaml.getConfigurationSection("inventory").getValues(false);
        for(Map.Entry<String, Object> item : items.entrySet()) {
            ItemStack itemStack = (ItemStack) item.getValue();
            int index = Integer.parseInt(item.getKey());
            playerInventory.getStorageContents()[index] = itemStack;
        }
        file.delete();
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

    private void setPartyInventory(@NotNull final SpiralParty party, @NotNull final Inventory partyInventory) {
        for(Player member : party.getPartyMembers()) {
            Inventory memberInventory = member.getInventory();
            for(int i = 0; i < partyInventory.getSize(); i++) {
                memberInventory.setItem(i, partyInventory.getItem(i));
            }
        }
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if(event.getEntity() instanceof Player player) {
            if(!SpiralPartyManager.hasParty(player)) return;
            event.setCancelled(true);
            ItemStack itemstack = event.getItem().getItemStack();
            SpiralParty party = SpiralPartyManager.getParty(player);
            Inventory partyInventory = this.getPartyInventory(party);
            partyInventory.addItem(itemstack);
            this.setPartyInventory(party, partyInventory);
            event.getItem().remove();
        }
    }

    //TODO: Add more events to account for dropping items, moving items, etc...

    @EventHandler
    public void onPartyCreated(PartyCreatedEvent event) {
        this.addPartyInventory(event.getParty(), Bukkit.createInventory(null, 36));
    }

    @EventHandler
    public void onPartyMemberAdded(PartyMemberAddedEvent event) {
        PartyModeInventoryListener.saveSerializedInventory(event.getMember());
    }

    @EventHandler
    public void onPartyMemberRemoved(PartyMemberRemovedEvent event) {
        PartyModeInventoryListener.loadSerializedInventory(event.getMember());
    }
}
