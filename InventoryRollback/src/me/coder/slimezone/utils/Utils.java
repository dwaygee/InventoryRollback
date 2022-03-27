package me.coder.slimezone.utils;

import me.coder.slimezone.Main;
import org.bukkit.ChatColor;

public class Utils {

    private static final Main plugin = Main.getPlugin(Main.class);

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendConsoleMessage(String message) {
        plugin.getServer().getConsoleSender().sendMessage(translate(message));
    }
}
