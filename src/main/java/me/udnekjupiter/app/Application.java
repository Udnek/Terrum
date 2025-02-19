package me.udnekjupiter.app;

import me.udnekjupiter.app.util.ApplicationSettings;
import me.udnekjupiter.app.util.DebugMenu;
import me.udnekjupiter.app.window.Window;
import me.udnekjupiter.physic.engine.PhysicEngine;
import me.udnekjupiter.util.Initializable;
import org.jetbrains.annotations.NotNull;

public interface Application extends Initializable {

    // TODO: 7/4/2024 SOMETHING ABOUT TPS, IPT, DELTA TIME
    int PHYSIC_TICKS_PER_SECOND = 50;

    double getFrameDeltaTime();
    @NotNull PhysicEngine<?> getPhysicEngine();
    void start();
    boolean isRunning();
    void stop();
    void addDebugInformation();
    @NotNull ApplicationSettings getSettings();
    @NotNull DebugMenu getDebugMenu();
    @NotNull Window getWindow();
}
