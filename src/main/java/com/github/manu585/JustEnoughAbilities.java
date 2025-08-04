package com.github.manu585;

import com.github.manu585.bending.air.listener.AirAbilityListener;
import com.github.manu585.configuration.ConfigManager;
import com.github.manu585.listener.BendingCommandConfigReloadListener;
import com.github.retrooper.packetevents.PacketEvents;
import com.projectkorra.projectkorra.ability.CoreAbility;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class JustEnoughAbilities extends JavaPlugin {
    private static JustEnoughAbilities plugin;

    private ConfigManager configManager;

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        plugin = this;
        PacketEvents.getAPI().init();

        this.configManager = new ConfigManager(plugin);
        CoreAbility.registerPluginAbilities(plugin, "com.github.manu585.bending");

        registerListeners(
                new BendingCommandConfigReloadListener(plugin),
                new AirAbilityListener()
        );
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> plugin.getServer().getPluginManager().registerEvents(listener, plugin));
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static JustEnoughAbilities getInstance() {
        return plugin;
    }
}
