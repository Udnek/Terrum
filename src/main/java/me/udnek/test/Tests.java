package me.udnek.test;

import me.udnek.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class Tests {
    public static void run() {

        Vector3d left = new Vector3d(-2, 0, 1);
        Vector3d right = new Vector3d(2, 0, 1);
        Vector3d down = new Vector3d(0, -1, 1);
        Vector3d up = new Vector3d(0, 1, 1);

        Vector3d vector = new Vector3d(0, 0, 1);


        in(vector, left, right, down, up);
    }


    public static boolean in(Vector3d vector, Vector3d left, Vector3d right, Vector3d down, Vector3d up){
        System.out.println(Math.toDegrees(VectorUtils.angleY(vector, left)));
        System.out.println(Math.toDegrees(VectorUtils.angleY(vector, right)));
        System.out.println(Math.toDegrees(VectorUtils.angleX(vector, down)));
        System.out.println(Math.toDegrees(VectorUtils.angleX(vector, up)));

        return true;
        //if (VectorUtils.angleY(vector, left),)
    }
}
