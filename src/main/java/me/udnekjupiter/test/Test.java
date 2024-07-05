package me.udnekjupiter.test;

import me.udnekjupiter.util.Utils;

public class Test {
    public static void run() {

/*
        Vector3d left = new Vector3d(-2, 0, 1);
        Vector3d right = new Vector3d(2, 0, 1);
        Vector3d down = new Vector3d(0, -1, 1);
        Vector3d up = new Vector3d(0, 1, 1);

        Vector3d vector = new Vector3d(0, 0, 1);
*/

        float yaw = 120;

        System.out.println(yaw);
        System.out.println(Utils.normalizeYaw(-yaw));



    }

}
