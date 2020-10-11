package de.schornyy.crate.crateplugin.crates;

import de.schornyy.crate.crateplugin.CratePlugin;

import java.io.File;
import java.util.HashMap;

public class CrateManager {

    private HashMap<String, Crate> storedCrates;

    public CrateManager() {
        storedCrates = new HashMap<>();
    }

    public void loadAllCrates() {
        File oder = new File(CratePlugin.plugin.getDataFolder() + "/Crates/");
        if(oder.exists()) {
            for(File files : oder.listFiles()) {
                if(files == null)break;
                String crateName = files.getName().replace(".yml", "");
                Crate crate = new Crate(crateName);
                crate.load();
                storedCrates.put(crateName, crate);
            }
        }
    }

    public void saveAllCrates() {
        if(!getStoredCrates().isEmpty()) {
            for(Crate crates : getStoredCrates().values()){
                if(crates == null)break;
                crates.save();
            }
        }
    }

    public HashMap<String, Crate> getStoredCrates() {
        return storedCrates;
    }
}
