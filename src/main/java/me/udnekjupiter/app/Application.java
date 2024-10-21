package me.udnekjupiter.app;

import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.physic.engine.PhysicEngine;
import org.jetbrains.annotations.NotNull;

public interface Application {
    double getFrameDeltaTime();
    @NotNull PhysicEngine<?> getPhysicEngine();
    @NotNull GraphicEngine getGraphicEngine();
    void initialize(@NotNull GraphicEngine graphicEngine, @NotNull PhysicEngine<?> physicEngine);
    void start();
    void stop();
    void addDebugInformation();
}
