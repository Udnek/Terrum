package me.udnekjupiter.graphic.object.traceable;

import me.udnekjupiter.graphic.object.GraphicObject;
import me.udnekjupiter.util.Triangle;
import org.realityforge.vecmath.Vector3d;

public abstract class TraceableObject extends GraphicObject {
    public TraceableObject(Vector3d position) {
        super(position);
    }
    public abstract Triangle[] getRenderTriangles();
}
