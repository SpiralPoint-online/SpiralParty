package online.spiralpoint.spigot.spiralparty;

import org.bukkit.plugin.java.JavaPlugin;

public final class SpiralPartyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
	this.saveDefaultConfig();
        SpiralPartyConfig.get(); // Initialize Config Class Singleton, do nothing with return value.
	PluginCommand partyCommand = this.getCommand("party");
    }

    @Override
    public void onDisable() {
        SpiralPartyConfig.nullify();
    }
}
