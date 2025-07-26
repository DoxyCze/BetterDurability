package vyloterra.betterdurability;

import org.bukkit.plugin.java.JavaPlugin;
import vyloterra.betterdurability.config.ConfigManager;
import vyloterra.betterdurability.config.MessagesManager;
import vyloterra.betterdurability.commands.DurabilityCommand;
import vyloterra.betterdurability.commands.BDCommand;
import vyloterra.betterdurability.listener.DurabilityListener;
import vyloterra.betterdurability.placeholder.BetterDurabilityExpansion;

public class BetterDurability extends JavaPlugin {

    private static BetterDurability instance;

    @Override
    public void onEnable() {
        instance = this;

        // Načtení configu a zpráv
        saveDefaultConfig();
        ConfigManager.load();
        MessagesManager.load();

        // Log
        getLogger().info(MessagesManager.get("plugin.enabled"));

        // Registrace listeneru a příkazů
        getServer().getPluginManager().registerEvents(new DurabilityListener(), this);
        new DurabilityCommand().register();
        new BDCommand().register();

        // PlaceholderAPI expansion
        new BetterDurabilityExpansion(this).register();
    }

    @Override
    public void onDisable() {
        getLogger().info(MessagesManager.get("plugin.disabled"));
    }

    public static BetterDurability getInstance() {
        return instance;
    }
}
