package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.collision.Collider;
import me.udnekjupiter.util.PositionedObject;
import me.udnekjupiter.util.Resettable;
import org.realityforge.vecmath.Vector3d;

import java.util.ResourceBundle;

public abstract class PhysicObject extends PositionedObject implements Resettable {
    protected final Vector3d initialPosition;

    public PhysicObject(Vector3d position) {
        super(position);
        this.initialPosition = position.dup();
    }

    public Vector3d getInitialPosition() {
        return initialPosition.dup();
    }

}
