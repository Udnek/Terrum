package me.udnekjupiter.graphic.object;

import me.udnekjupiter.physic.object.PhysicObject;
import org.jetbrains.annotations.NotNull;

public interface PhysicLinked {
    void synchronizeWithPhysic();
    @NotNull PhysicObject getPhysicRepresentation();
}
