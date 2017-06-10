package pl.centrumpvp.cpvpduel.data;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import pl.centrumpvp.cpvpduel.Main;
import pl.centrumpvp.cpvpduel.configuration.Config;
import pl.centrumpvp.cpvpduel.configuration.Messages;
import pl.centrumpvp.cpvpduel.enums.KitType;
import pl.centrumpvp.cpvpduel.managers.UserManager;

public class Arena {

    private String name;
    private Location spawn1;
    private Location spawn2;
    private Player player1;
    private Player player2;
    private User user1;
    private User user2;
    private boolean started;

    public Arena(String name) {
        this.name = name;
        this.started = false;
    }

    public String getName() {
        return name;
    }

    public Location getSpawn1() {
        return spawn1;
    }

    public Location getSpawn2() {
        return spawn2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public User getOpponent(User user) {
        return user.equals(user1) ? user2 : user1;
    }

    public boolean isStarted() {
        return started;
    }

    public void setSpawn1(Location spawn1) {
        this.spawn1 = spawn1;
    }

    public void setSpawn2(Location spawn2) {
        this.spawn2 = spawn2;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void prepareGame(Player first, Player second, KitType kit) {
        this.started = true;
        this.player1 = first;
        this.player2 = second;
        this.user1 = UserManager.getUser(player1);
        this.user2 = UserManager.getUser(player2);
        this.user1.setArena(this);
        this.user2.setArena(this);
        player1.teleport(this.spawn1);
        player2.teleport(this.spawn2);
        String message = Messages.GAME_STARTING.replace("{COUNTDOWN}", Integer.toString(Config.countdown));
        player1.sendMessage(message);
        player2.sendMessage(message);
        giveKit(kit);
        startCountdown();
    }

    public void startGame() {
        player1.sendMessage(Messages.GAME_STARTED);
        player2.sendMessage(Messages.GAME_STARTED);
    }

    public void stopGame() {
        player1.sendMessage(Messages.GAME_FINISHED);
        player2.sendMessage(Messages.GAME_FINISHED);
        player1.teleport(Config.spawn);
        player2.teleport(Config.spawn);
        this.user1.getRequests().clear();
        this.user2.getRequests().clear();
        this.user1.setArena(null);
        this.user2.setArena(null);
        this.started = false;
        this.player1 = null;
        this.player2 = null;
        this.user1 = null;
        this.user2 = null;
    }

    public void giveKit(KitType kit) {
        switch (kit) {
            case HARD: {
                ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
                player1.getInventory().clear();
                player2.getInventory().clear();
                player1.getInventory().addItem(sword);
                player2.getInventory().addItem(sword);
                break;
            }
        }
    }

    public void startCountdown() {
        new BukkitRunnable() {
            int run = Config.countdown;

            public void run() {
                if (run <= 3) {
                    player1.playSound(player1.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    player2.playSound(player2.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                }
                if (!player1.isOnline() || !player2.isOnline()) {
                    stopGame();
                    cancel();
                }
                player1.setLevel(run);
                player2.setLevel(run);
                if (run > 0) {
                    if (player1.getLocation().distance(spawn1) > 1) {
                        player1.teleport(spawn1);
                        player1.sendMessage(Messages.GAME_NOT0STARTED);
                    }
                    if (player2.getLocation().distance(spawn2) > 1) {
                        player2.teleport(spawn2);
                        player2.sendMessage(Messages.GAME_NOT0STARTED);
                    }
                    --run;
                } else {
                    cancel();
                    startGame();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }
}
