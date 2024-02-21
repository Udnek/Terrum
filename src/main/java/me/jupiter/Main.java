package me.jupiter;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid();
        double[] averageVelocities = new double[100];
        double[] averageAccelerations = new double[100];
        for (int i= 0; i < 100; i++) {
            averageVelocities[i] = grid.getAvgVelocity();
            grid.recalculateAcceleration();
            averageAccelerations[i] = grid.getAvgAcceleration();
            grid.recalculateVelocity();
        }
        System.out.println(Arrays.toString(averageVelocities));
        System.out.println(Arrays.toString(averageAccelerations));
    }
}