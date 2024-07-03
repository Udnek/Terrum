package me.jupiter.object;

import org.realityforge.vecmath.Vector3d;

public abstract class RigidBody extends SimulationObject {
    public RigidBody(Vector3d position) {
        super(position);
    }
}
