package online.spiralpoint.spigot.spiralparty;

import org.bukkit.plugin.java.JavaPlugin;

public final class SpiralPartyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        SpiralPartyConfig.get(); // Initialize Config Class Singleton, do nothing with return value.
    }

    @Override
    public void onDisable() {
        SpiralPartyConfig.nullify();
    }
}
