package pl.centrumpvp.cpvpduel.managers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import pl.centrumpvp.cpvpduel.data.Arena;
import pl.centrumpvp.cpvpduel.enums.KitType;

public class ArenaManager {
	
	private static Map<KitType, Arena> arenas = new ConcurrentHashMap<>();
	
	public static Arena getArena(KitType kit) {
		return arenas.get(kit);
	}
	
	public static void register() {
		Arena arena = new Arena(KitType.HARD);
		arena.setSpawn1(new Location(Bukkit.getWorlds().get(0), 21, 62, -1));
		arena.setSpawn2(new Location(Bukkit.getWorlds().get(0), 11, 62, -8));
		arenas.put(KitType.HARD, arena);
	}
	
	public static Map<KitType, Arena> getArenas() {
		return arenas;
	}
}
