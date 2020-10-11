package de.schornyy.crate.crateplugin.crates;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.crates.rarity.Rarity;
import de.schornyy.crate.crateplugin.crates.rarity.RarityUtils;
import de.schornyy.crate.crateplugin.crates.reward.RewardItem;
import de.schornyy.crate.crateplugin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Crate {

    private String name, invName;
    private int invSlots, winningSlot;
    private ItemStack icon;
    private List<RewardItem> rewardItems;
    private List<Integer> rollingSlots;
    private Inventory inventory;

    private File file;
    private FileConfiguration cfg;

    public Crate(String name) {
        this.name = name;
        file = new File(CratePlugin.plugin.getDataFolder() + "/Crates/" + name + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        rewardItems = new ArrayList<>();
        rollingSlots = new ArrayList<>();
    }

    public void removeItemByIndex(int index) {
        getRewardItems().remove(index);
        if(getCfg().getConfigurationSection("RewardItem." + index + ".") != null) {

            getCfg().set("RewardItem." + index + ".", null);

            try {
                getCfg().save(getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete() {
        getFile().delete();
        CratePlugin.plugin.getCrateManager().getStoredCrates().remove(getName());
    }


    public void create(ItemStack icon) {
        this.icon = icon;
        this.winningSlot = 13;
        this.invName = "§5Crate§f: §6" + name;
        getCfg().set("Inventory.name", invName.replace("§", "&"));
        this.invSlots = 27;
        getCfg().set("Inventory.slots", invSlots);
        this.inventory = Bukkit.createInventory(null, invSlots, invName);

        int[] invItems = {0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26};
        for(int slot : invItems) {
            this.inventory.setItem(slot, new ItemStack(Material.STAINED_GLASS_PANE));
        }
        this.inventory.setItem(4, new ItemBuilder(Material.TORCH).setName("§aWinning Item").build());

        rollingSlots.add(10);
        rollingSlots.add(11);
        rollingSlots.add(12);
        rollingSlots.add(13);
        rollingSlots.add(14);
        rollingSlots.add(15);
        rollingSlots.add(16);
        for(int slot : rollingSlots) {
            inventory.setItem(slot, new ItemStack(Material.BARRIER));
        }

        try {
            getCfg().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        CratePlugin.plugin.getCrateManager().getStoredCrates().put(name, this);
    }

    public void load() {
        this.icon = getCfg().getItemStack("Icon");
        this.winningSlot = getCfg().getInt("WinningSlot");
        this.invName = getCfg().getString("Inventory.name").replace("&", "§");
        this.invSlots = getCfg().getInt("Inventory.slots");
        this.inventory = Bukkit.createInventory(null, invSlots, invName);

        //loadInventory Items
        if(getCfg().getConfigurationSection("Inventory.items.") != null) {
            for(String section : getCfg().getConfigurationSection("Inventory.items.").getKeys(false)) {
                if(section == null)break;
                int slot = Integer.valueOf(section);
                inventory.setItem(slot, getCfg().getItemStack("Inventory.items." + section));
            }
        }

        //load rewardItems
        if(getCfg().getConfigurationSection("RewardItem.") != null) {
            for(String section : getCfg().getConfigurationSection("RewardItem.").getKeys(false)) {
                if(section == null)break;
                ItemStack item = getCfg().getItemStack("RewardItem." + section + ".item");
                double chance = getCfg().getDouble("RewardItem." + section + ".chance");
                Rarity rarity = RarityUtils.getRarityByName(getCfg().getString("RewardItem." + section + ".rarity"));
                RewardItem rewardItem = new RewardItem(item, chance, rarity);
                rewardItems.add(rewardItem);
            }
        }

        //load RotatingSlots
        if(getCfg().getIntegerList("RotatingSlots") != null) {
            rollingSlots = getCfg().getIntegerList("RotatingSlots");
            for(int slot : rollingSlots) {
                inventory.setItem(slot, new ItemStack(Material.BARRIER));
            }
        }

    }

    public void save() {
        getCfg().set("Icon", getIcon());
        getCfg().set("WinningSlot", getWinningSlot());

        if(!getRollingSlots().isEmpty()) {
            for(int slot : getRollingSlots()) {
                this.inventory.setItem(slot, new ItemStack(Material.BARRIER));
            }
        }

        //Save Rotating Slots && Inventory Items
        if(getInventory().getContents() != null) {
            int i = 0;
            rollingSlots = new ArrayList<>();
            for(ItemStack slots : getInventory().getContents()) {
                i++;
                if(slots == null) continue;
                if(slots.getType() == Material.BARRIER) {
                    rollingSlots.add(i-1);
                } else {
                    getCfg().set("Inventory.items." + (i-1), slots);
                }
            }
        }

        getCfg().set("RotatingSlots", getRollingSlots());

        //Save rewardItems
        if(!getRewardItems().isEmpty()) {
            int i = 0;
            for(RewardItem rewardItem : getRewardItems()){
                if(rewardItem == null)break;
                getCfg().set("RewardItem." + i + ".item", rewardItem.getItem());
                getCfg().set("RewardItem." + i + ".chance", rewardItem.getChance());
                getCfg().set("RewardItem." + i + ".rarity", rewardItem.getRarity().getRawRarity());
                i++;
            }
        }

        try {
            getCfg().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public void setWinningSlot(int winningSlot) {
        this.winningSlot = winningSlot;
    }

    public void setRollingSlots(List<Integer> rollingSlots) {
        this.rollingSlots = rollingSlots;
    }

    public int getWinningSlot() {
        return winningSlot;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getInvSlots() {
        return invSlots;
    }

    public String getInvName() {
        return invName;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getRollingSlots() {
        return rollingSlots;
    }

    public List<RewardItem> getRewardItems() {
        return rewardItems;
    }
}
