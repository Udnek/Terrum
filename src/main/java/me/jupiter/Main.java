package me.jupiter;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid();
        int iterationsCount = 52;
        int segmentUnderInspectionIndex = grid.sizeX/2 + grid.sizeX*grid.sizeY/2 + grid.sizeX*grid.sizeY*grid.sizeZ/2;

        double[] averageVelocities = new double[iterationsCount];
        double[] averageAccelerations = new double[iterationsCount];

        double[] segmentXSpeed = new double[iterationsCount];
        double[] segmentYSpeed = new double[iterationsCount];
        double[] segmentZSpeed = new double[iterationsCount];

        double[] segmentXAcceleration = new double[iterationsCount];
        double[] segmentYAcceleration = new double[iterationsCount];
        double[] segmentZAcceleration = new double[iterationsCount];

        for (int i= 0; i < iterationsCount; i++) {
            GridSegment segmentUnderInspection = grid.getSegment(segmentUnderInspectionIndex);
            segmentXSpeed[i] = segmentUnderInspection.getVelocity().x;
            segmentYSpeed[i] = segmentUnderInspection.getVelocity().y;
            segmentZSpeed[i] = segmentUnderInspection.getVelocity().z;

            segmentXAcceleration[i] = segmentUnderInspection.getAcceleration().x;
            segmentYAcceleration[i] = segmentUnderInspection.getAcceleration().y;
            segmentZAcceleration[i] = segmentUnderInspection.getAcceleration().z;

            averageVelocities[i] = grid.getAvgVelocityXY();
            grid.recalculateAcceleration();
            averageAccelerations[i] = grid.getAvgAccelerationXY();
            grid.recalculateVelocity(0.001);
        }
        System.out.println(Arrays.toString(averageVelocities));
        System.out.println(Arrays.toString(averageAccelerations));
        System.out.println(Arrays.toString(segmentXSpeed));
        System.out.println(Arrays.toString(segmentYSpeed));
        System.out.println(Arrays.toString(segmentZSpeed));
        System.out.println(Arrays.toString(segmentXAcceleration));
        System.out.println(Arrays.toString(segmentYAcceleration));
        System.out.println(Arrays.toString(segmentZAcceleration));
    }
}