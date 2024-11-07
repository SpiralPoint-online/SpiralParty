package online.spiralpoint.spigot.spiralparty.event;

import online.spiralpoint.spigot.spiralparty.party.SpiralParty;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PartyMemberAddedEvent extends PartyEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return PartyMemberAddedEvent.HANDLERS;
    }

    private final Player member;

    public PartyMemberAddedEvent(@NotNull final SpiralParty party, @NotNull final Player member) {
        super(party);
        this.member = member;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return PartyMemberAddedEvent.HANDLERS;
    }

    @NotNull
    public Player getMember() {
        return this.member;
    }
}
