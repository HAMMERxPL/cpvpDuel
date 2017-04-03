package pl.centrumpvp.cpvpduel.managers;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pl.centrumpvp.cpvpduel.data.Arena;

public class ArenaManager {

    private static Map<String, Arena> arenas = new ConcurrentHashMap<>();

    public static Arena getFreeArena() {
        for (Arena arena : getArenas()) {
            if (!arena.isStarted()) {
                return arena;
            }
        }
        return null;
    }
    
    public static void registerArena(Arena arena) {
        arenas.put(arena.getName(), arena);
    }

    public static Collection<Arena> getArenas() {
        return arenas.values();
    }
}
