package online.spiralpoint.spigot.spiralparty.event.listener;

import org.bukkit.event.Listener;

public class PartyModeExperienceListener implements Listener {
    private static PartyModeExperienceListener INSTANCE;

    public static PartyModeExperienceListener getInstance() {
        if(PartyModeExperienceListener.INSTANCE == null) PartyModeExperienceListener.INSTANCE = new PartyModeExperienceListener();
        return PartyModeExperienceListener.INSTANCE;
    }

    private PartyModeExperienceListener() {}
}
