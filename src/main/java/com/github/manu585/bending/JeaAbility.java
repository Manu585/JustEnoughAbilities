package com.github.manu585.bending;

import com.github.manu585.JustEnoughAbilities;
import com.github.manu585.util.JustEnoughAbilitiesUtils;
import com.projectkorra.projectkorra.util.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public interface JeaAbility {
    default FileConfiguration getJeaConfig() {
        return JustEnoughAbilities.getInstance().getConfigManager().getDefaultConfig();
    }

    default String getJeaVersion() {
        return JustEnoughAbilitiesUtils.getVersion();
    }

    /**
     * Get all the author names formatted with '&' and ','
     * @return The formatted String
     */
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

    /**
     * Get all the JEA authors with the name param being highlighted
     * @param highlighted The name of the Player who should be highlighted
     * @return The formatted String
     */
    default String getJeaAuthors(String highlighted) {
        List<String> authors = JustEnoughAbilitiesUtils.getAuthors();
        if (authors.isEmpty()) return "";
        if (authors.size() == 1) {
            String author = authors.getFirst();
            return author.equalsIgnoreCase(highlighted) ? "&#e8c410" + author + "&f" : "&f" + author;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < authors.size(); i++) {
            String author = authors.get(i);
            if (author.equalsIgnoreCase(highlighted)) {
                builder.append("&#e8c410").append(author).append("&f");
            } else {
                builder.append("&f").append(author);
            }

            if (i < authors.size() - 2) {
                builder.append("&7, ");
            } else if (i == authors.size() - 2) {
                builder.append("&7 & ");
            }
        }
        return builder.toString();
    }

    /**
     * Get the colored String of author names
     * @param name The name of the Player who should be highlighted
     * @return Colored String
     */
    default String getJeaAuthorsColored(String name) {
        return ChatUtil.color(getJeaAuthors(name));
    }
}
