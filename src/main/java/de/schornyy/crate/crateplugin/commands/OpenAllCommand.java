package de.schornyy.crate.crateplugin.commands;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.configs.MessagesConfig;
import de.schornyy.crate.crateplugin.crates.Crate;
import de.schornyy.crate.crateplugin.crates.CrateUtils;
import de.schornyy.crate.crateplugin.player.CratePlayer;
import de.schornyy.crate.crateplugin.utils.Inventorys;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OpenAllCommand implements CommandExecutor {

    private MessagesConfig messagesConfig = CratePlugin.plugin.getConfigManager().getMessagesConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        CratePlayer cratePlayer = CratePlugin.plugin.getCratePlayerManager().getStoredPlayer().get(player);

        if(player.getItemInHand() == null) return true;
        ItemStack itemStack = player.getItemInHand().clone();
        itemStack.setAmount(1);

        if(CrateUtils.getCrateByItemStack(itemStack) == null)return true;

        if(player.getItemInHand().getAmount() == 1) {
            player.sendMessage(messagesConfig.need_more_crates_for_openall);
            return true;
        }

        Crate crate = CrateUtils.getCrateByItemStack(itemStack);

        player.openInventory(Inventorys.openAllCrates(player, crate));
        player.setItemInHand(null);
        return false;
    }
}
