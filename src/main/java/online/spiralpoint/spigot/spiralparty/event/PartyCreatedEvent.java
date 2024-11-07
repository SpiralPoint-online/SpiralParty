package online.spiralpoint.spigot.spiralparty.event;

import online.spiralpoint.spigot.spiralparty.party.SpiralParty;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PartyCreatedEvent extends PartyEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public PartyCreatedEvent(@NotNull final SpiralParty party) {
        super(party);
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
