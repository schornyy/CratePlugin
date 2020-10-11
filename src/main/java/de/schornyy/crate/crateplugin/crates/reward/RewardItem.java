package de.schornyy.crate.crateplugin.crates.reward;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.crates.rarity.Rarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RewardItem {

    private ItemStack item;
    private double chance;
    private Rarity rarity;

    public RewardItem(ItemStack item, double chance, Rarity rarity) {
        this.item = item;
        this.chance = chance;
        this.rarity = rarity;
    }

    public double getChance() {
        return chance;
    }

    public ItemStack getRollingItem() {
        ItemStack item = this.item;
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        for(String s : CratePlugin.plugin.getConfigManager().getSettingsConfig().loreOfRollingItems) {
            if(s == null)break;
            if(s.contains("%chance%")) {
                lore.add(s.replace("%chance%", "" + chance));
            } else if(s.contains("%rarity%")) {
                lore.add(s.replace("%rarity%", getRarity().getRarity()));
            } else {
                lore.add(s);
            }
        }
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemStack getItem() {
        return item;
    }

    public Rarity getRarity() {
        return rarity;
    }
}
