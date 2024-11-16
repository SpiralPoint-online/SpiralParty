package online.spiralpoint.spigot.spiralparty;

import online.spiralpoint.spigot.spiralparty.command.PartyCommand;
import online.spiralpoint.spigot.spiralparty.event.listener.*;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpiralPartyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        //TODO: CHECK FOR SOFTDEPEND 'SpiralChat'

        // INITIALIZE CONFIG CLASS SINGLETON
        this.saveDefaultConfig();
        SpiralPartyConfig config = SpiralPartyConfig.getInstance();

        // SET COMMAND EXECUTORS AND TABCOMPLETERS
	    PluginCommand partyCommand = this.getCommand("party");
        if(partyCommand != null) {
            partyCommand.setExecutor(PartyCommand.getInstance());
            partyCommand.setTabCompleter(PartyCommand.getInstance());
        }
        PluginCommand partyInviteCommand = this.getCommand("partyinvite");
        if(partyInviteCommand != null) {
            partyInviteCommand.setExecutor(PartyCommand.getInstance());
            partyInviteCommand.setTabCompleter(PartyCommand.getInstance());
        }
        PluginCommand partyJoinCommand = this.getCommand("partyjoin");
        if(partyJoinCommand != null) {
            partyJoinCommand.setExecutor(PartyCommand.getInstance());
            partyJoinCommand.setTabCompleter(PartyCommand.getInstance());
        }
        PluginCommand partyLeaveCommand = this.getCommand("partyleave");
        if(partyLeaveCommand != null) {
            partyLeaveCommand.setExecutor(PartyCommand.getInstance());
            partyLeaveCommand.setTabCompleter(PartyCommand.getInstance());
        }
        PluginCommand partyListCommand = this.getCommand("partylist");
        if(partyListCommand != null) {
            partyListCommand.setExecutor(PartyCommand.getInstance());
            partyListCommand.setTabCompleter(PartyCommand.getInstance());
        }

        // REGISTER LISTENERS
        PluginManager pluginManager = this.getServer().getPluginManager();
        //TODO: add if check if SpiralChat is enabled
        if(config.isPartyChatEnabled()) pluginManager.registerEvents(PartyChatListener.getInstance(), this);
        switch(config.getSharedExperienceMode()) {
            case SpiralPartyConfig.Mode.SPLIT:
                pluginManager.registerEvents(SplitModeExperienceListener.getInstance(), this);
                break;
            case SpiralPartyConfig.Mode.EQUAL:
                pluginManager.registerEvents(EqualModeExperienceListener.getInstance(), this);
                break;
            case SpiralPartyConfig.Mode.PARTY:
                pluginManager.registerEvents(PartyModeExperienceListener.getInstance(), this);
                break;
        }
        switch(config.getSharedInventoryMode()) {
            case SpiralPartyConfig.Mode.SPLIT:
                pluginManager.registerEvents(SplitModeInventoryListener.getInstance(), this);
                break;
            case SpiralPartyConfig.Mode.EQUAL:
                pluginManager.registerEvents(EqualModeInventoryListener.getInstance(), this);
                break;
            case SpiralPartyConfig.Mode.PARTY:
                pluginManager.registerEvents(PartyModeInventoryListener.getInstance(), this);
                break;
        }
    }

    @Override
    public void onDisable() {
        // UNREGISTER LISTENERS
        HandlerList.unregisterAll(this);

        // UNSET COMMAND EXECUTORS AND TABCOMPLETERS
        PluginCommand partyListCommand = this.getCommand("partylist");
        if(partyListCommand != null) {
            partyListCommand.setExecutor(null);
            partyListCommand.setTabCompleter(null);
        }
        PluginCommand partyLeaveCommand = this.getCommand("partyleave");
        if(partyLeaveCommand != null) {
            partyLeaveCommand.setExecutor(null);
            partyLeaveCommand.setTabCompleter(null);
        }
        PluginCommand partyJoinCommand = this.getCommand("partyjoin");
        if(partyJoinCommand != null) {
            partyJoinCommand.setExecutor(null);
            partyJoinCommand.setTabCompleter(null);
        }
        PluginCommand partyInviteCommand = this.getCommand("partyinvite");
        if(partyInviteCommand != null) {
            partyInviteCommand.setExecutor(null);
            partyInviteCommand.setTabCompleter(null);
        }
        PluginCommand partyCommand = this.getCommand("party");
        if(partyCommand != null) {
            partyCommand.setExecutor(null);
            partyCommand.setTabCompleter(null);
        }

        // NULLIFY CONFIG INSTANCE
        SpiralPartyConfig.nullifyInstance();
    }
}
