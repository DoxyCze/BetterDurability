package vyloterra.betterdurability.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.meta.Damageable;
import vyloterra.betterdurability.config.ConfigManager;
import vyloterra.betterdurability.config.MessagesManager;

public class DurabilityListener implements Listener {

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        var item = event.getItem();
        if (item == null) return;

        short max = item.getType().getMaxDurability();
        if (max <= 0) return;

        Player player = event.getPlayer();
        Damageable meta = (Damageable) item.getItemMeta();
        int current = max - meta.getDamage();
        int percent = (current * 100) / max;

        // lore
        var line = Component.text("Durability: " + current + "/" + max);
        if (percent >= ConfigManager.getGreenAbove()) {
            line = line.color(NamedTextColor.GREEN);
        } else if (percent >= ConfigManager.getYellowAbove()) {
            line = line.color(NamedTextColor.YELLOW);
        } else {
            line = line.color(NamedTextColor.RED);
        }
        item.lore(java.util.List.of(line));

        // upozornění
        if (percent <= ConfigManager.getWarnBelow()) {
            String ab = MessagesManager.parse("listener.low-actionbar", current, max);
            String chat = MessagesManager.parse("listener.low-chat", current, max);
            if (ConfigManager.isNotifyActionBar()) {
                player.sendActionBar(Component.text(ab));
            }
            if (ConfigManager.isNotifyChat()) {
                player.sendMessage(chat);
            }
        }
    }
}
