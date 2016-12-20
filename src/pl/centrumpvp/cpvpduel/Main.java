package pl.centrumpvp.cpvpduel;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pl.centrumpvp.cpvpduel.commands.DuelCommand;
import pl.centrumpvp.cpvpduel.listeners.PlayerDeathListener;
import pl.centrumpvp.cpvpduel.listeners.PlayerJoinQuitListener;
import pl.centrumpvp.cpvpduel.managers.ArenaManager;

public class Main extends JavaPlugin {
	
	private static Main instance;
	
	public void onEnable() {
		instance = this;
		registerListeners();
		getCommand("duel").setExecutor(new DuelCommand());
		ArenaManager.register();
	}
	
	private void registerListeners() {
		registerListeners(
				new PlayerDeathListener(),
				new PlayerJoinQuitListener());
	}
	
	private void registerListeners(Listener... listeners) {
		PluginManager pluginManager = Bukkit.getPluginManager();
		for (Listener listener : listeners) {
			pluginManager.registerEvents(listener, this);
		}
	}

	public static Main getInstance() {
		return instance;
	}
}
