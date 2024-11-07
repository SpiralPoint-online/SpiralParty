package online.spiralpoint.spigot.spiralparty.event;

import online.spiralpoint.spigot.spiralparty.party.SpiralParty;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class PartyEvent extends Event {

    private final SpiralParty party;

    public PartyEvent(@NotNull final SpiralParty party) {
        this.party = party;
    }

    @NotNull
    public SpiralParty getParty() {
        return this.party;
    }

}
