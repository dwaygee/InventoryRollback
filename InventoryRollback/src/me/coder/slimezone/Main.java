package me.coder.slimezone;

import me.coder.slimezone.commands.InventoryRollbackCommand;
import me.coder.slimezone.listeners.OnPlayerJoinEvent;
import me.coder.slimezone.listeners.OnPlayerLeaveEvent;
import me.coder.slimezone.utils.InventoryUtils;
import me.coder.slimezone.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class Main extends JavaPlugin {

    private final String prefix = "&7[&aInventoryRollback&7]&r ";

    @Override
    public void onEnable() {
        /*
        Checks if the plugin's config folder exists
        if not it creates a new one
         */
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        //Registers all plugin listeners
        getServer().getPluginManager().registerEvents(new OnPlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerLeaveEvent(), this);
        //Registers all plugin commands
        getCommand("inventoryrollback").setExecutor(new InventoryRollbackCommand());

        new BukkitRunnable() {

            @Override
            public void run() {
                Utils.sendConsoleMessage(prefix + "Saving all player inventories...");
                try {
                    InventoryUtils.saveAllInventories();
                } catch (IOException e) {
                    e.printStackTrace();
                    Utils.sendConsoleMessage("&cUnable to save all player inventories!");
                }
                Utils.sendConsoleMessage(prefix + "&aSuccessfully saved all player inventories!");
            }
        }.runTaskTimer(this, 0, 20 * 60 * 30);

        Utils.sendConsoleMessage(prefix + "Loaded!");
    }

    public void onDisable() {
        try {
            InventoryUtils.saveAllInventories();
        } catch (IOException e) {
            e.printStackTrace();
            Utils.sendConsoleMessage(prefix + "&cUnable to save all inventories!");
        }
        Utils.sendConsoleMessage(prefix + "Unloaded!");
    }
}
