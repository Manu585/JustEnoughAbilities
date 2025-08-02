package com.github.manu585.listener;

import com.github.manu585.JustEnoughAbilities;
import com.github.manu585.configuration.ConfigManager;
import com.projectkorra.projectkorra.event.BendingReloadEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class ConfigReloadListener implements Listener {
    private final JustEnoughAbilities plugin;

    public ConfigReloadListener(JustEnoughAbilities plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBendingReload(BendingReloadEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (event.getSender() instanceof Player player) player.sendMessage(
                        MiniMessage.miniMessage().deserialize("<color:#e8c410>JustEnoughAbilities config reloaded!</color>")
                );

                ConfigManager.getDefaultConfigInstance().reload();
                ConfigManager.getDefaultConfigInstance().save();
            }
        }.runTaskLaterAsynchronously(plugin, 2); // Small delay
    }

    public JustEnoughAbilities getPlugin() {
        return plugin;
    }
}
