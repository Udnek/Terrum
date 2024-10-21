package me.udnekjupiter.graphic.object.renderable.shape;

import me.udnekjupiter.graphic.object.renderable.RenderableObject3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.realityforge.vecmath.Vector3d;

public class PyramidObject extends RenderableObject3d {
    private RenderableTriangle plane0;
    private RenderableTriangle plane1;
    private RenderableTriangle plane2;
    private RenderableTriangle planeBottom;

    public PyramidObject(Vector3d position,
                         Vector3d vertex,
                         Vector3d vertexBottom0,
                         Vector3d vertexBottom1,
                         Vector3d vertexBottom2) {
        super(position);
        planeBottom = new RenderableTriangle(vertexBottom0, vertexBottom1, vertexBottom2);
        plane0 = new RenderableTriangle(vertexBottom0, vertexBottom1, vertex);
        plane1 = new RenderableTriangle(vertexBottom1, vertexBottom2, vertex);
        plane2 = new RenderableTriangle(vertexBottom2, vertexBottom0, vertex);

    }


    @Override
    public RenderableTriangle[] getRenderTriangles() {
        return new RenderableTriangle[]{plane0.copy(), plane1.copy(), plane2.copy(), planeBottom.copy()};
    }

    @Override
    public RenderableTriangle[] getUnsafeRenderTriangles() {
        return new RenderableTriangle[]{plane0, plane1, plane2, planeBottom};
    }
}
