package com.github.manu585.util;

import com.github.manu585.JustEnoughAbilities;

public class JustEnoughAbilitiesUtils {
    public static String getVersion() {
        return JustEnoughAbilities.getInstance().getPluginMeta().getVersion();
    }

    public static String getAuthor() {
        return JustEnoughAbilities.getInstance().getPluginMeta().getAuthors().getFirst();
    }

    public static String getName() {
        return JustEnoughAbilities.getInstance().getPluginMeta().getName();
    }
}
