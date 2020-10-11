package de.schornyy.crate.crateplugin.commands;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.player.CratePlayer;
import de.schornyy.crate.crateplugin.player.CratePlayerUtils;
import de.schornyy.crate.crateplugin.utils.Inventorys;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        CratePlayer cratePlayer = CratePlugin.plugin.getCratePlayerManager().getStoredPlayer().get(player);

        player.openInventory(Inventorys.playerSettingsInventory(cratePlayer));

        return false;
    }

 /*
 Player Commands:
    - /settings
    - /openall
  */
}
