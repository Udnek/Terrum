package me.udnek.objects;

import me.udnek.utils.PositionedObject;
import me.udnek.utils.Triangle;
import org.realityforge.vecmath.Vector3d;

public abstract class SceneObject extends PositionedObject {

    public SceneObject(Vector3d position) {
        super(position);
    }

    public abstract Triangle[] getRenderTriangles();
}
