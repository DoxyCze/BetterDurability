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
```

V `onEnable()`:
```java
@Override
public void onEnable() {
    new MyDurabilityListener(this);
}
```

---

## 🧱 Popis API Eventů

| Event | Popis | Cancellable | Trigger |
|-------|--------|--------------|----------|
| `PlayerDurabilityChangeEvent` | Volá se při každé změně durability (poškození nebo oprava). | ❌ | Damage, repair, mending |
| `PlayerDurabilityLowEvent` | Volá se, když durabilita klesne pod nastavený práh varování. | ✅ | Pokles pod limit |
| `PlayerDurabilityRestoreEvent` | Volá se při opravě itemu (Mending, Anvil, Smithing). | ✅ | Po opravě durability |

---

## 🧠 Detailní popis eventů

### 🔁 PlayerDurabilityChangeEvent
Volá se při každé změně durability — při poškození i opravě.

| Metoda | Návrat | Popis |
|--------|--------|--------|
| `getPlayer()` | `Player` | Hráč, jehož item se změnil |
| `getItem()` | `ItemStack` | Sledovaný item |
| `getOldValue()` | `int` | Hodnota durability před změnou |
| `getNewValue()` | `int` | Hodnota durability po změně |
| `getPercent()` | `int` | Procento odolnosti po změně |

---

### ⚠️ PlayerDurabilityLowEvent
Volá se, když hráčův item klesne pod jeho nastavený varovný práh.

| Metoda | Návrat | Popis |
|--------|--------|--------|
| `getPlayer()` | `Player` | Hráč, který drží item |
| `getItem()` | `ItemStack` | Sledovaný item |
| `getCurrent()` | `int` | Aktuální durabilita |
| `getMax()` | `int` | Maximální hodnota itemu |
| `getThreshold()` | `int` | Nastavený práh varování |
| `isCancelled()` / `setCancelled()` | `boolean` | Můžeš zrušit výchozí varování |

---

### 💚 PlayerDurabilityRestoreEvent
Volá se při opravě itemu (např. Mending, Anvil, Smithing).

| Metoda | Návrat | Popis |
|--------|--------|--------|
| `getPlayer()` | `Player` | Hráč, který opravil item |
| `getItem()` | `ItemStack` | Opravený item |
| `getMethod()` | `String` | Typ opravy (`mending`, `anvil`, `smithing`) |
| `isCancelled()` / `setCancelled()` | `boolean` | Můžeš zrušit výchozí akci nebo opravu |

---

## ⚡ Příklad využití v jiném pluginu
```java
@EventHandler
public void onLowDurability(PlayerDurabilityLowEvent e) {
    Player p = e.getPlayer();
    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.2f);
    p.sendTitle("§cPOZOR!", "§7Tvůj item je téměř zničen!", 5, 30, 10);
    e.setCancelled(true); // vypne výchozí varování
}
```

---

## 📂 Doporučená struktura složek
```
src/main/java/vyloterra/betterdurability/api/
 ├── PlayerDurabilityChangeEvent.java
 ├── PlayerDurabilityLowEvent.java
 └── PlayerDurabilityRestoreEvent.java
```

---

## 💬 Shrnutí

| Funkce | Popis |
|--------|--------|
| 📡 API Eventy | Kompletní systém událostí pro sledování durability |
| 🔧 Rozšiřitelnost | Možnost napojení jiných pluginů bez úprav kódu |
| 🧱 Cíl | Poskytnout vývojářům přístup ke všem změnám durability hráče |
| 💬 Kompatibilita | Funguje i bez závislostí, kompatibilní s Paper 1.21+ |
| 🧩 Balíček | `vyloterra.betterdurability.api` |

---

## 🧑‍💻 Autor
**Projekt:** BetterDurability  
**Autor:** [Vyloterra Development](https://github.com/DoxyCze)  
**Licence:** MIT  
**Verze:** 1.0.0  
