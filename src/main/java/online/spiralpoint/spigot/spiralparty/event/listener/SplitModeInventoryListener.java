package online.spiralpoint.spigot.spiralparty.event.listener;

import org.bukkit.event.Listener;

public class SplitModeInventoryListener implements Listener {
    private static SplitModeInventoryListener INSTANCE;

    public static SplitModeInventoryListener getInstance() {
        if(SplitModeInventoryListener.INSTANCE == null) SplitModeInventoryListener.INSTANCE = new SplitModeInventoryListener();
        return SplitModeInventoryListener.INSTANCE;
    }

    private SplitModeInventoryListener() {}
}
