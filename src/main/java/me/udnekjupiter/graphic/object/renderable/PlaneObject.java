package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.triangle.ColoredTriangle;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.function.Consumer;

public class PlaneObject extends GraphicObject3d {

    protected RenderableTriangle plane0;
    protected RenderableTriangle plane1;

    public PlaneObject(@NotNull Vector3d position, double x0, double z0, double x1, double z1, double y) {
        super(position);
        plane0 = new RenderableTriangle(new Vector3d(x0, y, z0), new Vector3d(x1, y, z1), new Vector3d(x0, y, z1));
        plane1 = new RenderableTriangle(new Vector3d(x0, y, z0), new Vector3d(x1, y, z1), new Vector3d(x1, y, z0));
    }
    public PlaneObject(@NotNull Vector3d position, double x0, double z0, double x1, double z1, double y, @NotNull Color color) {
        super(position);
        plane0 = new ColoredTriangle(new Vector3d(x0, y, z0), new Vector3d(x1, y, z1), new Vector3d(x0, y, z1), color);
        plane1 = new ColoredTriangle(new Vector3d(x0, y, z0), new Vector3d(x1, y, z1), new Vector3d(x1, y, z0), color);
    }


    @Override
    public @NotNull RenderableTriangle @NotNull [] getRenderTriangles() {return new RenderableTriangle[]{plane0.copy(), plane1.copy()};}
    @Override
    public @NotNull RenderableTriangle @NotNull [] getUnsafeRenderTriangles() {
        return new RenderableTriangle[]{plane0, plane1};
    }

    @Override
    public void getUnsafeRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer) {
        consumer.accept(plane0);
        consumer.accept(plane1);
    }
}
