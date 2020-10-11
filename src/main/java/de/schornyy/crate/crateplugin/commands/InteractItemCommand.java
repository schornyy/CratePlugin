package de.schornyy.crate.crateplugin.commands;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.configs.MessagesConfig;
import de.schornyy.crate.crateplugin.interactableItems.InteractItemManager;
import de.schornyy.crate.crateplugin.interactableItems.InteractItemsUtils;
import de.schornyy.crate.crateplugin.utils.Inventorys;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InteractItemCommand implements CommandExecutor {

    private MessagesConfig messagesConfig = CratePlugin.plugin.getConfigManager().getMessagesConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        if(!player.hasPermission("interactitems." + args[0])) {
            player.sendMessage(messagesConfig.no_Permissions);
            return true;
        }

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("show")) {
                player.openInventory(Inventorys.getInteractItemsShow());
            } else if(args[0].equalsIgnoreCase("index")) {
                player.openInventory(Inventorys.getItemIndexOfInteractItems());
            }
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("remove")) {
                int index = 0;
                try {
                    index = Integer.valueOf(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(messagesConfig.need_a_Number);
                    return true;
                }
                if(InteractItemsUtils.getItemByIndex(index) == null) {
                    player.sendMessage(messagesConfig.interactItem_with_index_dosnt_exists.replace("%index%", "" + index));
                    return true;
                }
                CratePlugin.plugin.getInteractItemManager().removeInteractItem(index);
                player.sendMessage(messagesConfig.interactItem_deleted_byIndex.replace("%index%", "" + index));

            }
        } else if(args.length > 2) {
            if(args[0].equalsIgnoreCase("add")) {
                if(args[1].startsWith("/")) {

                    if(player.getItemInHand() == null) {
                        player.sendMessage(messagesConfig.need_Item_inHand_to_add_Interactitem);
                        return true;
                    }

                    String cmd = "";
                    for(int i = 1; i < args.length;i++) {
                        cmd += args[i] + " ";
                    }

                    CratePlugin.plugin.getInteractItemManager().addInteractItem(player.getItemInHand(), cmd);
                    player.sendMessage(messagesConfig.added_interactitem.replace("%index%", "" + CratePlugin.plugin.getInteractItemManager().getGlobalIndex())
                            .replace("%command%", cmd));
                }
            }
        } else if(args.length == 3) {
            if(args[0].equalsIgnoreCase("give")) {
                if(Bukkit.getPlayer(args[1]) == null) {
                    player.sendMessage(messagesConfig.player_isNot_Online);
                    return true;
                }

                int index = 0;
                try {
                    index = Integer.valueOf(args[2]);
                } catch (NumberFormatException e) {
                    player.sendMessage(messagesConfig.need_a_Number);
                    return true;
                }

                if(InteractItemsUtils.getItemByIndex(index) == null) {
                    player.sendMessage(messagesConfig.interactItem_with_index_dosnt_exists);
                    return true;
                }

                Player playerTarget = Bukkit.getPlayer(args[1]);
                playerTarget.getInventory().addItem(InteractItemsUtils.getItemByIndex(index).getItemStack());
                player.sendMessage(messagesConfig.give_player_InteractItem
                        .replace("%player%", playerTarget.getName()
                        .replace("%index%", "" + index)));
            }
        }



        return false;
    }
}
