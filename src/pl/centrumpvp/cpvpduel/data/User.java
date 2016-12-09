package pl.centrumpvp.cpvpduel.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

public class User {

	private UUID uuid;
	private List<UUID> requests;
	
	public User(Player player) {
		this.uuid = player.getUniqueId();
		this.requests = new ArrayList<>();
	}

	public UUID getUuid() {
		return uuid;
	}
	
	public List<UUID> getRequests() {
		return requests;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	public boolean addRequest(UUID request) {
		if (this.requests.contains(request)) {
			return false;
		}
		this.requests.add(uuid);
		return true;
	}
	
	public boolean addRequest(Player player) {
		if (this.requests.contains(player.getUniqueId())) {
			return false;
		}
		this.requests.add(player.getUniqueId());
		return true;
	}
}
