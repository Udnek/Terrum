package me.udnekjupiter.graphic.object.light;

import me.udnekjupiter.graphic.object.renderable.RenderableObject;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.realityforge.vecmath.Vector3d;

public abstract class LightSource extends RenderableObject {
    public LightSource(Vector3d position) {
        super(position);
    }

    @Override
    public RenderableTriangle[] getRenderTriangles() {
        return new RenderableTriangle[]{new RenderableTriangle(new Vector3d(0,0.1,0), new Vector3d(0.2, 0.1, 0.2), new Vector3d(-0.2, 0.1, 0.2))};
    }
}
