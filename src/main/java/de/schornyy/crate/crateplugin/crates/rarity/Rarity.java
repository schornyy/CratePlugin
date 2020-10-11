package de.schornyy.crate.crateplugin.crates.rarity;

public class Rarity {

    private String rawRarity, rarity;

    public Rarity(String rarity, String rawRarity) {
        this.rarity = rarity;
        this.rawRarity = rawRarity;
    }

    public String getRarity() {
        return rarity;
    }

    public String getRawRarity() {
        return rawRarity;
    }
}
