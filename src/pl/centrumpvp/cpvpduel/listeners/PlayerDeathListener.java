package pl.centrumpvp.cpvpduel.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import pl.centrumpvp.cpvpduel.data.User;
import pl.centrumpvp.cpvpduel.managers.UserManager;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        User user = UserManager.getUser(victim);
        if (user.isFighting()) {
            event.getDrops().clear();
            user.getArena().stopGame();
        }
    }
}
