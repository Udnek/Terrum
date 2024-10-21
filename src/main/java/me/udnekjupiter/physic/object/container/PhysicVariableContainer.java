package me.udnekjupiter.physic.object.container;

import org.realityforge.vecmath.Vector3d;

public abstract class PhysicVariableContainer implements VariableContainer {
    Vector3d position;
    Vector3d velocity;
    Vector3d acceleration;
    Vector3d initialPosition;
    double mass;
}
