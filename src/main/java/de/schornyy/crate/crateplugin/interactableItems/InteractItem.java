package de.schornyy.crate.crateplugin.interactableItems;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InteractItem {

    private String command;
    private ItemStack itemStack;
    private int index;

    public InteractItem(ItemStack itemStack, String command, int index) {
        this.command = command;
        this.itemStack = itemStack;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public ItemStack getShowItem() {
        ItemStack itemStack = getItemStack().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add("§fCommand: §e" + getCommand());
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getCommand() {
        return command;
    }
}
