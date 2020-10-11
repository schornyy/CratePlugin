package de.schornyy.crate.crateplugin.configs;

public class ConfigManager {

    private SettingsConfig settingsConfig;
    private MessagesConfig messagesConfig;

    public ConfigManager() {
        settingsConfig = new SettingsConfig();
        settingsConfig.load();
        messagesConfig = new MessagesConfig();
    }

    public MessagesConfig getMessagesConfig() {
        return messagesConfig;
    }

    public SettingsConfig getSettingsConfig() {
        return settingsConfig;
    }
}
