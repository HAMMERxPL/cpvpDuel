package pl.centrumpvp.cpvpduel.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pl.centrumpvp.cpvpduel.data.Arena;
import pl.centrumpvp.cpvpduel.data.User;
import pl.centrumpvp.cpvpduel.enums.KitType;
import pl.centrumpvp.cpvpduel.managers.ArenaManager;
import pl.centrumpvp.cpvpduel.managers.UserManager;
import pl.centrumpvp.cpvpduel.utils.ChatUtil;

public class DuelCommand implements CommandExecutor {
	
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
    	if (!(sender instanceof Player)) {
    		return true;
    	}
    	Player player = (Player) sender;
    	if (args.length < 2) {
    		return ChatUtil.sendColored(player, "&3Prawidlowe uzycie: &b/duel <nick> <zestaw>");
    	}
    	Player requested = Bukkit.getPlayer(args[0]);
    	if (requested == null) {
    		return ChatUtil.sendColored(player, "&cPodany gracz nie jest online.");
    	}
    	if (requested == player) {
    		return ChatUtil.sendColored(player, "&cNie mozesz wyzwac siebie do pojedynku.");
    	}
    	KitType kit = KitType.getKitType(args[1]);
    	if (kit == null) {
    		ChatUtil.sendColored(player, "&cPodany zestaw nie istnieje.");
    		return ChatUtil.sendColored(player, "&cDostepne zestawy: &6HARD&c.");
    	}
    	Arena arena = ArenaManager.getArena(kit);
    	if (arena == null) {
    		return ChatUtil.sendColored(player, "&cArena z wybranym zestawem nie istnieje!");
    	}
    	if (arena.isStarted()) {
    		return ChatUtil.sendColored(player, "&cGra na wybranej arenie trwa! Poczekaj na jej zakonczenie!");
    	}
    	User user = UserManager.getUser(player);
    	User requestedUser = UserManager.getUser(requested);
    	if (!requestedUser.addRequest(player, kit)) {
    		return ChatUtil.sendColored(player, "&cPodany gracz dostal juz od ciebie zaproszenie!");
    	}
    	if (user.canFight(requestedUser, kit)) {
    		arena.prepareGame(player, requested);
    		return true;
    	}
    	ChatUtil.sendColored(requested, "&3Gracz &b" + player.getName() + " &3 wyzywa cie do walki! Wpisz &b/duel " + player.getName() + " " + kit.name() +  "&3, aby przyjac zaproszenie!");
    	return ChatUtil.sendColored(player, "&3Wyzwales gracza &b" + requested.getName() + " &3do walki!");
    }
}
