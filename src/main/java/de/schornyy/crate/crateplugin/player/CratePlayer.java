package de.schornyy.crate.crateplugin.player;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.crates.CrateRoll;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CratePlayer {

    private File file;
    private FileConfiguration cfg;

    private boolean allowAnimation, opening;
    private Player player;
    private CrateRoll crateRoll_opening;

    public CratePlayer(Player player) {
        this.player = player;
        file = new File(CratePlugin.plugin.getDataFolder() + "/Player/" + player.getName() + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        opening = false;
        crateRoll_opening = null;
        load();
    }

    public void save() {
        getCfg().set("AllowAnimations", isAllowAnimation());

        try {
            getCfg().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        CratePlugin.plugin.getCratePlayerManager().getStoredPlayer().remove(player);
    }

    private void load() {
        if(!getFile().exists()) {

            getCfg().set("AllowAnimations", true);

            try {
                getCfg().save(getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        allowAnimation = getCfg().getBoolean("AllowAnimations");
        CratePlugin.plugin.getCratePlayerManager().getStoredPlayer().put(player, this);
    }

    public CrateRoll getCrateRoll_opening() {
        return crateRoll_opening;
    }

    public void setCrateRoll_opening(CrateRoll crateRoll_opening) {
        this.crateRoll_opening = crateRoll_opening;
    }

    public void setAllowAnimation(boolean allowAnimation) {
        this.allowAnimation = allowAnimation;
    }

    public void setOpening(boolean opening) {
        this.opening = opening;
    }

    public boolean isAllowAnimation() {
        return allowAnimation;
    }

    public boolean isOpening() {
        return opening;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }



}
