package de.schornyy.crate.crateplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBuilder {

    private Inventory inventory;

    public InventoryBuilder(int slots) {
        inventory = Bukkit.createInventory(null, slots);
    }

    public InventoryBuilder(int slots, String name) {
        inventory = Bukkit.createInventory(null, slots, name);
    }

    public InventoryBuilder addItem(ItemStack itemStack) {
        inventory.addItem(itemStack);
        return this;
    }

    public InventoryBuilder addItemsFromTo(int from, int to, ItemStack filler) {
        for(int i = from;i < to;i++) {
            inventory.setItem(i, filler);
        }
        return this;
    }

    public  InventoryBuilder setItem(ItemStack item, int slot) {
        inventory.setItem(slot, item);
        return this;
    }

    public Inventory build() {
        return inventory;
    }
}
