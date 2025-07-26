package vyloterra.betterdurability.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import vyloterra.betterdurability.BetterDurability;

public class BetterDurabilityExpansion extends PlaceholderExpansion {

    private final BetterDurability plugin;

    public BetterDurabilityExpansion(BetterDurability plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return plugin.getServer()
                .getPluginManager()
                .isPluginEnabled("PlaceholderAPI");
    }

    @Override
    public String getAuthor() {
        return "Michal";
    }

    @Override
    public String getIdentifier() {
        return "betterdurability";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) return "";

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType().getMaxDurability() <= 0) {
            return "";
        }
        Damageable meta = (Damageable) item.getItemMeta();
        int max     = item.getType().getMaxDurability();
        int current = max - meta.getDamage();

        switch (identifier) {
            case "current": return String.valueOf(current);
            case "max":     return String.valueOf(max);
            case "percent": return String.valueOf((current * 100) / max);
            default:        return null;
        }
    }
}
