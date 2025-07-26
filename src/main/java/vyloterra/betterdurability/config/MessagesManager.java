package vyloterra.betterdurability.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import vyloterra.betterdurability.BetterDurability;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MessagesManager {

    private static FileConfiguration messages;

    public static void load() {
        BetterDurability plugin = BetterDurability.getInstance();
        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        messages = YamlConfiguration.loadConfiguration(file);

        try (InputStream stream = plugin.getResource("messages.yml")) {
            if (stream != null) {
                YamlConfiguration def = YamlConfiguration.loadConfiguration(new InputStreamReader(stream));
                messages.setDefaults(def);
            }
        } catch (Exception ignored) {}
    }

    public static String get(String path) {
        return messages.getString(path, path);
    }

    public static String parse(String path, int current, int max) {
        return get(path)
                .replace("%current%", String.valueOf(current))
                .replace("%max%", String.valueOf(max));
    }
}
