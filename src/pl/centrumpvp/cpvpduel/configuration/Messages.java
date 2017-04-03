package pl.centrumpvp.cpvpduel.configuration;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import pl.centrumpvp.cpvpapi.utils.ChatUtil;
import pl.centrumpvp.cpvpapi.utils.ConfigurationUtil;
import pl.centrumpvp.cpvpduel.Main;

public class Messages {

    private static File file = new File(Main.getInstance().getDataFolder(), "messages.yml");
    private static FileConfiguration c;

    public static String PREFIX = "&8[&6Duel&8] ";

    public static String DUEL_USAGE = PREFIX + "&3Prawidlowe uzycie: &b/duel <gracz> <zestaw>";
    public static String DUEL_ASKED = PREFIX + "&3Wyzwano gracza &b{PLAYER} &3do walki z zestawem &b{KIT}&3.";
    public static String DUEL_GOT_MESSAGE = PREFIX
            + "&3Gracz &b{PLAYER} &3 wyzywa cie do walki! Wpisz &b/duel {PLAYER} {KIT}&3, aby przyjac zaproszenie!";
    public static String DUEL_YOURSELF = PREFIX + "&cNie mozesz wyzwac siebie do pojedynku.";
    public static String DUEL_ALREADY0ASKED = PREFIX + "&cPodany gracz otrzymal juz od ciebie zaproszenie!";

    public static String GAME_STARTING = PREFIX + "&3Walka rozpocznie sie za {COUNTDOWN} sekund! Przygotuj sie!";
    public static String GAME_STARTED = PREFIX + "&3Walka rozpoczyna sie! Powodzenia!";
    public static String GAME_FINISHED = PREFIX + "&3Walka zostala zakonczona!";
    public static String GAME_NOT0STARTED = PREFIX + "&cGra jeszcze sie nie rozpoczela!";
    
    public static String ERROR_PLAYER0NOT0ONLINE = PREFIX + "&cGracz &6{PLAYER} &cnie jest online.";
    public static String ERROR_NO0FREE0ARENAS = PREFIX + "&cNie znaleziono wolnej areny.";
    public static String ERROR_ALREADY0INGAME = PREFIX + "&cW trakcie walki nie mozesz wyzywac do pojedynku.";
    public static String ERROR_WRONG0KIT = PREFIX + "&cZestaw &6{KIT} &cnie istnieje. Dostepne zestawy: &6{KITS}&c.";
    public static String ERROR_PLAYER0NOT0EXISTING = "&8» &cGracza o podanym nicku nie ma w bazie danych.";
    
    public static String CONFIGURATION_RELOADED = PREFIX + "&aKonfiguracja przeladowana pomyslnie.";

    public static void load() {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                InputStream is = Main.getInstance().getResource(file.getName());
                if (is != null) {
                    ConfigurationUtil.copy(is, file);
                }
            }
            c = YamlConfiguration.loadConfiguration(file);
            for (Field f : Messages.class.getFields()) {
                if (!f.getName().startsWith("_")
                        && c.isSet(f.getName().toLowerCase().replace("_", ".").replace("0", "-"))) {
                    f.set(null, ChatUtil.fixColors(
                            c.get(f.getName().toLowerCase().replace("_", ".").replace("0", "-")).toString()));
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void save() {
        try {
            for (Field f : Messages.class.getFields()) {
                c.set(f.getName().toLowerCase().replace("_", ".").replace("0", "-"),
                        f.get(null).toString().replace("§", "&"));
            }
            c.save(file);
            load();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void reload() {
        load();
        save();
    }
}
