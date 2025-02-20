package me.udnekjupiter.util.utilityinterface;

import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.vector.Vector3d;

public interface Positioned {
    @NotNull Vector3d getPosition();
    void setPosition(@NotNull Vector3d position);
    void move(@NotNull Vector3d position);
    void move(double x, double y, double z);

}
