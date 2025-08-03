package com.github.manu585.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PkCommandEvent extends Event {
    public enum CommandType {
        VERSION,
        WHO,
        JEA;

        public static CommandType getType(String type) {
            for (CommandType element : CommandType.values()) {
                if (element.toString().equalsIgnoreCase(type)) {
                    return element;
                }
            }
            return null;
        }
    }

    public static final HandlerList handlers = new HandlerList();

    private final Player sender;
    private final String[] args;
    private final CommandType type;

    public PkCommandEvent(Player sender, String[] args, CommandType type) {
        this.sender = sender;
        this.type = type;
        this.args = args;
    }

    public Player getSender() {
        return sender;
    }

    public String[] getArgs() {
        return args;
    }

    public CommandType getType() {
        return type;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
