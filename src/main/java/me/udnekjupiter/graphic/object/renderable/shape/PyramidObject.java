package me.udnekjupiter.graphic.object.renderable.shape;

import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.Vector3d;

import java.util.function.Consumer;

public class PyramidObject extends GraphicObject3d {
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
    public RenderableTriangle @NotNull [] getRenderTriangles() {
        return new RenderableTriangle[]{plane0.copy(), plane1.copy(), plane2.copy(), planeBottom.copy()};
    }

    @Override
    public RenderableTriangle @NotNull [] getUnsafeRenderTriangles() {
        return new RenderableTriangle[]{plane0, plane1, plane2, planeBottom};
    }

    @Override
    public void getUnsafeRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer) {
        consumer.accept(plane0);
        consumer.accept(plane1);
        consumer.accept(plane2);
        consumer.accept(planeBottom);
    }
}
