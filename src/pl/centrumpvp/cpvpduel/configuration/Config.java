package pl.centrumpvp.cpvpduel.configuration;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import pl.centrumpvp.cpvpapi.utils.LocationUtil;
import pl.centrumpvp.cpvpduel.Main;
import pl.centrumpvp.cpvpduel.data.Arena;
import pl.centrumpvp.cpvpduel.managers.ArenaManager;

public class Config {
    
    public static Location spawn;
    public static int countdown;
    
    public static void load() {
        if (!Main.getInstance().getDataFolder().exists()) {
            Main.getInstance().getDataFolder().mkdir();
        }
        if (!new File(Main.getInstance().getDataFolder(), "config.yml").exists()) {
            Main.getInstance().saveDefaultConfig();
        }
        FileConfiguration cfg = Main.getInstance().getConfig();
        cfg.addDefault("spawn", "world/0.5/66.0/-3.5/-180.14969/-2.8500426");
        cfg.addDefault("countdown", 10);
        cfg.addDefault("arenas.pvp1.spawn1", "world/0.5/66.0/-3.5/-180.14969/-2.8500426");
        cfg.addDefault("arenas.pvp1.spawn2", "world/0.5/66.0/-3.5/-180.14969/-2.8500426");
        Main.getInstance().getConfig().options().copyDefaults(true);
        Main.getInstance().saveConfig();
        reload();
    }

    public static void reload() {
        Main.getInstance().reloadConfig();
        FileConfiguration cfg = Main.getInstance().getConfig();
        spawn = LocationUtil.fromString(cfg.getString("spawn"));
        countdown = cfg.getInt("countdown");
        loadArenas();
    }

    public static void loadArenas() {
        ConfigurationSection section = Main.getInstance().getConfig().getConfigurationSection("arenas");
        for (String s : section.getKeys(false)) {
            ConfigurationSection cs = section.getConfigurationSection(s);
            Arena arena = new Arena(cs.getName());
            arena.setSpawn1(LocationUtil.fromString(cs.getString("spawn1")));
            arena.setSpawn2(LocationUtil.fromString(cs.getString("spawn2")));
            ArenaManager.registerArena(arena);
        }
        Bukkit.getLogger().info("Loaded " + ArenaManager.getArenas().size() + " arenas!");
    }
}
