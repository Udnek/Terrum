package me.udnekjupiter.graphic.object.renderable.shape;

import me.udnekjupiter.graphic.object.renderable.RenderableObject;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.realityforge.vecmath.Vector3d;

public class PolygonObject extends RenderableObject {

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
    public RenderableTriangle[] getRenderTriangles() {
        return new RenderableTriangle[]{getPlane()};
    }

    @Override
    public RenderableTriangle[] getUnsafeRenderTriangles() {
        return new RenderableTriangle[]{plane};
    }
}
