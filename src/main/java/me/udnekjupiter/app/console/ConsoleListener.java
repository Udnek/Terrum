package me.udnekjupiter.app.console;

import org.jetbrains.annotations.NotNull;

public interface ConsoleListener {
    void handleCommand(@NotNull Command command, @NotNull Object[] args);

}
