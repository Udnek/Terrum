package me.udnek.objects;

import me.udnek.utils.Triangle;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public List<Triangle> getRenderTriangles() {
        return Collections.singletonList(getPlane());
    }
}
