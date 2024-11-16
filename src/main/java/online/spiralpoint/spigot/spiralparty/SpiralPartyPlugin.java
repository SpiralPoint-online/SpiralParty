package online.spiralpoint.spigot.spiralparty;

import online.spiralpoint.spigot.spiralparty.command.PartyCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpiralPartyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
	    this.saveDefaultConfig();
        SpiralPartyConfig.get(); // Initialize Config Class Singleton, do nothing with return value.

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
    }

    @Override
    public void onDisable() {
        SpiralPartyConfig.nullify();
    }
}
