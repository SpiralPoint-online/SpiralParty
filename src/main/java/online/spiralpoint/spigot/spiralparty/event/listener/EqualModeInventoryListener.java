package online.spiralpoint.spigot.spiralparty.event.listener;

import org.bukkit.event.Listener;

public class EqualModeInventoryListener implements Listener {
    private static EqualModeInventoryListener INSTANCE;

    public static EqualModeInventoryListener getInstance() {
        if(EqualModeInventoryListener.INSTANCE == null) EqualModeInventoryListener.INSTANCE = new EqualModeInventoryListener();
        return EqualModeInventoryListener.INSTANCE;
    }

    private EqualModeInventoryListener() {}
}
