package de.schornyy.crate.crateplugin.commands;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.configs.MessagesConfig;
import de.schornyy.crate.crateplugin.crates.Crate;
import de.schornyy.crate.crateplugin.crates.CrateRoll;
import de.schornyy.crate.crateplugin.crates.CrateUtils;
import de.schornyy.crate.crateplugin.crates.rarity.Rarity;
import de.schornyy.crate.crateplugin.crates.rarity.RarityUtils;
import de.schornyy.crate.crateplugin.crates.reward.RewardItem;
import de.schornyy.crate.crateplugin.shop.ShopCrate;
import de.schornyy.crate.crateplugin.shop.ShopUtils;
import de.schornyy.crate.crateplugin.utils.Inventorys;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CrateCommand implements CommandExecutor {

    private MessagesConfig messagesConfig = CratePlugin.plugin.getConfigManager().getMessagesConfig();

    /*
    - Crate <Crate> open                        === Erledigt ===
    - Crate <Name> open <Player>                === Erledigt ===
    - Crate create <name>                       === Erledigt ===
    - Crate <Name> delete                       === Erledigt ===
    - Crate <Name> addItem <Chance> <Rarity>    === Erledigt ===
    - Crate <Name> removeItem <Index>           === Erledigt ===
    - Crate <Name> setIcon                      === Erledigt ===
    - Crate <Name> edit                         === Erledigt ===
    - Crate <Name> setWinningSlot               === Erledigt ===
    - Crate <Name> itemIndex                    === Erledigt ===
    - Crate give <Player> <Crate>               === Erledigt ===
     */


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        switch (args.length) {
            case 2:
                if(args[0].equalsIgnoreCase("create"))  {
                    if(!player.hasPermission("crate.create")) {
                        player.sendMessage(messagesConfig.no_Permissions);
                        return true;
                    }

                    String crateName = args[1];

                    if(CrateUtils.getCrateByCrateName(crateName) != null) {
                        player.sendMessage(messagesConfig.crate_Already_exists.replace("%crate%", crateName));
                        return true;
                    }

                    if(player.getItemInHand() == null) {
                        player.sendMessage(messagesConfig.neeed_Item_inHand);
                        return true;
                    }

                    Crate crate = new Crate(crateName);
                    crate.create(player.getItemInHand());
                    player.sendMessage(messagesConfig.created_Crate.replace("%crate%", crateName));

                } else if(args[1].equalsIgnoreCase("open")) {
                    String crateName = args[0];
                    if(!player.hasPermission("crate.open")) {
                        player.sendMessage(messagesConfig.no_Permissions);
                        return true;
                    }

                    if(CrateUtils.getCrateByCrateName(crateName) == null) {
                        player.sendMessage(messagesConfig.crate_dosnt_exists);
                        return true;
                    }

                    CrateRoll crateRoll = new CrateRoll(player, CrateUtils.getCrateByCrateName(crateName));
                    crateRoll.roll();

                } else if(args[1].equalsIgnoreCase("delete")) {
                    String crateName = args[0];

                    if(CrateUtils.getCrateByCrateName(crateName) == null) {
                        player.sendMessage(messagesConfig.crate_dosnt_exists);
                        return true;
                    }

                    Crate crate = CrateUtils.getCrateByCrateName(crateName);
                    crate.delete();
                    player.sendMessage(messagesConfig.crate_Deleted);

                } else if(args[1].equalsIgnoreCase("setIcon")) {
                    String crateNmae = args[0];

                    if(!player.hasPermission("crate.setIcon")) {
                        player.sendMessage(messagesConfig.no_Permissions);
                        return true;
                    }

                    if(CrateUtils.getCrateByCrateName(crateNmae) == null) {
                        player.sendMessage(messagesConfig.crate_dosnt_exists);
                        return true;
                    }

                    if(player.getItemInHand() == null) {
                        player.sendMessage(messagesConfig.neeed_Item_inHand);
                        return true;
                    }
                    Crate crate = CrateUtils.getCrateByCrateName(crateNmae);
                    crate.setIcon(player.getItemInHand());
                    player.sendMessage(messagesConfig.crate_setIcon.replace("%crate%", crateNmae));


                } else if(args[1].equalsIgnoreCase("edit")) {
                    String crateNmae = args[0];
                    if(!player.hasPermission("crate.edit")) {
                        player.sendMessage(messagesConfig.no_Permissions);
                        return true;
                    }

                    if(CrateUtils.getCrateByCrateName(crateNmae) == null) {
                        player.sendMessage(messagesConfig.crate_dosnt_exists);
                        return true;
                    }
                    Crate crate = CrateUtils.getCrateByCrateName(crateNmae);

                    player.openInventory(Inventorys.editCrate(crate));

                } else if(args[1].equalsIgnoreCase("setWinningSlot")) {
                    String crateNmae = args[0];

                    if(!player.hasPermission("crate.setWinningSlot")) {
                        player.sendMessage(messagesConfig.no_Permissions);
                        return true;
                    }

                    if(CrateUtils.getCrateByCrateName(crateNmae) == null) {
                        player.sendMessage(messagesConfig.crate_dosnt_exists);
                        return true;
                    }

                    int winningSlot = 0;

                    try {
                        winningSlot = Integer.valueOf(2);
                    }catch (NumberFormatException e) {
                        player.sendMessage(messagesConfig.need_a_Number);
                        return true;
                    }

                    Crate crate = CrateUtils.getCrateByCrateName(crateNmae);
                    crate.setWinningSlot(winningSlot);
                    player.sendMessage(messagesConfig.crate_setWinningSlot
                            .replace("%crate%", crateNmae)
                            .replace("%winningSlor%", winningSlot + ""));


                } else if(args[1].equalsIgnoreCase("itemIndex")) {
                    String crateName = args[0];
                    if(!player.hasPermission("crate.itemIndex")) {
                        player.sendMessage(messagesConfig.no_Permissions);
                        return true;
                    }

                    if(CrateUtils.getCrateByCrateName(crateName) == null) {
                        player.sendMessage(messagesConfig.crate_dosnt_exists);
                        return true;
                    }
                    Crate crate = CrateUtils.getCrateByCrateName(crateName);
                    player.openInventory(Inventorys.ItemIndexOfCrate(crate));

                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("give")) {
                    String crateName = args[2];
                    if(!player.hasPermission("crate.give.player")) {
                        player.sendMessage(messagesConfig.no_Permissions);
                        return true;
                    }

                    if(CrateUtils.getCrateByCrateName(crateName) == null) {
                        player.sendMessage(messagesConfig.crate_dosnt_exists);
                        return true;
                    }

                    if(!Bukkit.getPlayer(args[1]).isOnline()) {
                        player.sendMessage(messagesConfig.player_isNot_Online);
                        return true;
                    }

                    Crate crate = CrateUtils.getCrateByCrateName(crateName);
                    Player target = Bukkit.getPlayer(args[1]);
                    target.getInventory().addItem(crate.getIcon());
                    player.sendMessage(messagesConfig.gived_Player_Crate
                            .replace("%player%", target.getName())
                            .replace("%crate%", crate.getName()));

                } else if(args[1].equalsIgnoreCase("open")) {
                    String crateName = args[0];
                    if(!player.hasPermission("crate.open.player")) {
                        player.sendMessage(messagesConfig.no_Permissions);
                        return true;
                    }

                    if(CrateUtils.getCrateByCrateName(crateName) == null) {
                        player.sendMessage(messagesConfig.crate_dosnt_exists);
                        return true;
                    }

                    if(!Bukkit.getPlayer(args[2]).isOnline()) {
                        player.sendMessage(messagesConfig.player_isNot_Online);
                        return true;
                    }

                    Player target = Bukkit.getPlayer(args[2]);

                    CrateRoll crateRoll = new CrateRoll(target, CrateUtils.getCrateByCrateName(crateName));
                    crateRoll.roll();
                    player.sendMessage(messagesConfig.opened_Crate_for_Player
                            .replace("%player%", target.getName())
                            .replace("%crate%", crateName));
                } else if (args[1].equalsIgnoreCase("removeItem")) {
                    String crateName = args[0];
                    if(!player.hasPermission("crate.removeItem")) {
                        player.sendMessage(messagesConfig.no_Permissions);
                        return true;
                    }

                    if(CrateUtils.getCrateByCrateName(crateName) == null) {
                        player.sendMessage(messagesConfig.crate_dosnt_exists);
                        return true;
                    }

                    int index = 0;

                    try {
                        index = Integer.valueOf(args[2]);
                    }catch (NumberFormatException e) {
                        player.sendMessage(messagesConfig.need_a_Number);
                        return true;
                    }

                    Crate crate = CrateUtils.getCrateByCrateName(crateName);

                    if(crate.getRewardItems().get(index) == null) {
                        player.sendMessage(messagesConfig.crateItem_dosnt_Exists
                                .replace("%crate%", crateName)
                                .replace("%index%", "" + index));
                        return true;
                    }

                    crate.removeItemByIndex(index);
                    player.sendMessage(messagesConfig.deleted_item_from_Crate
                            .replace("%crate%", crateName)
                            .replace("%index%", "" + index));

                }
                break;
            case 4:
                if(args[1].equalsIgnoreCase("addItem")) {
                    String crateName = args[0];

                    if(!player.hasPermission("crate.additem")) {
                        player.sendMessage(messagesConfig.no_Permissions);
                        return true;
                    }

                    if (player.getItemInHand() == null) {
                        player.sendMessage(messagesConfig.neeed_Item_inHand);
                        return true;
                    }

                    double chance = 0.00;

                    try {
                        chance = Double.valueOf(args[2]);
                    }catch (NumberFormatException e) {
                        player.sendMessage(messagesConfig.need_a_Number);
                        return true;
                    }

                    if(RarityUtils.getRarityByName(args[3]) == null) {
                        player.sendMessage(messagesConfig.rarity_Dosnt_Exists);
                        return true;
                    }

                    Rarity rarity = RarityUtils.getRarityByName(args[3]);
                    Crate crate = CratePlugin.plugin.getCrateManager().getStoredCrates().get(args[0]);
                    crate.getRewardItems().add(new RewardItem(player.getItemInHand(), chance, rarity));
                    player.sendMessage(messagesConfig.add_Item_to_Crate
                            .replace("%chance%", "" + chance)
                            .replace("%crate%", crateName)
                            .replace("%rarity%", rarity.getRarity()));
                }
                break;
        }

        return false;
    }
}
