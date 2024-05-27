package me.udnek.tests;

import me.udnek.objects.PolygonObject;
import me.udnek.scene.RayTracer;
import me.udnek.utils.Triangle;
import me.udnek.utils.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import java.util.Collections;

public class Tests {
    public static void run(){

        double zPos = 2.0;

        Triangle triangle = new Triangle(
                new Vector3d(-2, -1, zPos),
                new Vector3d(0.5, 1, zPos),
                new Vector3d(1, -1, zPos)
        );

        Vector3d dir = new Vector3d(0, 0, 1);

        Vector3d vector3d = VectorUtils.triangleRayIntersection(dir, triangle);

        System.out.println(vector3d == null ? "null" : vector3d.asString());



/*        PolygonObject polygonObject = new PolygonObject(
                new Vector3d(0, 0, 0),

                new Triangle(
                        new Triangle(
                                new Vector3d(-0.5, 0, 0),
                                new Vector3d(0.4, 1, 0.5),
                                new Vector3d(0.4, 0, 0.5)
                        )
                )
        );

        RayTracer rayTracer = new RayTracer(new Vector3d(0, 0, 0), Collections.singletonList(polygonObject));*/

    }
}
