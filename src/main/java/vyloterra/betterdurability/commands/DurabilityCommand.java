package vyloterra.betterdurability.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.PlayerCommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import vyloterra.betterdurability.config.MessagesManager;

public class DurabilityCommand {

    public void register() {
        new CommandAPICommand("durability")
                .withPermission("betterdurability.command")
                .executesPlayer((PlayerCommandExecutor) (player, args) -> {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    short max = item != null ? item.getType().getMaxDurability() : 0;
                    if (item == null || max <= 0) {
                        player.sendMessage(MessagesManager.get("command.no-tool"));
                        return;
                    }
                    Damageable meta = (Damageable) item.getItemMeta();
                    int current = max - meta.getDamage();
                    String msg = MessagesManager.parse("command.display", current, max);
                    player.sendMessage(msg);
                })
                .register();
    }
}
