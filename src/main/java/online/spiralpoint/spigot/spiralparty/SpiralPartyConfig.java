package online.spiralpoint.spigot.spiralparty;

import org.bukkit.configuration.file.FileConfiguration;

public final class SpiralPartyConfig {

    public static final String DISABLED = "disabled";
    public static final String SPLIT = "split";
    public static final String EQUAL = "equal";
    public static final String PARTY = "party";

    private static SpiralPartyConfig instance;

    public static SpiralPartyConfig getInstance() {
        if(SpiralPartyConfig.instance == null) SpiralPartyConfig.instance = new SpiralPartyConfig();
        return SpiralPartyConfig.instance;
    }

    private final int partySize;
    private final char chatPrefix;
    private final String sharedExperience;
    private final String sharedInventory;
    private final long inviteExpireTime;

    private SpiralPartyConfig() {
        FileConfiguration config = SpiralPartyPlugin.getInstance().getConfig();
        this.partySize = Math.max(2, Math.min(9, config.getInt("max-party-size", 3)));
        this.chatPrefix = config.getString("party-chat-prefix", "@").charAt(0);
        this.sharedExperience = config.getString("shared-experience", "party");
        this.sharedInventory = config.getString("shared-inventory", "party");
        this.inviteExpireTime = config.getLong("invite-expire-time", 600L);
    }

    public int getPartySize() {
        return this.partySize;
    }

    public char getChatPrefix() {
        return this.chatPrefix;
    }

    public String getSharedExperience() {
        return this.sharedExperience;
    }

    public String getSharedInventory() {
        return this.sharedInventory;
    }

    public long getInviteExpireTime() {
        return this.inviteExpireTime;
    }

}
