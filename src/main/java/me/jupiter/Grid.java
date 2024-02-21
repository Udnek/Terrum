package me.jupiter;

import org.realityforge.vecmath.Vector3d;

public class Grid {

    public final int sizeX;
    public final int sizeY;
    public final int sizeZ;
    private GridSegment[] grid;
    Grid()
    {
        this(100, 100, 100);
    }
    Grid(int sizeX, int sizeY, int sizeZ)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        initializeGrid();
    }
    private void initializeGrid() {
        grid = new GridSegment[sizeX*sizeY*sizeZ];
        for (int i = 0; i < sizeX*sizeY*sizeZ; i++) {
            grid[i] = new GridSegment();
            Vector3d randomVelocity = new Vector3d();
            randomVelocity.x = Math.random();
            randomVelocity.y = Math.random();
            randomVelocity.z = Math.random();
            grid[i].setVelocity(randomVelocity);
        }
    }

    public void recalculateAcceleration() {
        Vector3d gravitationalAcceleration = new Vector3d(0, 0, 9.806);
        for (int i = 0; i < grid.length - (1 + sizeX + sizeX*sizeY); i++) {
            Vector3d velocity = grid[i].getVelocity();
            Vector3d acceleration = new Vector3d();
            Vector3d deltaVelocity = new Vector3d();
            Vector3d neighboursVelocities = neighboursVelocities(i);

            deltaVelocity.x = neighboursVelocities.x - velocity.x;
            deltaVelocity.y = neighboursVelocities.y - velocity.y;
            deltaVelocity.z = neighboursVelocities.z - velocity.z;

            acceleration.x = gravitationalAcceleration.x * (-gravitationalAcceleration.x +
                    velocity.x * deltaVelocity.x +
                    velocity.y * deltaVelocity.y +
                    velocity.z * deltaVelocity.z -
                    velocity.x * deltaVelocity.x -
                    velocity.y * deltaVelocity.x -
                    velocity.z * deltaVelocity.x);
            acceleration.y = gravitationalAcceleration.y * (-gravitationalAcceleration.y +
                    velocity.x * deltaVelocity.x +
                    velocity.y * deltaVelocity.y +
                    velocity.z * deltaVelocity.z -
                    velocity.x * deltaVelocity.y -
                    velocity.y * deltaVelocity.y -
                    velocity.z * deltaVelocity.y);
            acceleration.z = gravitationalAcceleration.z * (-gravitationalAcceleration.z +
                    velocity.x * deltaVelocity.x +
                    velocity.y * deltaVelocity.y +
                    velocity.z * deltaVelocity.z -
                    velocity.x * deltaVelocity.z -
                    velocity.y * deltaVelocity.z -
                    velocity.z * deltaVelocity.z);
            grid[i].setAcceleration(acceleration);
        }
    }
    public void recalculateVelocity(){
        for (GridSegment gridSegment : grid) {
            Vector3d acceleration = gridSegment.getAcceleration();
            Vector3d velocity = gridSegment.getVelocity();
            velocity.x += acceleration.x;
            velocity.y += acceleration.y;
            velocity.z += acceleration.z;
            gridSegment.setVelocity(velocity);
        }
    }

    public Vector3d neighboursVelocities(int targetPosition){
        Vector3d result = new Vector3d();
        result.x = grid[targetPosition + 1].getVelocity().x;
        result.y = grid[targetPosition + sizeX].getVelocity().y;
        result.z = grid[targetPosition + (sizeX*sizeY)].getVelocity().z;

        return result;
    }

    public double getAvgVelocity(){
        double velocities = 0;
        for (GridSegment gridSegment : grid) {
            velocities += gridSegment.getVelocity().length();
        }
        return velocities/grid.length;
    }

    public double getAvgAcceleration(){
        double accelerations = 0;
        for (GridSegment gridSegment : grid) {
            accelerations += gridSegment.getVelocity().length();
        }
        return accelerations/grid.length;
    }

    public int getLength(){
        return grid.length;
    }
}
