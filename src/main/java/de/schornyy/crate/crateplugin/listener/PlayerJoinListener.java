package de.schornyy.crate.crateplugin.listener;

import de.schornyy.crate.crateplugin.player.CratePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        CratePlayer cratePlayer = new CratePlayer(player);

    }
}
