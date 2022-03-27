package me.coder.slimezone.listeners;

import me.coder.slimezone.Main;
import me.coder.slimezone.utils.InventoryUtils;
import me.coder.slimezone.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class OnPlayerJoinEvent implements Listener {

    private static final Main plugin = Main.getPlugin(Main.class);
    private final String prefix = "&7[&aInventoryRollback&7]&r ";

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws IOException {

        String uuid = e.getPlayer().getUniqueId().toString();

        File player = new File(plugin.getDataFolder() + "/playerdata", uuid);
        File inventoryFile = new File(plugin.getDataFolder() + "/playerdata/" + uuid, "inventories.yml");
        if (!player.exists()) {
            player.mkdirs();
            inventoryFile.createNewFile();
            Utils.sendConsoleMessage(prefix + "&aCreating new player data folder for " + e.getPlayer().getDisplayName() + "...");
        }

        InventoryUtils.saveInventory(e.getPlayer());
    }
}
