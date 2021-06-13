package me.fertiz.reward.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtil {

    public static String fixColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text).replace(">>", "»").replace("<<", "«");
    }

    public static void sendMessage(Player p, String text) {
        p.sendMessage(fixColor(text));
    }

    public static void sendTitle(Player p, String subtitle) {
        p.sendTitle(fixColor("&8✰ &6NAGRODA &8✰"), fixColor(subtitle));
    }
}
