package online.spiralpoint.spigot.spiralparty.command;

import online.spiralpoint.spigot.spiralparty.party.SpiralParty;
import online.spiralpoint.spigot.spiralparty.party.SpiralPartyManager;
import org.bukkit.Bukkit;
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
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            switch(command.getName().toLowerCase()) {
                case "party":
                    if(args.length == 0) return false;
                    String[] newArgs = new String[args.length - 1];
                    System.arraycopy(args, 2, newArgs, 1, newArgs.length);
                    switch(args[0].toLowerCase()) {
                        case "invite":
                            return this.onInviteCommand(player, newArgs);
                        case "join":
                            return this.onJoinCommand(player, newArgs);
                        case "leave":
                            return this.onLeaveCommand(player);
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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            switch(command.getName().toLowerCase()) {
                case "party":
                    if(args.length == 0) return List.of("invite", "join", "leave", "list");
                    String[] newArgs = new String[args.length - 1];
                    System.arraycopy(args, 2, newArgs, 1, newArgs.length);
                    switch(args[0].toLowerCase()) {
                        case "invite":
                            return this.onInviteComplete(newArgs);
                        case "join":
                            return this.onJoinComplete(player, newArgs);
                        case "list":
                            return this.onListComplete(newArgs);
                    }
                    break;
                case "partyinvite":
                    return this.onInviteComplete(args);
                case "partyjoin":
                    return this.onJoinComplete(player, args);
                case "partylist":
                    return this.onListComplete(args);
            }
        }
        return List.of();
    }

    private boolean onInviteCommand(Player player, String[] args) {
        if(args.length == 0) return false;
        Player target = Bukkit.getServer().getPlayer(args[0]);
        SpiralPartyManager.sendInvite(target, player);
        return true;
    }

    private boolean onJoinCommand(Player player, String[] args) {
        if(args.length == 0) return false;
        Player target = Bukkit.getServer().getPlayer(args[0]);
        SpiralPartyManager.joinParty(player, target);
        return true;
    }

    private boolean onLeaveCommand(Player player) {
        SpiralPartyManager.leaveParty(player);
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
                    if(member.equals(player)) continue;
                    if(!member.getDisplayName().equals(member.getName())) player.sendMessage(String.format("%s (%s)", member.getDisplayName(), member.getName())); else player.sendMessage(member.getName());
                }
                break;
            case "invites":
                player.sendMessage("You've been invited by:");
                List<Player> invitedBy = SpiralPartyManager.getInviteList(player);
                if(invitedBy.isEmpty()) player.sendMessage("You have no invites!");
                for(Player member : invitedBy) {
                    if(member.equals(player)) continue;
                    if(!member.getDisplayName().equals(member.getName())) player.sendMessage(String.format("%s (%s)", member.getDisplayName(), member.getName())); else player.sendMessage(member.getName());
                }
                break;
            default:
                return false;
        }
        return true;
    }

    private List<String> onInviteComplete(String[] args) {
        List<String> result = new ArrayList<>();
        if(args.length == 0) {
            for(Player onlinePlayer : Bukkit.getOnlinePlayers().toArray(new Player[0])) {
                result.add(onlinePlayer.getName());
            }
        }
        return result;
    }

    private List<String> onJoinComplete(Player player, String[] args) {
        List<String> result = new ArrayList<>();
        if(args.length == 0) {
            for(Player playerInvited : SpiralPartyManager.getInviteList(player)) {
                result.add(playerInvited.getName());
            }
        }
        return result;
    }

    private List<String> onListComplete(String[] args) {
        if(args.length == 0) return List.of("members", "invites");
        return List.of();
    }

}
