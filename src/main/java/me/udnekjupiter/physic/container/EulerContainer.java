package me.udnekjupiter.physic.container;

import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class EulerContainer extends PhysicVariableContainer {

    public EulerContainer(@NotNull Vector3d position) {
        super(position);
    }

    public EulerContainer(@NotNull PhysicVariableContainer other)
    {
        super(other.position.dup());
        this.velocity = other.velocity.dup();
        this.acceleration = other.acceleration.dup();
        this.initialPosition = other.initialPosition.dup();
        this.appliedForce = other.appliedForce.dup();
        this.mass = other.mass;
    }
}
