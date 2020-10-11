package de.schornyy.crate.crateplugin.shop;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.crates.Crate;
import de.schornyy.crate.crateplugin.crates.CrateUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.inventory.ItemStack;

public class ShopUtils{

    public static ShopCrate getShopCrateByCrate(Crate crate) {
        Shop shop = CratePlugin.plugin.getShop();

        if(shop.getStoredShopCrates().isEmpty()) return null;

        for(ShopCrate shopCrate : shop.getStoredShopCrates()) {
            if(shopCrate == null)break;
            if(shopCrate.getCrate().equals(crate)) {
                return shopCrate;
            }
        }

        return null;
    }

    public static ShopCrate getShopCrateByItemStack(ItemStack itemStack) {
        Shop shop = CratePlugin.plugin.getShop();

        if(shop.getStoredShopCrates().isEmpty()) return null;

        for(ShopCrate shopCrate : shop.getStoredShopCrates()) {
            if(shopCrate == null)break;
            if(shopCrate.getShopIcon().equals(itemStack)) return shopCrate;
        }

        return null;
    }

    public static ShopCrate getShopCrateByName(String name) {
        for(ShopCrate shopCrate : CratePlugin.plugin.getShop().getStoredShopCrates()) {
            if(shopCrate == null)break;
            if(shopCrate.getCrate().getName().equalsIgnoreCase(name)) {
                return shopCrate;
            }
         }

        return null;
    }

    public static void addCrateToShop(int slot, Crate crate, int price) {
        Shop shop = CratePlugin.plugin.getShop();
        ShopCrate shopCrate = new ShopCrate(crate, price, slot);
        shop.getStoredShopCrates().add(shopCrate);
        shop.getInventory().setItem(slot, shopCrate.getShopIcon());
    }

    public static void removeCrateFromShop(String crateName) {
        ShopCrate shopCrate = getShopCrateByName(crateName);
        CratePlugin.plugin.getShop().getInventory().setItem(shopCrate.getSlot(), null);
        CratePlugin.plugin.getShop().getStoredShopCrates().remove(shopCrate);
    }




}
