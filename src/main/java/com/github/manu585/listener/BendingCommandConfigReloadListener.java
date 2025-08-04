package com.github.manu585.listener;

import com.github.manu585.JustEnoughAbilities;
import com.github.manu585.event.PkCommandEvent;
import com.github.manu585.util.JustEnoughAbilitiesUtils;
import com.projectkorra.projectkorra.command.PKCommand;
import com.projectkorra.projectkorra.event.BendingReloadEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Credits for listening to bending commands: Jedk1
 */
public class BendingCommandConfigReloadListener implements Listener {
    private final JustEnoughAbilities plugin;

    private final Map<String, PkCommandEvent.CommandType> aliasToType = new HashMap<>();
    private final Set<String> baseCommand = Set.of("/b", "/pk", "/projectkorra", "/bending", "/mtla", "/tla", "/korra", "/bend");

    public BendingCommandConfigReloadListener(final JustEnoughAbilities plugin) {
        this.plugin = plugin;

        for (PKCommand command : PKCommand.instances.values()) {
            PkCommandEvent.CommandType type = PkCommandEvent.CommandType.getType(command.getName());
            if (type == null) continue;
            for (String alias : command.getAliases()) {
                aliasToType.put(alias.toLowerCase(), type);
            }
        }
    }

    @EventHandler
    public void onBendingReload(final BendingReloadEvent event) {
        if (event.getSender() instanceof Player player) player.sendMessage(
                MiniMessage.miniMessage().deserialize("<color:#e8c410>JustEnoughAbilities config reloaded!</color>")
        );

        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.getConfigManager().getDefaultConfigInstance().reload();
                plugin.getConfigManager().getDefaultConfigInstance().save();
            }
        }.runTaskLaterAsynchronously(plugin, 2); // Small delay
    }

    @EventHandler
    public void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
        String[] split = event.getMessage().split("\\s+");
        String command = split[0].toLowerCase();

        if (baseCommand.contains(command) && split.length >= 2) {
            PkCommandEvent.CommandType type = aliasToType.get(split[1].toLowerCase());
            plugin.getServer().getPluginManager().callEvent(new PkCommandEvent(event.getPlayer(), split, type));
        }
    }

    @EventHandler
    public void onPkCommand(final PkCommandEvent event) {
        if (event.getType() == null) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                switch (event.getType()) {
                    case WHO -> {
                        if (!event.getSender().hasPermission("bending.command.who")) return;
                        if (event.getArgs().length <= 2 || event.getArgs().length > 3) return;

                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(event.getArgs()[2]);
                        if (!offlinePlayer.isOnline()) return;

                        if (!plugin.getAuthorCache().isDeveloper(offlinePlayer.getUniqueId())) return;

                        event.getSender().sendMessage(MiniMessage.miniMessage().deserialize("<color:#e8c410>JustEnoughAbilities Developer</color>"));
                    }
                    case VERSION -> {
                        if (!event.getSender().hasPermission("bending.command.version")) return;
                        if (event.getArgs().length > 2) return;

                        event.getSender().sendMessage(MiniMessage.miniMessage().deserialize(
                                "<color:#e8c410>" + JustEnoughAbilitiesUtils.getName() + "</color>"
                                        + " <color:#828282>-</color>"
                                        + " <color:#e8c410>" + JustEnoughAbilitiesUtils.getVersion()));
                    }
                }
            }
        }.runTaskLater(plugin, 2);
    }

    public JustEnoughAbilities getPlugin() {
        return plugin;
    }
}
