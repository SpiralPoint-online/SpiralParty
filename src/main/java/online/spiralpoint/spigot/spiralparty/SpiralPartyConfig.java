package online.spiralpoint.spigot.spiralparty;

import org.bukkit.configuration.file.FileConfiguration;

public final class SpiralPartyConfig {

    public enum Mode {
        DISABLED, SPLIT, EQUAL, PARTY;
    }

    private static SpiralPartyConfig INSTANCE;

    /**
     * This static method initializes a new SpiralPartyConfig Object if singleton field is null.
     *
     * @return singleton field of this class
     */
    public static SpiralPartyConfig getInstance() {
        if(SpiralPartyConfig.INSTANCE == null) SpiralPartyConfig.INSTANCE = new SpiralPartyConfig();
        return SpiralPartyConfig.INSTANCE;
    }

    /**
     * This static method will set singleton field to null. (Useful for disable and restarting the plugin)
     */
    public static void nullifyInstance() {
        if(SpiralPartyConfig.INSTANCE != null) SpiralPartyConfig.INSTANCE = null;
    }

    private final int partySize;
    private final boolean partyChatEnabled;
    private final char partyChatPrefix;
    private final SpiralPartyConfig.Mode sharedExperience;
    private final SpiralPartyConfig.Mode sharedInventory;
    private final long inviteExpireTime;

    private SpiralPartyConfig() {
        FileConfiguration config = SpiralPartyPlugin.getPlugin(SpiralPartyPlugin.class).getConfig();
        this.partySize = Math.max(2, Math.min(9, config.getInt("max-party-size", 3)));
        this.partyChatEnabled = config.getBoolean("party-chat.enabled", true);
        this.partyChatPrefix = config.getString("party-chat.prefix", "@").charAt(0);
        this.sharedExperience = SpiralPartyConfig.Mode.valueOf(config.getString("shared-experience", "party"));
        this.sharedInventory = SpiralPartyConfig.Mode.valueOf(config.getString("shared-inventory", "party"));
        this.inviteExpireTime = config.getLong("invite-expire-time", 600L);
    }

    public int getPartySize() {
        return this.partySize;
    }

    public boolean isPartyChatEnabled() {
        return this.partyChatEnabled;
    }

    public char getPartyChatPrefix() {
        return this.partyChatPrefix;
    }

    public SpiralPartyConfig.Mode getSharedExperienceMode() {
        return this.sharedExperience;
    }

    public SpiralPartyConfig.Mode getSharedInventoryMode() {
        return this.sharedInventory;
    }

    public long getInviteExpireTime() {
        return this.inviteExpireTime;
    }

}
