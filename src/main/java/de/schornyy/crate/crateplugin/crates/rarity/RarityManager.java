package de.schornyy.crate.crateplugin.crates.rarity;

import de.schornyy.crate.crateplugin.CratePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class RarityManager {

    private File file;
    private FileConfiguration cfg;
    private HashMap<String, Rarity> storedRaritys;

    public RarityManager() {
        file = new File(CratePlugin.plugin.getDataFolder() + "/Raritys.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        storedRaritys = new HashMap<>();
    }

    public void load() {
        if(!getFile().exists()) {

            getCfg().set("Raritys.default", "&fDefault");

            try {
                getCfg().save(getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(getCfg().getConfigurationSection("Raritys.") != null) {
            for (String rarity : getCfg().getConfigurationSection("Raritys.").getKeys(false)) {
                if(rarity == null) break;
                Rarity rarity1 = new Rarity(getCfg().getString("Raritys." + rarity).replace("&", "§"), rarity);
                storedRaritys.put(rarity, rarity1);
            }
        }
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }

    public HashMap<String, Rarity> getStoredRaritys() {
        return storedRaritys;
    }
}
