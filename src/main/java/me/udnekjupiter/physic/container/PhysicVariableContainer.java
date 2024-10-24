package me.udnekjupiter.physic.container;

import me.udnekjupiter.util.VariableContainer;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class PhysicVariableContainer implements VariableContainer {
    public @NotNull Vector3d position;
    public @NotNull Vector3d velocity = new Vector3d();
    public @NotNull Vector3d acceleration = new Vector3d();
    public @NotNull Vector3d initialPosition;
    public @NotNull Vector3d appliedForce = new Vector3d();
    public @NotNull Vector3d positionDifferential = new Vector3d();
    public @NotNull Vector3d velocityDifferential = new Vector3d();
    public double mass = 1;
    public PhysicVariableContainer(@NotNull Vector3d position){
        this.position = position;
        initialPosition = position.dup();
    }
    public @NotNull Vector3d getPosition(){
        return position;
    }
    public @NotNull Vector3d getVelocity(){
        return velocity;
    }
}
