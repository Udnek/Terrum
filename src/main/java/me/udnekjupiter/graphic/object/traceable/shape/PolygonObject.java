package me.udnekjupiter.graphic.object.traceable.shape;

import me.udnekjupiter.graphic.object.traceable.TraceableObject;
import me.udnekjupiter.graphic.triangle.TraceableTriangle;
import org.realityforge.vecmath.Vector3d;

public class PolygonObject extends TraceableObject {

    private TraceableTriangle plane;


    public PolygonObject(Vector3d position, TraceableTriangle plane) {
        super(position);
        this.plane = plane;
    }

    public void setPlane(TraceableTriangle plane) {
        this.plane = plane;
    }

    public TraceableTriangle getPlane(){
        return plane.copy();
    }

    public Vector3d getNormal(){
        return plane.getNormal();
    }

    public double getArea(){
        return plane.getArea();
    }

    @Override
    public TraceableTriangle[] getRenderTriangles() {
        return new TraceableTriangle[]{getPlane()};
    }
}
