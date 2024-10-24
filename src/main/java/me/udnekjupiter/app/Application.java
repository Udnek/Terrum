package me.udnekjupiter.app;

import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.physic.engine.PhysicEngine;
import org.jetbrains.annotations.NotNull;

public interface Application {

    // TODO: 7/4/2024 SOMETHING ABOUT TPS, IPT, DELTA TIME
    public static final int PHYSIC_TICKS_PER_SECOND = 50;

    double getFrameDeltaTime();
    @NotNull PhysicEngine<?> getPhysicEngine();
    @NotNull GraphicEngine getGraphicEngine();
    void initialize(@NotNull GraphicEngine graphicEngine, @NotNull PhysicEngine<?> physicEngine);
    void start();
    void stop();
    void addDebugInformation();
    @NotNull ApplicationSettings getSettings();
    @NotNull DebugMenu getDebugMenu();
}
