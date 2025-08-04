package com.github.manu585.bending;

import com.github.manu585.JustEnoughAbilities;
import org.bukkit.configuration.file.FileConfiguration;

public interface JeaAbility {
    default FileConfiguration getJeaConfig() {
        return JustEnoughAbilities.getInstance().getConfigManager().getDefaultConfig();
    }
}
