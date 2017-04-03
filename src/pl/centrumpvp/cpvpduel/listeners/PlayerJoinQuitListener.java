package pl.centrumpvp.cpvpduel.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import pl.centrumpvp.cpvpduel.data.User;
import pl.centrumpvp.cpvpduel.managers.UserManager;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UserManager.createUser(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);
        if (user.isFighting()) {
            user.getArena().stopGame();
        }
        UserManager.removeUser(player);
    }
}
