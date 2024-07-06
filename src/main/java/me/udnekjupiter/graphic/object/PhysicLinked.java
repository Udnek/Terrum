package me.udnekjupiter.graphic.object;

import me.udnekjupiter.physic.object.PhysicObject;

public interface PhysicLinked {
    void synchronizeWithPhysic();
    PhysicObject getPhysicRepresentation();
}
