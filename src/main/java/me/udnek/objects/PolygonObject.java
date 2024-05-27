package me.udnek.objects;

import me.udnek.utils.Triangle;
import org.realityforge.vecmath.Vector3d;

public class PolygonObject extends SceneObject{

    private Triangle plane;


    public PolygonObject(Vector3d position, Triangle plane) {
        super(position);
        this.plane = plane;
    }

    public void setPlane(Triangle plane) {
        this.plane = plane;
    }

    public Triangle getPlane(){
        return plane.copy();
    }

    public Vector3d getNormal(){
        return plane.getNormal();
    }

    public double getArea(){
        return plane.getArea();
    }

    @Override
    public Triangle[] getRenderTriangles() {
        return new Triangle[]{getPlane()};
    }
}
