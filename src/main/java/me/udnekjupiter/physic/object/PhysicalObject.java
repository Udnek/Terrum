package me.udnekjupiter.physic.object;

import me.udnekjupiter.util.PositionedObject;
import org.realityforge.vecmath.Vector3d;

public abstract class PhysicalObject extends PositionedObject {
    public PhysicalObject(Vector3d position) {
        super(position);
    }
}
