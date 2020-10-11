package de.schornyy.crate.crateplugin.commands;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.configs.MessagesConfig;
import de.schornyy.crate.crateplugin.crates.Crate;
import de.schornyy.crate.crateplugin.crates.CrateUtils;
import de.schornyy.crate.crateplugin.shop.ShopCrate;
import de.schornyy.crate.crateplugin.shop.ShopUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {

    private MessagesConfig messagesConfig = CratePlugin.plugin.getConfigManager().getMessagesConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        if(args.length == 0) {
            player.openInventory(CratePlugin.plugin.getShop().getInventory());
        } else if(args.length == 4) {
            if(args[0].equalsIgnoreCase("addCrate")) {

                if (!player.hasPermission("shop.addCrate")) {
                    player.sendMessage(messagesConfig.no_Permissions);
                    return true;
                }

                int slot = 0;
                int price = 0;
                try {
                    slot = Integer.valueOf(args[1]);
                    price = Integer.valueOf(args[3]);
                } catch (NumberFormatException e) {
                    player.sendMessage(messagesConfig.need_a_Number);
                    return true;
                }

                if(CrateUtils.getCrateByCrateName(args[2]) == null) {
                    player.sendMessage(messagesConfig.crate_dosnt_exists);
                    return true;
                }
                Crate crate = CrateUtils.getCrateByCrateName(args[2]);
                ShopUtils.addCrateToShop(slot,crate,price);
                player.sendMessage(messagesConfig.add_Crate_to_Shop
                        .replace("%price%", "" + price)
                        .replace("%slot%", "" + slot)
                        .replace("%crate%", crate.getName()));

            } else if(args[1].equalsIgnoreCase("setprice")) {
                if (!player.hasPermission("shop.setprice")) {
                    player.sendMessage(messagesConfig.no_Permissions);
                    return true;
                }

                int price = 0;
                try {
                    price =  Integer.valueOf(args[2]);
                } catch (NumberFormatException e) {
                    player.sendMessage(messagesConfig.need_a_Number);
                    return true;
                }

                if(CrateUtils.getCrateByCrateName(args[0]) == null) {
                    player.sendMessage(messagesConfig.crate_dosnt_exists);
                    return true;
                }

                ShopCrate shopCrate = ShopUtils.getShopCrateByCrate(CrateUtils.getCrateByCrateName(args[0]));
                shopCrate.setPrice(price);
                player.sendMessage(messagesConfig.shopCrate_setPrice
                        .replace("%crate%", shopCrate.getCrate().getName())
                        .replace("%price%", price + ""));
            }
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("remove")) {
                if (!player.hasPermission("shop.remove")) {
                    player.sendMessage(messagesConfig.no_Permissions);
                    return true;
                }
                if(ShopUtils.getShopCrateByName(args[1]) == null) {
                    player.sendMessage(messagesConfig.crate_dosnt_exists);
                    return true;
                }
                ShopUtils.removeCrateFromShop(args[1]);
                player.sendMessage(messagesConfig.remove_Crate_from_Shop.replace("%crate%", args[1]));
            }
        }


        return false;
    }
}
