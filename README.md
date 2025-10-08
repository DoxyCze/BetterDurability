# 🧩 BetterDurability – Developer API

BetterDurability poskytuje otevřené **API pro vývojáře pluginů**, které umožňuje:
- reagovat na změny durability,
- detekovat nízký stav odolnosti,
- zachytávat opravy (Mending, Anvil, Smithing),
- nebo upravovat chování pluginu pomocí vlastních event listenerů.

---

## ⚙️ Základní registrace Listeneru

Vytvoř si vlastní třídu (např. `MyDurabilityListener.java`) a registruj ji v `onEnable()`:
```java
package my.plugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import vyloterra.betterdurability.api.*;

public class MyDurabilityListener implements Listener {

    public MyDurabilityListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    // 🩸 1. Každá změna durability (poškození nebo oprava)
    @EventHandler
    public void onDurabilityChange(PlayerDurabilityChangeEvent e) {
        e.getPlayer().sendMessage("§7Durabilita změněna: " + e.getOldValue() + " → " + e.getNewValue());
    }

    // ⚠️ 2. Když durabilita klesne pod práh varování
    @EventHandler
    public void onDurabilityLow(PlayerDurabilityLowEvent e) {
        e.getPlayer().sendMessage("§cTvůj item má jen " + e.getCurrent() + " / " + e.getMax() + " durability!");
        e.setCancelled(true); // zruší výchozí varování pluginu
    }

    // 💚 3. Když se item opraví
    @EventHandler
    public void onDurabilityRestore(PlayerDurabilityRestoreEvent e) {
        e.getPlayer().sendMessage("§aItem opraven pomocí: §e" + e.getMethod());
    }
}
