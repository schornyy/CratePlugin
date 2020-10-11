package de.schornyy.crate.crateplugin.configs;

import de.schornyy.crate.crateplugin.CratePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingsConfig {

    private File file;
    private FileConfiguration cfg;

    public List<String> loreOfRollingItems, loreOfCrateShopItems;

    public SettingsConfig() {
        file = new File(CratePlugin.plugin.getDataFolder() + "/Settings.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public void load() {
        if(!file.exists()) {

            loreOfRollingItems = new ArrayList<>();
            loreOfRollingItems.add("&fChance: &c%chance%");
            loreOfRollingItems.add("&fSeltenheit: %rarity%");

            cfg.set("loreOfRollingItems", loreOfRollingItems);

            loreOfCrateShopItems = new ArrayList<>();
            loreOfCrateShopItems.add("&fPreis: &a%price%");

            cfg.set("loreOfCrateShopItems", loreOfCrateShopItems);

            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loreOfRollingItems = new ArrayList<>();
        for(String lori : cfg.getStringList("loreOfRollingItems")) {
            if(lori == null)break;
            loreOfRollingItems.add(lori.replace("&", "§"));
        }

        loreOfCrateShopItems = new ArrayList<>();
        for(String locsi : cfg.getStringList("loreOfCrateShopItems")) {
            if(locsi == null)break;
            loreOfCrateShopItems.add(locsi.replace("&", "§"));
        }
    }


}
