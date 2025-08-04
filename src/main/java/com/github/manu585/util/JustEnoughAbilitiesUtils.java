package com.github.manu585.util;

import com.github.manu585.JustEnoughAbilities;

import java.util.List;

public class JustEnoughAbilitiesUtils {
    public static String getVersion() {
        return JustEnoughAbilities.getInstance().getPluginMeta().getVersion();
    }

    public static List<String> getAuthors() {
        return JustEnoughAbilities.getInstance().getPluginMeta().getAuthors();
    }

    public static String getName() {
        return JustEnoughAbilities.getInstance().getPluginMeta().getName();
    }
}
