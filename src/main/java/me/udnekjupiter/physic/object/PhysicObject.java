package me.udnekjupiter.physic.object;

import me.udnekjupiter.util.PositionedObject;
import org.realityforge.vecmath.Vector3d;

public abstract class PhysicObject extends PositionedObject {
    public PhysicObject(Vector3d position) {
        super(position);
    }
}
