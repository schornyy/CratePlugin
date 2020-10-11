package de.schornyy.crate.crateplugin.listener;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.player.CratePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        CratePlayer cratePlayer = CratePlugin.plugin.getCratePlayerManager().getStoredPlayer().get(player);
        cratePlayer.save();
    }
}
