package com.github.manu585;

import com.github.manu585.listener.bending.AbilityListener;
import com.github.manu585.configuration.ConfigManager;
import com.github.manu585.listener.BendingCommandConfigReloadListener;
import com.github.manu585.util.AuthorCache;
import com.github.retrooper.packetevents.PacketEvents;
import com.projectkorra.projectkorra.ability.CoreAbility;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class JustEnoughAbilities extends JavaPlugin {
    private static JustEnoughAbilities plugin;

    private ConfigManager configManager;
    private AuthorCache authorCache;

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        plugin = this;

        PacketEvents.getAPI().init();
        this.authorCache = new AuthorCache(plugin);
        this.authorCache.cacheAuthorsUuidsAsync(getPluginMeta().getAuthors());
        this.configManager = new ConfigManager(plugin);

        CoreAbility.registerPluginAbilities(plugin, "com.github.manu585.bending");

        registerListeners(
                new BendingCommandConfigReloadListener(plugin),
                new AbilityListener()
        );
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> plugin.getServer().getPluginManager().registerEvents(listener, plugin));
    }

    public static JustEnoughAbilities getInstance() {
        return plugin;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public AuthorCache getAuthorCache() {
        return authorCache;
    }
}
