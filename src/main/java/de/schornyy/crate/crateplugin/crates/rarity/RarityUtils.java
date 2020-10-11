package de.schornyy.crate.crateplugin.crates.rarity;

import de.schornyy.crate.crateplugin.CratePlugin;

public class RarityUtils {

    public static Rarity getRarityByName(String name) {
        return CratePlugin.plugin.getRarityManager().getStoredRaritys().get(name);
    }
}
