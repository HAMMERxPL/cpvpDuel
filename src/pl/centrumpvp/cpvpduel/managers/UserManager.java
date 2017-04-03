package pl.centrumpvp.cpvpduel.managers;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import pl.centrumpvp.cpvpduel.data.User;

public class UserManager {

    private static Map<UUID, User> users = new ConcurrentHashMap<>();

    public static User getUser(UUID uuid) {
        return users.get(uuid);
    }

    public static User getUser(Player player) {
        return users.get(player.getUniqueId());
    }

    public static User getUser(String name) {
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            return null;
        }
        return users.get(player.getUniqueId());
    }

    public static void createUser(Player player) {
        User user = new User(player);
        users.put(player.getUniqueId(), user);
    }

    public static void removeUser(UUID uuid) {
        users.remove(uuid);
    }

    public static void removeUser(Player player) {
        users.remove(player.getUniqueId());
    }

    public static void removeUser(String name) {
        Player player = Bukkit.getPlayer(name);
        users.remove(player.getUniqueId());
    }

    public static Map<UUID, User> getUsers() {
        return users;
    }
}
