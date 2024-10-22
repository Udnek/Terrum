package me.udnekjupiter.graphic.object;

import me.udnekjupiter.physic.object.PhysicObject3d;
import org.jetbrains.annotations.NotNull;

public interface PhysicLinked {
    void synchronizeWithPhysic();
    @NotNull PhysicObject3d getPhysicRepresentation();
}
