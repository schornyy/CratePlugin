package de.schornyy.crate.crateplugin.configs;

import de.schornyy.crate.crateplugin.CratePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessagesConfig {

    private File file;
    private FileConfiguration cfg;

    public String preifx, no_Permissions, need_more_crates_for_openall, need_a_Number
            , interactItem_with_index_dosnt_exists, interactItem_deleted_byIndex
            , need_Item_inHand_to_add_Interactitem, added_interactitem, player_isNot_Online
            , give_player_InteractItem, crate_dosnt_exists, add_Crate_to_Shop, remove_Crate_from_Shop
            , not_Enough_Money_to_Buy_Crate, buy_Crate, crate_Already_exists, neeed_Item_inHand, created_Crate
            , crate_Deleted, crate_setIcon, crate_setWinningSlot, crate_Edited, opened_Crate_for_Player
            , deleted_item_from_Crate, crateItem_dosnt_Exists, gived_Player_Crate, rarity_Dosnt_Exists, add_Item_to_Crate
            , shopCrate_setPrice;

    public MessagesConfig() {
        file = new File(CratePlugin.plugin.getDataFolder() + "/Messages.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        load();
    }

    public void load() {
        if(!getFile().exists()) {

            getCfg().set("Prefix", "&5Crates&f: ");
            getCfg().set("no_Permissions", "&cDu hast keine Rechte dazu!");
            getCfg().set("need_more_crates_for_openall", "&cDu musst mehr als 1 Crate haben um /openall benutzen zu können!");
            getCfg().set("need_a_Number", "&cDu musst eine Zahl angeben!");
            getCfg().set("interactItem_with_index_dosnt_exists", "&cDas InteractItem mit dem Index &f[&5Index&f:%index%&f] &cexistiert nicht!");
            getCfg().set("interactItem_deleted_byIndex", "&cDu hast das InteractItem mit dem Index &f[&5Index&f:%index%&f] &cgelöscht");
            getCfg().set("need_Item_inHand_to_add_Interactitem", "&cDu musst ein item in der Hand halten für denn Command &e/interactitem add <Command>");
            getCfg().set("added_interactitem", "&aDu hast erfolgreich ein InteractItem hinzugefügt mit der Index ID (&e%index%) &aund dem Command &e%command%");
            getCfg().set("player_isNot_Online", "&cDer Spieler ist nicht online!");
            getCfg().set("give_player_InteractItem", "&aDu hast dem Spieler &f(&e%player%&e) &aein InteractItem mit dem Index &f[&e%index%&f] &agegeben!");
            getCfg().set("crate_dosnt_exists", "&cDie angegebene Crate existiert nicht!");
            getCfg().set("add_Crate_to_Shop", "&aDu hast die Crate &f(&e%crate%&f) &azum Shop hinzu gefügt mit dem Preis&f: &e%price% &aauf Slot&f: &e%slot%");
            getCfg().set("remove_Crate_from_Shop", "&aDu hast die Crate &f(&e%crate%&f) &aaus dem Shop entfernt!");
            getCfg().set("not_Enough_Money_to_Buy_Crate", "&cDu hast nicht genug Geld um dir die Crate&f: &e%crate% &czu kaufen");
            getCfg().set("buy_Crate", "&aDu hast dir die Crate &f(&e%crate%&f) &agekauft");
            getCfg().set("crate_Already_exists", "&cDie Crate &f(&e%crate%&f) &cexistiert bereits!");
            getCfg().set("neeed_Item_inHand", "&cDu musst ein Item in dr Hand halten dafür!");
            getCfg().set("created_Crate", "&aDu hast die Crate&f(&e%crate%&f) &aerfolgreich erstelte!");
            getCfg().set("crate_Deleted", "&aDu hast die Crate &f(&e%crate%&f) &aerfolgreich gelöscht!");
            getCfg().set("crate_setIcon", "&aDu hast das Icon für die Crate &f(&e%crate%&f) &agesetzt");
            getCfg().set("crate_setWinningSlot", "&aDu hast denn WIningSlor für die Crate &f(&e%crate%&f) &aauf Slot&f: &5%winningSlot%");
            getCfg().set("crate_Edited", "&aDu hast die Crate &e%crate% &aerfolgreich editiert!");
            getCfg().set("opened_Crate_for_Player", "&aDu hast dem Spieler &f(&e%player%&f) &Adie Crate &f(&e%crate%&f) &ageöffnet!");
            getCfg().set("deleted_item_from_Crate", "&aDu hast das Item mit dem Index&f:&5 %index% &avon der Crate&f: &e%crate% &agelöscht!");
            getCfg().set("crateItem_dosnt_Exists", "&cDas Item mit dem Index&f: &5%index% &cin der Crate &e%crate% &cexistiert nicht!");
            getCfg().set("gived_Player_Crate", "&aDu hast dem Spieler &e%player% &adie Crate &e%crate% &agegeben!");
            getCfg().set("rarity_Dosnt_Exists", "&cDie Rarity mit dem Namen &e%rarity% &cexistiert nicht!");
            getCfg().set("add_Item_to_Crate", "&aDu hast ein Item zu der Crate&f:&e%crate% hinzu gefügt mit der Chance &e%chance% &aund der Seltenheit &e%rarity%");
            getCfg().set("shopCrate_setPrice", "&a Du hast den Preis von &&%crate% &aauf &5%price% &agesetzt");

            try {
                getCfg().save(getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        preifx = getCfg().getString("Prefix").replace("&", "§");
        no_Permissions = preifx + getCfg().getString("no_Permissions").replace("&", "§");
        need_more_crates_for_openall = preifx + getCfg().getString("need_more_crates_for_openall").replace("&", "§");
        need_a_Number = preifx + getStringByConfig("need_a_Number");
        interactItem_with_index_dosnt_exists = preifx + getStringByConfig("interactItem_with_index_dosnt_exists");
        interactItem_deleted_byIndex = preifx + getStringByConfig("interactItem_deleted_byIndex");
        need_Item_inHand_to_add_Interactitem = preifx + getStringByConfig("need_Item_inHand_to_add_Interactitem");
        added_interactitem = preifx + getStringByConfig("added_interactitem");
        player_isNot_Online = preifx + getStringByConfig("player_isNot_Online");
        give_player_InteractItem = preifx + getStringByConfig("give_player_InteractItem");
        crate_dosnt_exists = preifx + getStringByConfig("crate_dosnt_exists");
        add_Crate_to_Shop = preifx + getStringByConfig("add_Crate_to_Shop");
        remove_Crate_from_Shop = preifx + getStringByConfig("remove_Crate_from_Shop");
        not_Enough_Money_to_Buy_Crate = preifx + getStringByConfig("not_Enough_Money_to_Buy_Crate");
        buy_Crate = preifx + getStringByConfig("buy_Crate");
        crate_Already_exists = preifx + getStringByConfig("crate_Already_exists");
        neeed_Item_inHand = preifx + getStringByConfig("neeed_Item_inHand");
        created_Crate = preifx + getStringByConfig("created_Crate");
        crate_Deleted = preifx + getStringByConfig("crate_Deleted");
        crate_setIcon = preifx + getStringByConfig("crate_setIcon");
        crate_setWinningSlot = preifx + getStringByConfig("crate_setWinningSlot");
        crate_Edited = preifx + getStringByConfig("crate_Edited");
        opened_Crate_for_Player = preifx + getStringByConfig("opened_Crate_for_Player");
        deleted_item_from_Crate = preifx + getStringByConfig("deleted_item_from_Crate");
        crateItem_dosnt_Exists = preifx + getStringByConfig("crateItem_dosnt_Exists");
        gived_Player_Crate = preifx + getStringByConfig("gived_Player_Crate");
        rarity_Dosnt_Exists = preifx + getStringByConfig("rarity_Dosnt_Exists");
        add_Item_to_Crate = preifx + getStringByConfig("add_Item_to_Crate");
        shopCrate_setPrice = preifx + getStringByConfig("shopCrate_setPrice");
    }

    private String getStringByConfig(String string) {
        return getCfg().getString(string).replace("&", "§");
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }
}
