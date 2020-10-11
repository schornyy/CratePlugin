package de.schornyy.crate.crateplugin.player;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CratePlayerUtils {

    public static CratePlayer getCratePlayerByPlayer(Player player) {
        return CratePlugin.plugin.getCratePlayerManager().getStoredPlayer().get(player);
    }

    public static ItemStack getItemBySettings(CratePlayer cratePlayer) {
        if(cratePlayer.isAllowAnimation()) {
            return new ItemBuilder(Material.STAINED_GLASS_PANE, (short)13 , 1).setName("§aAnimation aktiviert").build();
        }
        return new ItemBuilder(Material.STAINED_GLASS_PANE, (short)14 , 1).setName("§cAnimation deaktiviert").build();
    }

}
