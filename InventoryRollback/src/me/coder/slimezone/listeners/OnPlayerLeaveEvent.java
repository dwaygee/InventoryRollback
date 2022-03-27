package me.coder.slimezone.listeners;

import me.coder.slimezone.Main;
import me.coder.slimezone.utils.InventoryUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;

public class OnPlayerLeaveEvent implements Listener {

    private static final Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void OnPlayerLeave(PlayerQuitEvent e) throws IOException {

        String uuid = e.getPlayer().getUniqueId().toString();

        //Creates a data file for the joined player
        File player = new File(plugin.getDataFolder() + "/playerdata", uuid);
        File inventoryFile = new File(plugin.getDataFolder() + "/playerdata/" + uuid, "inventories.yml");
        if (!player.exists()) {
            player.mkdirs();
            inventoryFile.createNewFile();
        }

        //Saves the player's inventory on every join
        InventoryUtils.saveInventory(e.getPlayer());
    }
}
