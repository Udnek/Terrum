package me.udnekjupiter.graphic.object.renderable.shape;

import me.udnekjupiter.graphic.object.renderable.RenderableObject3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.util.function.Consumer;

public class PolygonObject extends RenderableObject3d {

    private RenderableTriangle plane;


    public PolygonObject(Vector3d position, RenderableTriangle plane) {
        super(position);
        this.plane = plane;
    }

    public void setPlane(RenderableTriangle plane) {
        this.plane = plane;
    }

    public RenderableTriangle getPlane(){
        return plane.copy();
    }

    public Vector3d getNormal(){
        return plane.getNormal();
    }

    public double getArea(){
        return plane.getArea();
    }

    @Override
    public RenderableTriangle @NotNull [] getRenderTriangles() {
        return new RenderableTriangle[]{getPlane()};
    }
    @Override
    public RenderableTriangle @NotNull [] getUnsafeRenderTriangles() {
        return new RenderableTriangle[]{plane};
    }

    @Override
    public void getUnsafeRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer) {
        consumer.accept(plane.copy());
    }
}
