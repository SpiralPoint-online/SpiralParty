package online.spiralpoint.spigot.spiralparty.event.listener;

import org.bukkit.event.Listener;

public class EqualModeExperienceListener implements Listener {
    private static EqualModeExperienceListener INSTANCE;

    public static EqualModeExperienceListener getInstance() {
        if(EqualModeExperienceListener.INSTANCE == null) EqualModeExperienceListener.INSTANCE = new EqualModeExperienceListener();
        return EqualModeExperienceListener.INSTANCE;
    }

    private EqualModeExperienceListener() {}
}
