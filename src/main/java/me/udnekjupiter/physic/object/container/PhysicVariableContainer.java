package me.udnekjupiter.physic.object.container;

import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public abstract class PhysicVariableContainer implements VariableContainer {
    public @NotNull Vector3d position;
    public @NotNull Vector3d velocity = new Vector3d();
    public @NotNull Vector3d acceleration = new Vector3d();
    public @NotNull Vector3d initialPosition;
    public double mass;
    public PhysicVariableContainer(@NotNull Vector3d position){
        this.position = position;
        initialPosition = position.dup();
    }
}
