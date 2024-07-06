package me.udnekjupiter.graphic.object.light;

import me.udnekjupiter.graphic.object.traceable.TraceableObject;
import me.udnekjupiter.graphic.triangle.TraceableTriangle;
import org.realityforge.vecmath.Vector3d;

public abstract class LightSource extends TraceableObject {
    public LightSource(Vector3d position) {
        super(position);
    }

    @Override
    public TraceableTriangle[] getRenderTriangles() {
        return new TraceableTriangle[]{new TraceableTriangle(new Vector3d(0,0.1,0), new Vector3d(0.2, 0.1, 0.2), new Vector3d(-0.2, 0.1, 0.2))};
    }
}
