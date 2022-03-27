package me.coder.slimezone.utils;

import me.coder.slimezone.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class InventoryUtils {

    private static final Main plugin = Main.getPlugin(Main.class);

    //Method for saving a player specific inventory
    public static void saveInventory(Player player) throws IOException {
        YamlConfiguration c = new YamlConfiguration();
        c.set("inventory.armor", player.getInventory().getArmorContents());
        c.set("inventory.content", player.getInventory().getContents());
        c.save(getPlayerFile(player));
    }

    //Method for loading a player specific inventory
    public static ItemStack[] loadInventory(Player player) {
        YamlConfiguration c = YamlConfiguration.loadConfiguration(getPlayerFile(player));
        ItemStack[] content = ((List<ItemStack>) c.get("inventory.armor")).toArray(new ItemStack[0]);
        player.getInventory().setArmorContents(content);
        content = ((List<ItemStack>) c.get("inventory.content")).toArray(new ItemStack[0]);

        return content;
    }

    //Method for retrieving a player specific data file
    public static File getPlayerFile(Player player) {
        String uuid = player.getUniqueId().toString();

        return new File(plugin.getDataFolder(), "/playerdata/" + uuid + "/inventories.yml");
    }

    //Method for checking if a player data file exists
    public static boolean playerDataExists(Player player) {
        String uuid = player.getUniqueId().toString();
        File playerData = getPlayerFile(player);

        return playerData.exists();
    }

    //Method for saving the inventory of every player
    public static void saveAllInventories() throws IOException {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            saveInventory(player);
        }
    }
}
