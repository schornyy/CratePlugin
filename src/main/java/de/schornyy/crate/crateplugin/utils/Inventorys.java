package de.schornyy.crate.crateplugin.utils;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.crates.Crate;
import de.schornyy.crate.crateplugin.crates.CrateUtils;
import de.schornyy.crate.crateplugin.crates.reward.RewardItem;
import de.schornyy.crate.crateplugin.interactableItems.InteractItem;
import de.schornyy.crate.crateplugin.interactableItems.InteractItemManager;
import de.schornyy.crate.crateplugin.player.CratePlayer;
import de.schornyy.crate.crateplugin.player.CratePlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Inventorys {

    public static Inventory ItemIndexOfCrate(Crate crate) {
        Inventory inventory = Bukkit.createInventory(null, getChangeableSlots(crate.getRewardItems().size()), crate.getInvName() + "-§5Index");

        for(int i = 0; i < crate.getRewardItems().size();i++) {
            ItemStack item = crate.getRewardItems().get(i).getRollingItem().clone();
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§5Index§f: §e" + i);
            item.setItemMeta(itemMeta);
            inventory.setItem(i, item);
        }


        return inventory;
    }

    public static Inventory editCrate(Crate crate) {
        Inventory inventory = Bukkit.createInventory(null, crate.getInvSlots(), crate.getInvName() + "-§eEdit");
        inventory.setContents(crate.getInventory().getContents());

        for(int i : crate.getRollingSlots()) {
            inventory.setItem(i, new ItemBuilder(Material.BARRIER).setName("§aRolling Slots").build());
        }

        return inventory;
    }

    public static Inventory getInteractItemsShow() {
        int input = CratePlugin.plugin.getInteractItemManager().getGlobalIndex();
        int slots = getChangeableSlots(input);
        Inventory inventory = Bukkit.createInventory(null, slots, "§5Interactitems Show");
        int i = 0;
        if(!CratePlugin.plugin.getInteractItemManager().getStoredInteractItems().isEmpty()) {
            for(InteractItem interactItem : CratePlugin.plugin.getInteractItemManager().getStoredInteractItems().values()) {
                if(interactItem == null)break;
                inventory.setItem(i, interactItem.getShowItem());
                i++;
            }
        }


        return  inventory;
    }

    public static Inventory getItemIndexOfInteractItems() {
        int slots = 9;
        InteractItemManager interactItemManager = CratePlugin.plugin.getInteractItemManager();
        int items = CratePlugin.plugin.getInteractItemManager().getGlobalIndex();
        if(items > 9) {
            slots = 18;
        } else if(items > 18) {
            slots = 27;
        }else if(items > 27) {
            slots = 36;
        }else if(items > 36) {
            slots = 45;
        }else if(items > 45) {
            slots = 54;
        }
        Inventory inventory = Bukkit.createInventory(null, slots, "§5Index of InteractableItems");

        int i = 0;
        if(!interactItemManager.getStoredInteractItems().isEmpty()) {
            for(InteractItem interactItem : interactItemManager.getStoredInteractItems().values()) {
                if(interactItem == null)break;
                ItemStack item = interactItem.getItemStack().clone();
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName("§5Index§f: §e" + interactItem.getIndex());
                item.setItemMeta(itemMeta);

                inventory.setItem(i, item);

                i++;
            }
        }


        return inventory;
    }

    public static Inventory getItemFromCrateWithoutAnimation(Player player, Crate crate) {
        Inventory inventory = Bukkit.createInventory(null, 9, crate.getInvName());

        for(int i = 0;i < inventory.getSize();i++) {
            inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (short)15, 1).build());
        }

        inventory.setItem(4, CrateUtils.getRandomItemFromCrate_NormalItem(crate));

        return inventory;
    }

    public static Inventory playerSettingsInventory(CratePlayer cratePlayer) {
        Inventory inv = new InventoryBuilder(9, "§eSettings")
                .addItemsFromTo(0,9,new ItemBuilder(Material.STAINED_GLASS_PANE, (short)15, 1).build())
                .setItem(CratePlayerUtils.getItemBySettings(cratePlayer), 4)
                .build();
        return inv;
    }

    public static Inventory openAllCrates(Player player, Crate crate) {
        int slots = 9;
        if(player.getItemInHand().getAmount() > 9) {
            slots = 18;
        } else if(player.getItemInHand().getAmount() > 18) {
            slots = 27;
        } else if(player.getItemInHand().getAmount() > 27) {
            slots = 36;
        }  else if(player.getItemInHand().getAmount() > 36) {
            slots = 45;
        } else if(player.getItemInHand().getAmount() > 45) {
            slots = 54;
        }

        Inventory inventory = Bukkit.createInventory(null, slots, crate.getInvName() + " §eopend all");

        int items = player.getItemInHand().getAmount();

        for(int i = 0; i < items;i++) {
            inventory.setItem(i, CrateUtils.getRandomItemFromCrate_NormalItem(crate));
        }

        return inventory;
    }

    private static int getChangeableSlots(int amountOfInput) {
        int slots = 9;
        if(amountOfInput > 9) {
            slots = 18;
        } else if(amountOfInput > 9) {
            slots = 18;
        }  else if(amountOfInput > 18) {
            slots = 27;
        } else if(amountOfInput > 27) {
            slots = 36;
        } else if(amountOfInput > 36) {
            slots = 45;
        } else if(amountOfInput > 45) {
            slots = 54;
        }
        return slots;
    }
}
