package de.schornyy.crate.crateplugin.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CratePlayerManager {

    private HashMap<Player, CratePlayer> storedPlayer;

    public CratePlayerManager() {
        storedPlayer = new HashMap<>();
    }

    public void loadAllPlayer() {
        if(Bukkit.getOnlinePlayers().isEmpty()) {
           return;
        }

        for(Player all : Bukkit.getOnlinePlayers()) {
            if(all == null) break;
            CratePlayer cratePlayer = new CratePlayer(all);
            storedPlayer.put(all, cratePlayer);
        }
    }

    public void saveAllPlayers() {
        if(getStoredPlayer().isEmpty()) return;

        for(CratePlayer cratePlayers : getStoredPlayer().values()) {
            if(cratePlayers == null)break;
            cratePlayers.save();
        }

    }

    public HashMap<Player, CratePlayer> getStoredPlayer() {
        return storedPlayer;
    }
}
