package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.object.GraphicObject;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.realityforge.vecmath.Vector3d;

public abstract class RenderableObject extends GraphicObject {
    public RenderableObject(Vector3d position) {
        super(position);
    }
    public abstract RenderableTriangle[] getRenderTriangles();
}
