package online.spiralpoint.spigot.spiralparty.event.listener;

import org.bukkit.event.Listener;

public class SplitModeExperienceListener implements Listener {
    private static SplitModeExperienceListener INSTANCE;

    public static SplitModeExperienceListener getInstance() {
        if(SplitModeExperienceListener.INSTANCE == null) SplitModeExperienceListener.INSTANCE = new SplitModeExperienceListener();
        return SplitModeExperienceListener.INSTANCE;
    }

    private SplitModeExperienceListener() {}
}
