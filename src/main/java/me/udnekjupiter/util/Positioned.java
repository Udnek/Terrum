package me.udnekjupiter.util;

import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.Vector3d;

public interface Positioned {
    @NotNull Vector3d getPosition();
    void setPosition(@NotNull Vector3d position);
    void move(@NotNull Vector3d position);
    void move(double x, double y, double z);

}
