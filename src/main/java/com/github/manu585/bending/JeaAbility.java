package com.github.manu585.bending;

import com.github.manu585.JustEnoughAbilities;
import com.github.manu585.util.JustEnoughAbilitiesUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public interface JeaAbility {
    default FileConfiguration getJeaConfig() {
        return JustEnoughAbilities.getInstance().getConfigManager().getDefaultConfig();
    }

    default String getJeaVersion() {
        return JustEnoughAbilitiesUtils.getVersion();
    }

    default String getJeaAuthors() {
        List<String> authors = JustEnoughAbilitiesUtils.getAuthors();
        if (authors.isEmpty()) return "";
        if (authors.size() == 1) return authors.getFirst();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < authors.size(); i++) {
            builder.append(authors.get(i));
            if (i < authors.size() - 2) {
                builder.append(", ");
            } else if (i == authors.size() - 2) {
                builder.append(" & ");
            }
        }
        return builder.toString();
    }
}
