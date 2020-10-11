package de.schornyy.crate.crateplugin.listener;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.crates.Crate;
import de.schornyy.crate.crateplugin.crates.CrateRoll;
import de.schornyy.crate.crateplugin.crates.CrateUtils;
import de.schornyy.crate.crateplugin.interactableItems.InteractItem;
import de.schornyy.crate.crateplugin.interactableItems.InteractItemsUtils;
import de.schornyy.crate.crateplugin.player.CratePlayer;
import de.schornyy.crate.crateplugin.player.CratePlayerUtils;
import de.schornyy.crate.crateplugin.utils.Inventorys;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        CratePlayer cratePlayer = CratePlayerUtils.getCratePlayerByPlayer(p);

        if(p.getItemInHand() == null) {
            return;
        }

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if(CrateUtils.getCrateByItemStack(p.getItemInHand()) != null) {
                Crate crate = CrateUtils.getCrateByItemStack(p.getItemInHand());
                e.setCancelled(true);
                if(cratePlayer.isAllowAnimation()) {
                    cratePlayer.setOpening(true);
                    CrateRoll crateRoll = new CrateRoll(p, crate);
                    crateRoll.roll();
                    cratePlayer.setCrateRoll_opening(crateRoll);
                } else {
                    p.openInventory(Inventorys.getItemFromCrateWithoutAnimation(p, crate));
                }

                if(p.getItemInHand().getAmount() > 1) {
                    p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
                } else {
                    p.setItemInHand(null);
                }
                return;
            }

            if(InteractItemsUtils.getInteractItembyItemStack(p.getItemInHand()) != null) {
                InteractItem interactItem1 = InteractItemsUtils.getInteractItembyItemStack(p.getItemInHand());
                String command = interactItem1.getCommand();
                if(command.contains("%player%")) {
                    command = command.replace("%player%", p.getName());
                }
                command = command.replace("/", "");

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

                if(p.getItemInHand().getAmount() > interactItem1.getItemStack().getAmount()) {
                    p.getItemInHand().setAmount(p.getItemInHand().getAmount() - interactItem1.getItemStack().getAmount());
                } else {
                    p.setItemInHand(null);
                }
                return;
            }

        }

    }
}
