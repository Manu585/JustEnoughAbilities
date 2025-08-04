package com.github.manu585.configuration;

import com.github.manu585.JustEnoughAbilities;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final JustEnoughAbilities plugin;
    private final Config defaultConfig;

    public ConfigManager(JustEnoughAbilities plugin) {
        this.plugin = plugin;
        this.defaultConfig = new Config(plugin, "config.yml");

        createDefaults();
    }

    private void createDefaults() {
        FileConfiguration config = defaultConfig.get();

        defaultConfig.save();
    }

    public FileConfiguration getDefaultConfig() {
        return defaultConfig.get();
    }

    public Config getDefaultConfigInstance() {
        return defaultConfig;
    }

    public JustEnoughAbilities getPlugin() {
        return plugin;
    }
}
