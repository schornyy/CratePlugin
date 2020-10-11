package de.schornyy.crate.crateplugin.shop;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.crates.Crate;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopCrate {

    private int price, slot;
    private Crate crate;

    public ShopCrate(Crate crate, int price, int slot) {
        this.crate = crate;
        this.price = price;
        this.slot = slot;
    }

    public ItemStack getShopIcon() {
        ItemStack shopIcon = getCrate().getIcon();
        ItemMeta itemMeta = shopIcon.getItemMeta();

        List<String> lore = new ArrayList<>();

        for(String l : CratePlugin.plugin.getConfigManager().getSettingsConfig().loreOfCrateShopItems) {
            if(l == null)break;
            if(l.contains("%price%")) {
                lore.add(l.replace("%price%", "" + getPrice()));
            } else {
                lore.add(l);
            }
        }
        itemMeta.setLore(lore);
        shopIcon.setItemMeta(itemMeta);

        return shopIcon;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSlot() {
        return slot;
    }

    public Crate getCrate() {
        return crate;
    }

    public int getPrice() {
        return price;
    }
}
