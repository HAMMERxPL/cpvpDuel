package pl.centrumpvp.cpvpduel.data;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import pl.centrumpvp.cpvpduel.Main;
import pl.centrumpvp.cpvpduel.enums.KitType;
import pl.centrumpvp.cpvpduel.managers.UserManager;
import pl.centrumpvp.cpvpduel.utils.ChatUtil;

public class Arena {

	private KitType kit;
	private Location spawn1;
	private Location spawn2;
	private Player player1;
	private Player player2;
	private User user1;
	private User user2;
	private boolean started;

	public Arena(KitType kit) {
		this.kit = kit;
		;
		this.started = false;
	}

	public KitType getKitType() {
		return kit;
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
		return user == user1 ? user2 : user1;
	}

	public boolean isStarted() {
		return started;
	}

	public void setKitType(KitType kit) {
		this.kit = kit;
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

	public void prepareGame(Player first, Player second) {
		this.started = true;
		this.player1 = first;
		this.player2 = second;
		this.user1 = UserManager.getUser(player1);
		this.user2 = UserManager.getUser(player2);
		this.user1.setArena(this);
		this.user2.setArena(this);
		player1.teleport(this.spawn1);
		player2.teleport(this.spawn2);
		ChatUtil.sendColored(player1, "&3Walka rozpocznie sie za 10 sekund! Przygotuj sie!");
		ChatUtil.sendColored(player1, "&3Walka rozpocznie sie za 10 sekund! Przygotuj sie!");
		giveKit();
		startCountdown();
	}

	public void startGame() {
		ChatUtil.sendColored(player1, "&3Walka sie rozpoczyna! Powodzenia!");
		ChatUtil.sendColored(player2, "&3Walka sie rozpoczyna! Powodzenia!");
	}

	public void stopGame() {
		this.started = false;
		this.player1 = null;
		this.player2 = null;
		this.user1 = null;
		this.user2 = null;
	}

	public void giveKit() {
		switch (kit) {
		case HARD: {
			player1.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD, 1));
			player2.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD, 1));
			break;
		}
		default: 
			break;
		}
	}

	public void startCountdown() {
		new BukkitRunnable() {
			int run = 10;

			public void run() {
				if (run <= 3) {
					player1.playSound(player1.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
					player2.playSound(player2.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
				}
				player1.setLevel(run);
				player2.setLevel(run);
				if (run > 0) {
					if (player1.getLocation().distance(spawn1) > 1) {
						player1.teleport(spawn1);
						ChatUtil.sendColored(player1, "&cGra jeszcze sie nie rozpoczela!");
					}
					if (player2.getLocation().distance(spawn2) > 1) {
						player2.teleport(spawn2);
						ChatUtil.sendColored(player2, "&cGra jeszcze sie nie rozpoczela!");
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
