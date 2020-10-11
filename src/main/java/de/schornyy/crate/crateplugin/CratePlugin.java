package de.schornyy.crate.crateplugin;

import de.schornyy.crate.crateplugin.commands.*;
import de.schornyy.crate.crateplugin.configs.ConfigManager;
import de.schornyy.crate.crateplugin.crates.CrateManager;
import de.schornyy.crate.crateplugin.crates.rarity.RarityManager;
import de.schornyy.crate.crateplugin.interactableItems.InteractItemManager;
import de.schornyy.crate.crateplugin.listener.*;
import de.schornyy.crate.crateplugin.player.CratePlayerManager;
import de.schornyy.crate.crateplugin.shop.Shop;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class CratePlugin extends JavaPlugin {

    public static CratePlugin plugin;
    private RarityManager rarityManager;
    private ConfigManager configManager;
    private CrateManager crateManager;
    private CratePlayerManager cratePlayerManager;
    private Shop shop;
    private InteractItemManager interactItemManager;
    private static Economy econ = null;

    @Override
    public void onEnable() {
        loadInits();
        loadCommands();
        loadListener();
    }

    @Override
    public void onDisable() {
        crateManager.saveAllCrates();
        cratePlayerManager.saveAllPlayers();
        shop.save();
        interactItemManager.save();
    }

    private void loadCommands() {
        getCommand("crate").setExecutor(new CrateCommand());
        getCommand("settings").setExecutor(new SettingsCommand());
        getCommand("openall").setExecutor(new OpenAllCommand());
        getCommand("interactitem").setExecutor(new InteractItemCommand());
        getCommand("shop").setExecutor(new ShopCommand());
    }

    private void loadListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new PlayerInteractInventoryListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new PlayerCloseInventoryListener(), this);
    }

    private  void loadInits() {
        plugin = this;
        if (accessAlowed()) {
            rarityManager = new RarityManager();
            rarityManager.load();

            configManager = new ConfigManager();

            crateManager = new CrateManager();
            crateManager.loadAllCrates();

            cratePlayerManager = new CratePlayerManager();
            cratePlayerManager.loadAllPlayer();

            shop = new Shop();

            interactItemManager = new InteractItemManager();
            interactItemManager.load();

        }
        setupEconomy();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public InteractItemManager getInteractItemManager() {
        return interactItemManager;
    }

    public Shop getShop() {
        return shop;
    }

    public CratePlayerManager getCratePlayerManager() {
        return cratePlayerManager;
    }

    public CrateManager getCrateManager() {
        return crateManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public RarityManager getRarityManager() {
        return rarityManager;
    }

    public static Economy getEcon() {
        return econ;
    }

    public boolean accessAlowed() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime acces = LocalDateTime.of(2020, 10,7,0,0,0);

        if(now.isAfter(acces)) {
            return false;
        }

        return true;
    }
}
