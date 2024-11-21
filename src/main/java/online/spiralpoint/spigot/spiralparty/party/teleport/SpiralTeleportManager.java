package online.spiralpoint.spigot.spiralparty.party.teleport;

import online.spiralpoint.spigot.spiralparty.SpiralPartyConfig;
import online.spiralpoint.spigot.spiralparty.SpiralPartyPlugin;
import online.spiralpoint.spigot.spiralparty.party.SpiralPartyManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SpiralTeleportManager {

    private static final ConcurrentMap<Player, BukkitTask> TELEPORT_DELAY_TIMERS = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Player, BukkitTask> TELEPORT_COOLDOWN_TIMERS = new ConcurrentHashMap<>();

    public static boolean queueTeleport(Player player, Player target) {
        if(player == null || target == null || player.equals(target)) return false;
        if(!SpiralPartyManager.hasParty(player)) return false;
        if(!SpiralPartyManager.getParty(player).hasMember(target)) return false;
        if(SpiralTeleportManager.TELEPORT_DELAY_TIMERS.containsKey(player)) return false;
        if(SpiralTeleportManager.TELEPORT_COOLDOWN_TIMERS.containsKey(player)) return false;
        SpiralTeleportManager.TELEPORT_DELAY_TIMERS.put(player, new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(target.getLocation());
                player.sendMessage("Teleported!");
                player.sendMessage("Cooldown Started!");
                SpiralTeleportManager.TELEPORT_DELAY_TIMERS.remove(player);
                SpiralTeleportManager.TELEPORT_COOLDOWN_TIMERS.put(player, new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.sendMessage("Cooldown Complete!");
                        SpiralTeleportManager.TELEPORT_COOLDOWN_TIMERS.remove(player);
                    }
                }.runTaskLater(SpiralPartyPlugin.getPlugin(SpiralPartyPlugin.class), SpiralPartyConfig.getInstance().getPartyTeleportCooldown() * 20L));
            }
        }.runTaskLater(SpiralPartyPlugin.getPlugin(SpiralPartyPlugin.class), SpiralPartyConfig.getInstance().getPartyTeleportDelay() * 20L));
        return true;
    }

    private SpiralTeleportManager() {}
}
