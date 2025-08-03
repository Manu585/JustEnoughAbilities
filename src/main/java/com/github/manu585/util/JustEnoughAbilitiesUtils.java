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

    public static String[] getDeveloperUuids() {
        return new String[]
                { "7d283a87-378d-4384-b748-3480dc7d3814" };
    }
}
