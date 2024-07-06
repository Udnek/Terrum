package me.udnekjupiter.graphic.object.traceable.shape;

import me.udnekjupiter.graphic.object.traceable.TraceableObject;
import me.udnekjupiter.graphic.triangle.TraceableTriangle;
import org.realityforge.vecmath.Vector3d;

public class PyramidObject extends TraceableObject {
    private TraceableTriangle plane0;
    private TraceableTriangle plane1;
    private TraceableTriangle plane2;
    private TraceableTriangle planeBottom;

    public PyramidObject(Vector3d position,
                         Vector3d vertex,
                         Vector3d vertexBottom0,
                         Vector3d vertexBottom1,
                         Vector3d vertexBottom2) {
        super(position);
        planeBottom = new TraceableTriangle(vertexBottom0, vertexBottom1, vertexBottom2);
        plane0 = new TraceableTriangle(vertexBottom0, vertexBottom1, vertex);
        plane1 = new TraceableTriangle(vertexBottom1, vertexBottom2, vertex);
        plane2 = new TraceableTriangle(vertexBottom2, vertexBottom0, vertex);

    }


    @Override
    public TraceableTriangle[] getRenderTriangles() {
        return new TraceableTriangle[]{plane0.copy(), plane1.copy(), plane2.copy(), planeBottom.copy()};
    }
}
