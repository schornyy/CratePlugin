package de.schornyy.crate.crateplugin.listener;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.crates.Crate;
import de.schornyy.crate.crateplugin.crates.CrateUtils;
import de.schornyy.crate.crateplugin.player.CratePlayer;
import de.schornyy.crate.crateplugin.player.CratePlayerUtils;
import de.schornyy.crate.crateplugin.utils.Inventorys;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerCloseInventoryListener implements Listener {

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        CratePlayer cratePlayer = CratePlayerUtils.getCratePlayerByPlayer(player);

        if(player.getOpenInventory().getTopInventory() == null) return;

        if(player.getOpenInventory().getTopInventory().getTitle().contains("§eEdit")) {
            String[] split = player.getOpenInventory().getTopInventory().getTitle().split("-");
            String crateInvNmae = split[0];
            Crate crate = CrateUtils.getCrateByInventoryName(crateInvNmae);
            List<Integer> newRollingSlots = new ArrayList<>();

            for(int slot = 0; slot < e.getInventory().getSize();slot++) {
                if(e.getInventory().getItem(slot).getType() == Material.BARRIER) {
                    newRollingSlots.add(slot);
                }
            }

            crate.setRollingSlots(newRollingSlots);

            player.sendMessage(CratePlugin.plugin.getConfigManager().getMessagesConfig().crate_Edited.replace("%crate%", crate.getName()));
        }


        if(cratePlayer.isOpening()) {
            cratePlayer.setOpening(false);
            player.openInventory(Inventorys.getItemFromCrateWithoutAnimation(player, cratePlayer.getCrateRoll_opening().getCrate()));
            cratePlayer.setCrateRoll_opening(null);
        }
    }
}
