package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.container.PhysicVariableContainer;
import me.udnekjupiter.util.Freezable;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public interface PhysicObject3d extends Freezable, PhysicObject<PhysicVariableContainer>{
    //@NotNull Vector3d getAppliedForce(@NotNull Vector3d position);
    void calculateForces(@NotNull Vector3d position);
}
