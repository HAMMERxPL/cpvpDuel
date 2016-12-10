package pl.centrumpvp.cpvpduel.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatUtil {

    public static boolean send(CommandSender sender, String message, String permission) {
        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage(fixColors(message));
            return true;
        }
        if (permission == null) {
        	sender.sendMessage(fixColors(message));
        }
        return true;
    }
    
    public static boolean send(CommandSender sender, String message) {
        return send(sender, message, null);
    }
    
    public static boolean sendColored(CommandSender sender, String message) {
        return send(sender, fixColors(message), null);
    }
    
    public static boolean send(Collection<? extends CommandSender> senders, String message, String permission) {
    	message = fixColors(message);
        send(Bukkit.getConsoleSender(), message, permission);
        for (CommandSender sender : senders) {
            send(sender, message, permission);
        }
        return true;
    }
    
    public static boolean send(Collection<? extends CommandSender> senders, String message) {
    	message = fixColors(message);
        for (CommandSender sender : senders) {
            send(sender, message, null);
        }
        return true;
    }
    
    public static boolean broadcast(String message) {
    	Bukkit.broadcastMessage(fixColors(message));
        return true;
    }
    
    public static String stripColor(String string) {
        return ChatColor.stripColor(fixColors(string));
    }
    
    public static String fixColors(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
    
    public static List<String> fixColors(List<String> strings) {
        List<String> colors = new ArrayList<String>();
        for (String s : strings) {
            colors.add(fixColors(s));
        }
        return colors;
    }
}
