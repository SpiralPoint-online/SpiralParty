package online.spiralpoint.spigot.spiralparty;

import org.bukkit.plugin.java.JavaPlugin;

//TODO: Change all player.sendMessage method calls to use ChatColor.translateAlternateColorCode instead of ChatColor enums directly.
public final class SpiralPartyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
					this.saveDefaultConfig();
        SpiralPartyConfig.get(); // Initialize Config Class Singleton, do nothing with return value.
    }

    @Override
    public void onDisable() {
        SpiralPartyConfig.nullify();
    }
}
