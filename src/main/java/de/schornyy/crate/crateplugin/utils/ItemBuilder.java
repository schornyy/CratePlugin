package de.schornyy.crate.crateplugin.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, short subID, int amount) {
        itemStack = new ItemStack(material, amount, (short) subID);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setName(String name) {
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        List<String> l = new ArrayList<>();
        for(String s : lore) {
            l.add(s);
        }
        itemMeta.setLore(l);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment,int lvl) {
        itemStack.addEnchantment(enchantment, lvl);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int lvl) {
        itemStack.addUnsafeEnchantment(enchantment, lvl);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

}
