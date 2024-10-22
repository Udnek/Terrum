package me.udnekjupiter.physic.object.container;

import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class EulerContainer extends PhysicVariableContainer {
    public @NotNull Vector3d positionDifferential = new Vector3d();
    public @NotNull Vector3d velocityDifferential = new Vector3d();

    public EulerContainer(@NotNull Vector3d position) {
        super(position);
    }

    public EulerContainer(@NotNull PhysicVariableContainer other)
    {
        super(other.position);
        this.velocity = other.velocity;
        this.acceleration = other.acceleration;
        this.initialPosition = other.initialPosition;
        this.mass = other.mass;
    }
}
