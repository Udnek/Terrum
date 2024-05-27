package me.jupiter;

import org.realityforge.vecmath.Vector3d;

public class Main {
    public static void main(String[] args) {
        Net testNet = new Net();
        System.out.println(testNet.calculateDistance(new Vector3d(0, 0, 0), new Vector3d(0, 0, 1)));
    }
}
