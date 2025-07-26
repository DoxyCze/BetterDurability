package vyloterra.betterdurability.config;

import org.bukkit.configuration.file.FileConfiguration;
import vyloterra.betterdurability.BetterDurability;

public class ConfigManager {

    private static FileConfiguration config;

    public static void load() {
        BetterDurability plugin = BetterDurability.getInstance();
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public static int getGreenAbove() {
        return config.getInt("durability.green-above", 70);
    }

    public static int getYellowAbove() {
        return config.getInt("durability.yellow-above", 30);
    }

    public static boolean isNotifyActionBar() {
        return config.getBoolean("notifications.actionbar", true);
    }

    public static boolean isNotifyChat() {
        return config.getBoolean("notifications.chat", false);
    }

    public static int getWarnBelow() {
        return config.getInt("notifications.warn-below", 10);
    }
}
