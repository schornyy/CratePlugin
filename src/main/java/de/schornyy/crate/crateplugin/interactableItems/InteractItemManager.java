package de.schornyy.crate.crateplugin.interactableItems;

import de.schornyy.crate.crateplugin.CratePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class InteractItemManager {

    private HashMap<ItemStack, InteractItem> storedInteractItems;
    private File file;
    private FileConfiguration cfg;
    private int globalIndex = 0;

    public InteractItemManager() {
        storedInteractItems = new HashMap<>();
        file = new File(CratePlugin.plugin.getDataFolder() + "/InteractableItems.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public void addInteractItem(ItemStack item, String command) {
        globalIndex++;
        InteractItem interactItem = new InteractItem(item, command, globalIndex);
        getStoredInteractItems().put(item, interactItem);
    }

    public void removeInteractItem(int index) {
        if(getStoredInteractItems().isEmpty()) return;
        for(InteractItem interactItem : getStoredInteractItems().values()) {
            if(interactItem == null)break;
            if(interactItem.getIndex() == index) {
                getStoredInteractItems().remove(interactItem.getItemStack());

                if(getCfg().getConfigurationSection("Items." + index + ".") != null) {
                    getCfg().set("Items." + index + ".", null);

                try {
                    getCfg().save(getFile());
                } catch (IOException e) {
                    e.printStackTrace();
                } }
            }
        }
    }

    public void load() {
        if(getFile().exists()) {
            if(getCfg().getConfigurationSection("Items.") != null) {
                for(String section : getCfg().getConfigurationSection("Items.").getKeys(false)) {
                    if(section == null)break;
                    InteractItem interactItem = new InteractItem(getCfg().getItemStack("Items." + section + ".item")
                            , getCfg().getString("Items." + section + ".command")
                            , Integer.valueOf(section));
                    storedInteractItems.put(interactItem.getItemStack(), interactItem);
                    globalIndex++;
                }
            }
        }
    }

    public void save() {
        if(getStoredInteractItems().isEmpty()) return;
        int i = 0;
        for(InteractItem interactItem : getStoredInteractItems().values()) {
            if(interactItem == null) break;

            getCfg().set("Items." + i + ".item", interactItem.getItemStack());
            getCfg().set("Items." + i + ".command", interactItem.getCommand());

            i++;
        }

        try {
            getCfg().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getGlobalIndex() {
        return globalIndex;
    }

    public HashMap<ItemStack, InteractItem> getStoredInteractItems() {
        return storedInteractItems;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }
}
