package de.schornyy.crate.crateplugin.shop;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.crates.Crate;
import de.schornyy.crate.crateplugin.crates.CrateUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Shop {

    private File file;
    private FileConfiguration cfg;

    private String inventoryName;
    private int inventorySlots;
    private Inventory inventory;
    private List<ShopCrate> storedShopCrates;

    public Shop() {
        file = new File(CratePlugin.plugin.getDataFolder() + "/Shop.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        storedShopCrates = new ArrayList<>();
        load();
    }

    public void save() {
        if(storedShopCrates.isEmpty()) return;

        for(ShopCrate shopCrate : getStoredShopCrates()) {
            if(storedShopCrates == null)break;
            getCfg().set("Shop.crates." + shopCrate.getSlot() + ".crate", shopCrate.getCrate().getName());
            getCfg().set("Shop.crates." + shopCrate.getSlot() + ".price", shopCrate.getPrice());
        }


        try {
            getCfg().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void load() {
        if(!getFile().exists()) {

            getCfg().set("Inventory.name", "&eShop");
            getCfg().set("Inventory.slots", 9);

            try {
                getCfg().save(getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        inventoryName = getCfg().getString("Inventory.name").replace("&", "§");
        inventorySlots = getCfg().getInt("Inventory.slots");
        inventory = Bukkit.createInventory(null, inventorySlots, inventoryName);

        if(getCfg().getConfigurationSection("Shop.crates.") != null) {
            for(String section : getCfg().getConfigurationSection("Shop.crates.").getKeys(false)) {
                if(section == null)break;
                int slot = Integer.valueOf(section);
                Crate crate = CratePlugin.plugin.getCrateManager().getStoredCrates().get(getCfg().getString("Shop.crates." + section + ".crate"));
                ShopCrate shopCrate = new ShopCrate(crate, getCfg().getInt("Shop.crates." + section + ".price"), slot);
                storedShopCrates.add(shopCrate);

                inventory.setItem(slot, shopCrate.getShopIcon());
            }
        }


    }

    public List<ShopCrate> getStoredShopCrates() {
        return storedShopCrates;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public int getInventorySlots() {
        return inventorySlots;
    }
}
