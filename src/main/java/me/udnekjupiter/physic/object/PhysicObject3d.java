package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.container.PhysicVariableContainer;
import me.udnekjupiter.util.utilityinterface.Freezable;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.vector.Vector3d;

public interface PhysicObject3d extends Freezable, PhysicObject<PhysicVariableContainer>{
    //@NotNull Vector3d getAppliedForce(@NotNull Vector3d position);
    void calculateForces();
    @NotNull Vector3d getVelocity();
}
