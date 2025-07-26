package vyloterra.betterdurability.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.PlayerCommandExecutor;
import vyloterra.betterdurability.config.ConfigManager;
import vyloterra.betterdurability.config.MessagesManager;

public class BDCommand {

    public void register() {
        new CommandAPICommand("bd")
                .withPermission("betterdurability.reload")
                .withSubcommand(
                        new CommandAPICommand("reload")
                                .withPermission("betterdurability.reload")
                                .executesPlayer((PlayerCommandExecutor) (player, args) -> {
                                    ConfigManager.load();
                                    MessagesManager.load();
                                    player.sendMessage(MessagesManager.get("plugin.reload"));
                                })
                )
                .register();
    }
}
