package pl.centrumpvp.cpvpduel.data;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;

import pl.centrumpvp.cpvpduel.enums.KitType;

public class User {

	private UUID uuid;
	private boolean fighting;
	private Arena arena;
	private Map<UUID, KitType> requests;
	
	public User(Player player) {
		this.uuid = player.getUniqueId();
		this.requests = new ConcurrentHashMap<>();
	}

	public UUID getUUID() {
		return uuid;
	}
	
	public boolean isFighting() {
		return fighting;
	}
	
	public Arena getArena() {
		return arena;
	}
	
	public Map<UUID, KitType> getRequests() {
		return requests;
	}
	
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	public void setFighting(boolean fighting) {
		this.fighting = fighting;
	}
	
	public void setArena(Arena arena) {
		this.arena = arena;
	}
	
	public boolean addRequest(UUID request, KitType kit) {
		if (this.requests.containsKey(request) && this.requests.containsValue(kit)) {
			return false;
		}
		this.requests.put(uuid, kit);
		return true;
	}
	
	public boolean addRequest(Player player, KitType kit) {
		if (this.requests.containsKey(player.getUniqueId()) && this.requests.containsValue(kit)) {
			return false;
		}
		this.requests.put(player.getUniqueId(), kit);
		return true;
	}
	
	public boolean canFight(User user, KitType kit) {
		if (this.requests.containsKey(user.getUUID()) && this.requests.containsValue(kit) &&
				user.getRequests().containsKey(this.getUUID()) && user.getRequests().containsValue(kit)) {
			return true;
		}
		return false;
	}
}
