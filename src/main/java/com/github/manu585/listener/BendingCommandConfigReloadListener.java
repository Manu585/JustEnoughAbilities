package com.github.manu585.listener;

import com.github.manu585.JustEnoughAbilities;
import com.github.manu585.configuration.ConfigManager;
import com.github.manu585.event.PkCommandEvent;
import com.github.manu585.util.JustEnoughAbilitiesUtils;
import com.projectkorra.projectkorra.command.Commands;
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

import java.util.Arrays;

/**
 * Credits for listening to bending commands: Jedk1
 */
public class BendingCommandConfigReloadListener implements Listener {
    private final JustEnoughAbilities plugin;
    private final String[] commandAliases = {"/b", "/pk", "/projectkorra", "/bending", "/mtla", "/tla", "/korra", "/bend"};

    public BendingCommandConfigReloadListener(final JustEnoughAbilities plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBendingReload(final BendingReloadEvent event) {
        if (event.getSender() instanceof Player player) player.sendMessage(
                MiniMessage.miniMessage().deserialize("<color:#e8c410>JustEnoughAbilities config reloaded!</color>")
        );

        new BukkitRunnable() {
            @Override
            public void run() {
                ConfigManager.getDefaultConfigInstance().reload();
                ConfigManager.getDefaultConfigInstance().save();
            }
        }.runTaskLaterAsynchronously(plugin, 2); // Small delay
    }

    @EventHandler
    public void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().toLowerCase();
        String[] args = command.split("\\s+");

        if (Arrays.asList(commandAliases).contains(args[0]) && args.length >= 2) {
            PkCommandEvent pkCommandEvent = new PkCommandEvent(event.getPlayer(), args, null);
            for (PKCommand pkCommand : PKCommand.instances.values()) {
                if (Arrays.asList(pkCommand.getAliases()).contains(args[1].toLowerCase())) {
                    pkCommandEvent = new PkCommandEvent(event.getPlayer(), args, PkCommandEvent.CommandType.getType(pkCommand.getName()));
                }
            }
            plugin.getServer().getPluginManager().callEvent(pkCommandEvent);
        }
    }

    @EventHandler
    public void onPkCommand(final PkCommandEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (event.getType() == null) return;

                switch (event.getType()) {
                    case WHO -> {
                        if (!event.getSender().hasPermission("bending.command.who")) return;
                        if (event.getArgs().length <= 2) return;
                        if (event.getArgs().length > 3) return;

                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(event.getArgs()[2]);
                        if (!offlinePlayer.isOnline()) return;

                        if (!Arrays.asList(JustEnoughAbilitiesUtils.getDeveloperUuids()).contains(offlinePlayer.getUniqueId().toString())) return;

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
