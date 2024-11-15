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
                    switch(args[0].toLowerCase()) {
                        case "invite":
                            String[] newArgs = String[args.length - 1];
                            System.arraycopy(args, 2, newArgs, 1, newArgs.length);
                            this.invite(player, newArgs);
                            break;
                        case "join":
                            break;
                        case "leave":
                            break;
                        default:
                            return false;
                    }
                    break;
                case "partyinvite":
                    this.invite(player, args);
                    break;
                case "partyjoin":
                    this.join(player, args);
                    break;
                case "partyleave":
                    this.leave(player, args);
                    break;
                default:
                    return false;
            }
        } else {
            sender.sendMessage("Only players may use this command");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return List.of();
    }

    private void invite(Player player, String[] args) {
    }

    private void join(Player player, String[] args) {
    }

    private void leave(Player player, String[] args) {
    }

}
