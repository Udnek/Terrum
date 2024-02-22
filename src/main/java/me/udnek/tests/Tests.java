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
                                new Vector3d(-0.5, 0, 0),
                                new Vector3d(0.4, 1, 0.5),
                                new Vector3d(0.4, 0, 0.5)
                        )
                )
        );

        RayTracer rayTracer = new RayTracer(new Vector3d(0, 0, 0), Collections.singletonList(polygonObject));

    }
}
