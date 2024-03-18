package me.jupiter;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid(100, 100, 100, 0.005);
        int iterationsCount = 101;
//        int segmentUnderInspectionIndex = grid.sizeX/2 + grid.sizeX*grid.sizeY/2 + grid.sizeX*grid.sizeY*grid.sizeZ/2;

//        double[] averageVelocities = new double[iterationsCount];
//        double[] averageAccelerations = new double[iterationsCount];
//
//        double[] segmentXSpeed = new double[iterationsCount];
//        double[] segmentYSpeed = new double[iterationsCount];
//        double[] segmentZSpeed = new double[iterationsCount];
//
//        double[] segmentXAcceleration = new double[iterationsCount];
//        double[] segmentYAcceleration = new double[iterationsCount];
//        double[] segmentZAcceleration = new double[iterationsCount];

        double[] particleXPositions = new double[iterationsCount];
        double[] particleYPositions = new double[iterationsCount];
        double[] particleZPositions = new double[iterationsCount];

        Particle particle = new Particle(60, 60, 50);

        for (int i=0; i < iterationsCount; i++) {
            particleXPositions[i] = particle.getPosition().x;
            particleYPositions[i] = particle.getPosition().y;
            particleZPositions[i] = particle.getPosition().z;

            grid.recalculateAcceleration();
            grid.recalculateVelocity();

            grid.recalculateParticlePosition(particle);
//            GridSegment segmentUnderInspection = grid.getSegment(segmentUnderInspectionIndex);

//            segmentXSpeed[i] = segmentUnderInspection.getVelocity().x;
//            segmentYSpeed[i] = segmentUnderInspection.getVelocity().y;
//            segmentZSpeed[i] = segmentUnderInspection.getVelocity().z;
//
//            segmentXAcceleration[i] = segmentUnderInspection.getAcceleration().x;
//            segmentYAcceleration[i] = segmentUnderInspection.getAcceleration().y;
//            segmentZAcceleration[i] = segmentUnderInspection.getAcceleration().z;
//
//            averageVelocities[i] = grid.getAvgVelocityXY();
//            grid.recalculateAcceleration();
//            averageAccelerations[i] = grid.getAvgAccelerationXY();
//            grid.recalculateVelocity();
        }
//        System.out.println(Arrays.toString(averageVelocities));
//        System.out.println(Arrays.toString(averageAccelerations));
//        System.out.println(Arrays.toString(segmentXSpeed));
//        System.out.println(Arrays.toString(segmentYSpeed));
//        System.out.println(Arrays.toString(segmentZSpeed));
//        System.out.println(Arrays.toString(segmentXAcceleration));
//        System.out.println(Arrays.toString(segmentYAcceleration));
//        System.out.println(Arrays.toString(segmentZAcceleration));
        System.out.println(Arrays.toString(particleXPositions));
        System.out.println(Arrays.toString(particleYPositions));
        System.out.println(Arrays.toString(particleZPositions));
    }
}