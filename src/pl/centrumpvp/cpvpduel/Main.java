package pl.centrumpvp.cpvpduel;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pl.centrumpvp.cpvpduel.commands.DuelCommand;
import pl.centrumpvp.cpvpduel.configuration.Config;
import pl.centrumpvp.cpvpduel.configuration.Messages;
import pl.centrumpvp.cpvpduel.listeners.*;

public class Main extends JavaPlugin {

    private static Main instance;

    public void onEnable() {
        instance = this;
        Config.load();
        Messages.reload();
        registerListeners();
        getCommand("duel").setExecutor(new DuelCommand());
    }

    private void registerListeners() {
        registerListeners(
                new PlayerDeathListener(),
                new PlayerJoinQuitListener(),
                new PlayerRespawnListener());
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
