package de.schornyy.crate.crateplugin.listener;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.crates.Crate;
import de.schornyy.crate.crateplugin.crates.CrateUtils;
import de.schornyy.crate.crateplugin.player.CratePlayer;
import de.schornyy.crate.crateplugin.player.CratePlayerUtils;
import de.schornyy.crate.crateplugin.shop.ShopCrate;
import de.schornyy.crate.crateplugin.shop.ShopUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerInteractInventoryListener implements Listener {

    @EventHandler
    public void onInteractInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if(player.getOpenInventory().getTopInventory() == null) return;
        if(player.getOpenInventory().getTopInventory().getTitle().equalsIgnoreCase("§5Interactitems Show")){ e.setCancelled(true); return;}
        if(player.getOpenInventory().getTopInventory().getTitle().contains("Index")) { e.setCancelled(true); return;}
        if(player.getOpenInventory().getTopInventory().getTitle().equalsIgnoreCase("§5Index of InteractableItems")){ e.setCancelled(true); return;}

        if(CrateUtils.getCrateByInventoryName(player.getOpenInventory().getTopInventory().getTitle()) != null) {
            e.setCancelled(true);
            Crate crate = CrateUtils.getCrateByInventoryName(player.getOpenInventory().getTopInventory().getTitle());
            if(e.getSlot() == crate.getWinningSlot()) {
                e.setCancelled(false);
            }

        }

        if(player.getOpenInventory().getTopInventory().getTitle().equalsIgnoreCase("§eSettings")) {
            CratePlayer cratePlayer = CratePlugin.plugin.getCratePlayerManager().getStoredPlayer().get(player);
            e.setCancelled(true);

            if (e.getSlot() == 4) {
                if (cratePlayer.isAllowAnimation()) {
                    cratePlayer.setAllowAnimation(false);
                } else {
                    cratePlayer.setAllowAnimation(true);
                }
                player.getOpenInventory().getTopInventory().setItem(4, CratePlayerUtils.getItemBySettings(cratePlayer));
                return;
            }
        }

        if(player.getOpenInventory().getTopInventory().getTitle().equalsIgnoreCase(CratePlugin.plugin.getShop().getInventoryName())) {
            e.setCancelled(true);

            if(e.getCurrentItem() == null) return;

            if(ShopUtils.getShopCrateByItemStack(e.getCurrentItem()) != null) {
                ShopCrate shopCrate = ShopUtils.getShopCrateByItemStack(e.getCurrentItem());
                Economy economy = CratePlugin.getEcon();
                if(economy.getBalance(player) >= shopCrate.getPrice()) {
                    economy.withdrawPlayer(player, shopCrate.getPrice());
                    player.getInventory().addItem(shopCrate.getCrate().getIcon());
                    player.sendMessage(CratePlugin.plugin.getConfigManager().getMessagesConfig().buy_Crate.replace("%crate%", ShopUtils.getShopCrateByItemStack(e.getCurrentItem()).getCrate().getName()));
                    player.closeInventory();
                } else {
                    player.sendMessage(CratePlugin.plugin.getConfigManager().getMessagesConfig().not_Enough_Money_to_Buy_Crate.replace("%crate%", ShopUtils.getShopCrateByItemStack(e.getCurrentItem()).getCrate().getName()));
                    player.closeInventory();
                    return;
                }

            }

        }

    }
}
