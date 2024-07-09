package me.udnekjupiter.physic.object;

import me.udnekjupiter.util.PositionedObject;
import org.realityforge.vecmath.Vector3d;

public abstract class PhysicObject extends PositionedObject {
    protected final Vector3d initialPosition;
    public PhysicObject(Vector3d position) {
        super(position);
        this.initialPosition = position.dup();
    }

    public Vector3d getInitialPosition() {return initialPosition.dup();}
}
