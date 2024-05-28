package me.udnek.tests;

import com.aparapi.Kernel;
import com.aparapi.Range;
import me.udnek.utils.Triangle;
import me.udnek.utils.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class Tests {
    public static void run() {

        long startTime = System.nanoTime();

        int n = (int) Math.pow(2, 24);

        calc(n);

        System.out.println(((System.nanoTime() - startTime) / Math.pow(10, 9)));

        startTime = System.nanoTime();
        kernelCalc(n);

        System.out.println(((System.nanoTime() - startTime) / Math.pow(10, 9)));
    }

    public static void calc(int n){

        double[] result = new double[n];

        for (int i = 0; i < n; i++) {
            result[i] = functionToCalc();
        }
    }

    public static void kernelCalc(int n){

        double[] result = new double[n];
        Kernel kernel = new Kernel(){
            @Override
            public void run() {
                int gid = getGlobalId();
                result[gid] = functionToCalc();
            }

        };
        kernel.execute(Range.create(n));
        kernel.dispose();
    }

    public static double functionToCalc(){
        return VectorUtils.triangleRayIntersection(
                new Vector3d(0, 0, 1),
                new Triangle(
                        new Vector3d(-10, -10, 1),
                        new Vector3d(10, 10, (Math.random()+1)*10.0),
                        new Vector3d(10, -10, 1)
                )
        ).length();
    }

}
