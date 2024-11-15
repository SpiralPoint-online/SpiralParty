package online.spiralpoint.spigot.spiralparty.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

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
                    String[] newArgs = String[args.length - 1];
                    System.arraycopy(args, 2, newArgs, 1, newArgs.length);
                    switch(args[0].toLowerCase()) {
                        case "invite":
                            return this.onInviteCommand(player, newArgs);
                        case "join":
                            return this.onJoinCommand(player, newArgs);
                        case "leave":
                            return this.onLeaveCommand(player, newArgs);
                        default:
                            return false;
                    }
                    break;
                case "partyinvite":
                    return this.onInviteCommand(player, args);
                case "partyjoin":
                    return this.onJoinCommand(player, args);
                case "partyleave":
                    return this.onLeaveCommand(player, args);
                default:
                    return false;
            }
        }
        sender.sendMessage("Only players may use this command");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            String[] newArgs = String[args.length - 1];
            System.arraycopy(args, 2, newArgs, 1, newArgs.length);
            switch(command.getName().toLowercase()) {
                case "party":
                    if(args.length == 0) return List.of("invite", "join", "leave");
                    switch(args[0].toLowercase()) {
                        case "invite":
                            return this.onInviteComplete(player, newArgs);
                        case "join":
                            return this.onJoinComplete(player, newArgs);
                    }
                    break;
                case "partyinvite":
                    return this.onInviteComplete(player, args);
                case "partyjoin":
                    return this.onJoinComplete(player, args);
            }
        }
        return List.of();
    }

    private boolean onInviteCommand(Player player, String[] args) {
    }

    private boolean onJoinCommand(Player player, String[] args) {
    }

    private boolean onLeaveCommand(Player player, String[] args) {
    }

    private List<String> onInviteComplete(Player player, String[] args) {
        Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        
    }

    private List<String> onJoinComplete(Player player, String[] args) {
    }

    private List<String> onLeaveComplete(Player player, String[] args) {
    }


}
