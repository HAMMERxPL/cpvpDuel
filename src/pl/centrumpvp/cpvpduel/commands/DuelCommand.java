package pl.centrumpvp.cpvpduel.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.centrumpvp.cpvpduel.configuration.Messages;
import pl.centrumpvp.cpvpduel.data.Arena;
import pl.centrumpvp.cpvpduel.data.User;
import pl.centrumpvp.cpvpduel.enums.KitType;
import pl.centrumpvp.cpvpduel.managers.ArenaManager;
import pl.centrumpvp.cpvpduel.managers.UserManager;

public class DuelCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (args.length < 2) {
            player.sendMessage(Messages.DUEL_USAGE);
            return true;
        }
        Player requested = Bukkit.getPlayer(args[0]);
        if (requested == null) {
            player.sendMessage(Messages.ERROR_PLAYER0NOT0ONLINE);
            return true;
        }
        if (requested == player) {
            player.sendMessage(Messages.DUEL_YOURSELF);
            return true;
        }
        KitType kit = KitType.getKitType(args[1]);
        if (kit == null) {
            player.sendMessage(Messages.ERROR_WRONG0KIT
                    .replace("{KIT}", args[1])
                    .replace("{KITS}", KitType.getKitTypes()));
            return true;
        }
        Arena arena = ArenaManager.getFreeArena();
        if (arena == null) {
            player.sendMessage(Messages.ERROR_NO0FREE0ARENAS);
            return true;
        }
        User user = UserManager.getUser(player);
        if (user.isFighting()) {
            player.sendMessage(Messages.ERROR_ALREADY0INGAME);
            return true;
        }
        User requestedUser = UserManager.getUser(requested);
        if (!requestedUser.addRequest(player, kit)) {
            player.sendMessage(Messages.DUEL_ALREADY0ASKED);
            return true;
        }
        if (user.canFight(requestedUser, kit)) {
            arena.prepareGame(player, requested, kit);
            return true;
        }
        requested.sendMessage(Messages.DUEL_GOT_MESSAGE
                .replace("{PLAYER}", player.getName())
                .replace("{KIT}", kit.getName()));
        player.sendMessage(Messages.DUEL_ASKED
                .replace("{PLAYER}", requested.getName())
                .replace("{KIT}", kit.getName()));
        return true;
    }
}
