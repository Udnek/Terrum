package me.udnek.object;

import me.udnek.util.PositionedObject;
import me.udnek.util.Triangle;
import org.realityforge.vecmath.Vector3d;

public abstract class SceneObject extends PositionedObject {

    public SceneObject(Vector3d position) {
        super(position);
    }

    public abstract Triangle[] getRenderTriangles();
}
