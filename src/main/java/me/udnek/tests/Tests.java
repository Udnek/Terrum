package me.udnek.tests;

import me.udnek.objects.PolygonObject;
import me.udnek.scene.RayTracer;
import me.udnek.utils.Triangle;
import org.realityforge.vecmath.Vector3d;

import java.util.Collections;

public class Tests {
    public static void run(){
        PolygonObject polygonObject = new PolygonObject(
                new Vector3d(0, 0, 0),

                new Triangle(
                        new Triangle(
                                new Vector3d(0, 0, 10),
                                new Vector3d(0, 1, 8),
                                new Vector3d(1, 0, 10)
                        )
                )
        );

        RayTracer rayTracer = new RayTracer(new Vector3d(0, 0, 0), Collections.singletonList(polygonObject));

        int i = rayTracer.rayCast(new Vector3d(0, 0, 1));
        //System.out.println(i);
    }
}
