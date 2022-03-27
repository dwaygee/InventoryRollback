package me.coder.slimezone.commands;

import me.coder.slimezone.Main;
import me.coder.slimezone.utils.InventoryUtils;
import me.coder.slimezone.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class InventoryRollbackCommand implements CommandExecutor {

    private static final Main plugin = Main.getPlugin(Main.class);

    public boolean onCommand(CommandSender sender, Command cmd, String cmdname, String[] args) {
        Player player = (Player) sender;
        if (!(args.length == 0)) {
            switch (args[0]) {
                case "info" -> {
                    if (!(args.length > 2)) {
                        player.sendMessage(Utils.translate("&7&m]------------{&r &aInfo &7&m}------------["));
                        player.sendMessage(Utils.translate("&7Version: &a" + plugin.getDescription().getVersion()));
                        player.sendMessage(Utils.translate("&7Server Version: &a" + plugin.getServer().getBukkitVersion()));
                        player.sendMessage(Utils.translate("&7Author: &aHunter"));
                    } else {
                        player.sendMessage(Utils.translate("&cImproper arguments! Please use /ir help for a list of commands."));
                    }
                }
                case "help" -> {
                    if (!(args.length > 1)) {
                        player.sendMessage(Utils.translate("&7&m]------------{&r &aCommands &7&m}------------["));
                        player.sendMessage(Utils.translate("&7/ir info: &aDisplays all current info of the plugin"));
                        player.sendMessage(Utils.translate("&7/ir help: &aDisplays this help text"));
                        player.sendMessage(Utils.translate("&7/ir save: &aSaves the inventory of all the players on the server"));
                        player.sendMessage(Utils.translate("&7/ir load [player]: &aLoads inventory of the sender or selected player"));
                    } else {
                        player.sendMessage(Utils.translate("&cImproper arguments! Please use /ir help for a list of commands."));
                    }
                }
                case "save" -> {
                    if (args.length == 2) {
                        Player playerToLoad = plugin.getServer().getPlayer(args[1]);
                        if (playerToLoad != null) {
                            try {
                                InventoryUtils.saveInventory(playerToLoad);
                            } catch (IOException e) {
                                e.printStackTrace();
                                player.sendMessage(Utils.translate("&cThere was an error saving that player's inventory. Please check console for full error."));
                                break;
                            }
                            player.sendMessage(Utils.translate("&aSaved &b" + playerToLoad.getDisplayName() + "'s &ainventory!"));
                        }
                    } else if (args.length == 1) {
                        try {
                            InventoryUtils.saveInventory(player);
                        } catch (IOException e) {
                            e.printStackTrace();
                            player.sendMessage("&cThere was an error saving your inventory. Please check console for full error.");
                        }
                        player.sendMessage(Utils.translate("&aSaved your inventory successfully!"));
                    } else {
                        player.sendMessage("&cImproper arguments! Please use /ir help for a list of commands.");
                    }
                }
                case "load" -> {
                    if (args.length == 2) {
                        Player playerToLoad = plugin.getServer().getPlayer(args[2]);
                        if (playerToLoad != null) {
                            if (InventoryUtils.playerDataExists(playerToLoad)) {
                                player.getInventory().setContents(InventoryUtils.loadInventory(playerToLoad));
                                player.sendMessage(Utils.translate("&aLoaded &b" + playerToLoad.getDisplayName() + "'s &ainventory!"));
                                //To do method plz help brain explodinmg
                                player.sendMessage(Utils.translate("&aYour previous inventory has been saved temporarily."));
                            }
                        }
                    } else if (args.length == 1) {
                        player.getInventory().setContents(InventoryUtils.loadInventory(player));
                        player.sendMessage(Utils.translate("&aLoaded your inventory successfully!"));
                    } else {
                        player.sendMessage("&cImproper arguments! Please use /ir help for a list of commands.");
                    }
                }
                default -> player.sendMessage("&cImproper arguments! Please use /ir help for a list of commands.");
            }
        }
        return false;
    }
}
