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

    public static void saveInventory(Player player) throws IOException {
        YamlConfiguration c = new YamlConfiguration();
        c.set("inventory.armor", player.getInventory().getArmorContents());
        c.set("inventory.content", player.getInventory().getContents());
        c.save(getPlayerFile(player));
    }

    public static ItemStack[] loadInventory(Player player) {
        YamlConfiguration c = YamlConfiguration.loadConfiguration(getPlayerFile(player));
        ItemStack[] content = ((List<ItemStack>) c.get("inventory.armor")).toArray(new ItemStack[0]);
        player.getInventory().setArmorContents(content);
        content = ((List<ItemStack>) c.get("inventory.content")).toArray(new ItemStack[0]);

        return content;
    }

    public static File getPlayerFile(Player player) {
        String uuid = player.getUniqueId().toString();

        return new File(plugin.getDataFolder(), "/playerdata/" + uuid + "/inventories.yml");
    }

    public static boolean playerDataExists(Player player) {
        String uuid = player.getUniqueId().toString();
        File playerData = getPlayerFile(player);

        return playerData.exists();
    }

    public static void saveAllInventories() throws IOException {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            saveInventory(player);
        }
    }
}
