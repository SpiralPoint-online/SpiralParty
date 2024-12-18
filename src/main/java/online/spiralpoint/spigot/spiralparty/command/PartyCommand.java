package online.spiralpoint.spigot.spiralparty.command;

import online.spiralpoint.spigot.spiralparty.party.SpiralParty;
import online.spiralpoint.spigot.spiralparty.party.SpiralPartyManager;
import online.spiralpoint.spigot.spiralparty.party.invite.SpiralInviteManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class PartyCommand implements TabExecutor {

    private static PartyCommand INSTANCE;

    public static PartyCommand getInstance() {
        if(PartyCommand.INSTANCE == null) PartyCommand.INSTANCE = new PartyCommand();
        return PartyCommand.INSTANCE;
    }

    private PartyCommand() {}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            switch(command.getName().toLowerCase()) {
                case "party":
                    if(args.length == 0) return false;
                    String[] newArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, newArgs, 0, newArgs.length);
                    switch(args[0].toLowerCase()) {
                        case "invite":
                            return this.onInviteCommand(player, newArgs);
                        case "join":
                            return this.onJoinCommand(player, newArgs);
                        case "leave":
                            return this.onLeaveCommand(player);
                        case "teleport":
                            return this.onTeleportCommand(player, newArgs);
                        case "list":
                            return this.onListCommand(player, newArgs);
                        default:
                            return false;
                    }
                case "partyinvite":
                    return this.onInviteCommand(player, args);
                case "partyjoin":
                    return this.onJoinCommand(player, args);
                case "partyleave":
                    return this.onLeaveCommand(player);
                case "partyteleport":
                    return this.onTeleportCommand(player, args);
                case "partylist":
                    return this.onListCommand(player, args);
                default:
                    // Should never reach here!
                    return false;
            }
        }
        sender.sendMessage("Only players may use this command");
        return true;
    }

    private boolean onInviteCommand(Player player, String[] args) {
        if(args.length == 0) return false;
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if(!SpiralInviteManager.sendInvite(target, player)) player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lInvitation not sent!"));
        return true;
    }

    private boolean onJoinCommand(Player player, String[] args) {
        if(args.length == 0) return false;
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if(!SpiralPartyManager.joinParty(player, target)) player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lParty not joined!"));
        return true;
    }

    private boolean onLeaveCommand(Player player) {
        SpiralPartyManager.leaveParty(player);
        return true;
    }

    private boolean onTeleportCommand(Player player, String[] args) {

        return true;
    }

    private boolean onListCommand(Player player, String[] args) {
        if(args.length == 0) return false;
        switch(args[0].toLowerCase()) {
            case "members":
                player.sendMessage("Party Members are:");
                if(!SpiralPartyManager.hasParty(player)) {
                    player.sendMessage("You are not in a party!");
                    break;
                }
                SpiralParty party = SpiralPartyManager.getParty(player);
                for(Player member : party.getPartyMembers()) {
                    if(!member.getDisplayName().equals(member.getName())) player.sendMessage(String.format("%s (%s)", member.getDisplayName(), member.getName())); else player.sendMessage(member.getName());
                }
                break;
            case "invites":
                player.sendMessage("You've been invited by:");
                List<Player> invitedBy = SpiralInviteManager.getInviteList(player);
                if(invitedBy.isEmpty()) player.sendMessage("You have no invites!");
                for(Player member : invitedBy) {
                    if(!member.getDisplayName().equals(member.getName())) player.sendMessage(String.format("%s (%s)", member.getDisplayName(), member.getName())); else player.sendMessage(member.getName());
                }
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            switch(command.getName().toLowerCase()) {
                case "party":
                    if(args.length <= 1) return List.of("invite", "join", "leave", "teleport", "list");
                    String[] newArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, newArgs, 0, newArgs.length);
                    switch(args[0].toLowerCase()) {
                        case "invite":
                            return this.onInviteComplete(newArgs);
                        case "join":
                            return this.onJoinComplete(player, newArgs);
                        case "teleport":
                            return this.onTeleportComplete(player, newArgs);
                        case "list":
                            return this.onListComplete(newArgs);
                    }
                    break;
                case "partyinvite":
                    return this.onInviteComplete(args);
                case "partyjoin":
                    return this.onJoinComplete(player, args);
                case "partyteleport":
                    return this.onTeleportComplete(player, args);
                case "partylist":
                    return this.onListComplete(args);
            }
        }
        return List.of();
    }

    private List<String> onInviteComplete(String[] args) {
        List<String> result = new ArrayList<>();
        if(args.length <= 1) {
            for(Player onlinePlayer : Bukkit.getOnlinePlayers().toArray(new Player[0])) {
                result.add(onlinePlayer.getName());
            }
        }
        return result;
    }

    private List<String> onJoinComplete(Player player, String[] args) {
        List<String> result = new ArrayList<>();
        if(args.length <= 1) {
            for(Player playerInvited : SpiralInviteManager.getInviteList(player)) {
                result.add(playerInvited.getName());
            }
        }
        return result;
    }

    private List<String> onTeleportComplete(Player player, String[] args) {
        List<String> result = new ArrayList<>();
        if(args.length <= 1 && SpiralPartyManager.hasParty(player)) {
            for(Player member : SpiralPartyManager.getParty(player).getPartyMembers()) {
                if(member.equals(player)) continue;
                result.add(member.getName());
            }
        }
        return List.of();
    }

    private List<String> onListComplete(String[] args) {
        if(args.length <= 1) return List.of("members", "invites");
        return List.of();
    }

}
