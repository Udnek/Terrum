package me.udnekjupiter.app.controller;

import org.jetbrains.annotations.NotNull;

public interface ControllerListener {
    void keyEvent(@NotNull InputKey inputKey, boolean pressed);
}
