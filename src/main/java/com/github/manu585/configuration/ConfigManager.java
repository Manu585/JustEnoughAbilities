package com.github.manu585.configuration;

import com.github.manu585.JustEnoughAbilities;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private static final Config defaultConfig = new Config(JustEnoughAbilities.getInstance(), "config.yml");

    public ConfigManager() {
        createDefaults();
    }

    private void createDefaults() {
        FileConfiguration config = defaultConfig.get();

        defaultConfig.save();
    }

    public static FileConfiguration getDefaultConfig() {
        return defaultConfig.get();
    }

    public static Config getDefaultConfigInstance() {
        return defaultConfig;
    }
}
