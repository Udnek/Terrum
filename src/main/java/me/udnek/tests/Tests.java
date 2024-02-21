package me.udnek.tests;

import me.udnek.objects.PointObject;
import me.udnek.objects.PolygonObject;
import me.udnek.scene.Scene;
import org.realityforge.vecmath.Vector3d;

public class Tests {
    public static void run(){
        PolygonObject polygonObject = new PolygonObject(
                new Vector3d(0, 0, 0),

                new Vector3d(0, 0, 10),
                new Vector3d(1, 0, 10),
                new Vector3d(0.5, 1, 10)
        );

        System.out.println(polygonObject.getEdge0().asString());
        System.out.println(polygonObject.getEdge1().asString());
        System.out.println(polygonObject.getEdge2().asString());
        System.out.println(polygonObject.getArea());
    }
}
