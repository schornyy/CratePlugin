package de.schornyy.crate.crateplugin.crates;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.crates.reward.RewardItem;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Random;

public class CrateUtils {

    public static Crate getCrateByInventoryName(String invName) {
        for(Crate crate : CratePlugin.plugin.getCrateManager().getStoredCrates().values()) {
            if(crate == null) break;
            if(crate.getInvName().equals(invName)) {
                return crate;
            }
        }
        return null;
    }

    public static Crate getCrateByCrateName(String name) {
        for(Crate crate : CratePlugin.plugin.getCrateManager().getStoredCrates().values()) {
            if(crate == null)break;
            if(crate.getName().equalsIgnoreCase(name)) {
                return crate;
            }
        }
        return null;
    }

    public static Crate getCrateByItemStack(ItemStack item) {
        for(Crate crate : CratePlugin.plugin.getCrateManager().getStoredCrates().values()) {
            if (crate == null)break;
            ItemStack itemStack = item.clone();
            itemStack.setAmount(1);
            if(crate.getIcon().equals(itemStack)) return crate;
        }
        return null;
    }

    public static ItemStack getRandomItemFromCrate(Crate crate) {
        RewardItem rewardItem1 = crate.getRewardItems().get(new Random().nextInt(crate.getRewardItems().size()));
        RewardItem rewardItem2 = crate.getRewardItems().get(new Random().nextInt(crate.getRewardItems().size()));
        ItemStack item = null;

        if(rewardItem1.getChance() < rewardItem2.getChance()) {
            item = rewardItem1.getRollingItem();
        } else if(rewardItem2.getChance() < rewardItem1.getChance()) {
            item = rewardItem2.getRollingItem();
        } else {
            int rnd = new Random().nextInt(2);
            if(rnd == 1) {
                item = rewardItem1.getRollingItem();
            } else {
                item = rewardItem2.getRollingItem();
            }
        }

        return item;
    }

    public static ItemStack getRandomItemFromCrate_NormalItem(Crate crate) {
        RewardItem rewardItem1 = crate.getRewardItems().get(new Random().nextInt(crate.getRewardItems().size()));
        RewardItem rewardItem2 = crate.getRewardItems().get(new Random().nextInt(crate.getRewardItems().size()));
        ItemStack item = null;

        if(rewardItem1.getChance() > rewardItem2.getChance()) {
            ItemStack is = new ItemStack(rewardItem1.getItem().getType(), rewardItem1.getItem().getAmount());
            item = is;
        } else if(rewardItem2.getChance() > rewardItem1.getChance()) {
            ItemStack is = new ItemStack(rewardItem2.getItem().getType()
                    , rewardItem2.getItem().getAmount());
            item = is;
        } else {
            int rnd = new Random().nextInt(2);
            if(rnd == 1) {
                ItemStack is = new ItemStack(rewardItem1.getItem().getType(), rewardItem1.getItem().getAmount());
                item = is;
            } else {
                ItemStack is = new ItemStack(rewardItem2.getItem().getType(), rewardItem2.getItem().getAmount());
                item = is;
            }
        }

        return item;
    }
}
