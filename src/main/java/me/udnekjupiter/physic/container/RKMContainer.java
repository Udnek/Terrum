package me.udnekjupiter.physic.container;

import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class RKMContainer extends PhysicVariableContainer {
    public @NotNull Vector3d[] currentPhaseVector = new Vector3d[]{new Vector3d(), new Vector3d()};
    public @NotNull Vector3d[] basePhaseVector = new Vector3d[]{position, velocity};
    public @NotNull Vector3d[] coefficient1 = new Vector3d[]{new Vector3d(), new Vector3d()};
    public @NotNull Vector3d[] coefficient2 = new Vector3d[]{new Vector3d(), new Vector3d()};
    public @NotNull Vector3d[] coefficient3 = new Vector3d[]{new Vector3d(), new Vector3d()};
    public @NotNull Vector3d[] coefficient4 = new Vector3d[]{new Vector3d(), new Vector3d()};
    public int coefficientCounter = 1;

    public RKMContainer(@NotNull Vector3d position)
    {
        super(position);
    }

    public RKMContainer(@NotNull PhysicVariableContainer other)
    {
        super(other.position);
        this.velocity = other.velocity;
        this.acceleration = other.acceleration;
        this.initialPosition = other.initialPosition;
        this.appliedForce = other.appliedForce;
        this.mass = other.mass;
        this.currentPhaseVector[0] = other.position.dup();
        this.currentPhaseVector[1] = other.velocity.dup();
    }

    @Override
    public @NotNull Vector3d getPosition(){
        return currentPhaseVector[0];
    }
    @Override
    public @NotNull Vector3d getVelocity(){
        return currentPhaseVector[1];
    }
}
