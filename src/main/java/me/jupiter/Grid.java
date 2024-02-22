package me.jupiter;

import org.realityforge.vecmath.Vector2d;
import org.realityforge.vecmath.Vector3d;

public class Grid {

    public final int sizeX;
    public final int sizeY;
    public final int sizeZ;
    private GridSegment[] grid;
    public Grid()
    {
        this(100, 100, 100);
    }
    public Grid(int sizeX, int sizeY, int sizeZ)
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
            Vector3d initialVelocity = new Vector3d();
            GridPosition gridPosition = toGridPosition(i);
            initialVelocity.x = gridPosition.y - ((double) grid.length / 2);
            initialVelocity.y = gridPosition.x - ((double) grid.length / 2);
            initialVelocity.z = 0;
            grid[i].setVelocity(initialVelocity);
        }
    }

    public void recalculateAcceleration() {
        Vector3d gravitationalAcceleration = new Vector3d(0, 0, -9.806);
        for (int i = 0; i < grid.length - (1 + sizeX + sizeX*sizeY); i++) {
            Vector3d velocity = grid[i].getVelocity();
            Vector3d acceleration = new Vector3d();
            Vector3d deltaVelocity = new Vector3d();
            Vector3d neighboursVelocities = neighboursVelocities(i);

            deltaVelocity.x = neighboursVelocities.x - velocity.x;
            deltaVelocity.y = neighboursVelocities.y - velocity.y;
            deltaVelocity.z = neighboursVelocities.z - velocity.z;

            acceleration.x = gravitationalAcceleration.x - (-gravitationalAcceleration.x +
                    velocity.x * deltaVelocity.x +
                    velocity.y * deltaVelocity.y +
                    velocity.z * deltaVelocity.z -
                    velocity.x * deltaVelocity.x -
                    velocity.y * deltaVelocity.x -
                    velocity.z * deltaVelocity.x);
            acceleration.y = gravitationalAcceleration.y - (-gravitationalAcceleration.y +
                    velocity.x * deltaVelocity.x +
                    velocity.y * deltaVelocity.y +
                    velocity.z * deltaVelocity.z -
                    velocity.x * deltaVelocity.y -
                    velocity.y * deltaVelocity.y -
                    velocity.z * deltaVelocity.y);
            acceleration.z = gravitationalAcceleration.z - (-gravitationalAcceleration.z +
                    velocity.x * deltaVelocity.x +
                    velocity.y * deltaVelocity.y +
                    velocity.z * deltaVelocity.z -
                    velocity.x * deltaVelocity.z -
                    velocity.y * deltaVelocity.z -
                    velocity.z * deltaVelocity.z);
            grid[i].setAcceleration(acceleration);
        }
    }
    public void recalculateVelocity(double deltaTime){
        for (GridSegment gridSegment : grid) {
            Vector3d acceleration = gridSegment.getAcceleration();
            Vector3d velocity = gridSegment.getVelocity();
            velocity.x += acceleration.x * deltaTime;
            velocity.y += acceleration.y * deltaTime;
            velocity.z += acceleration.z * deltaTime;
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
            accelerations += gridSegment.getAcceleration().length();
        }
        return accelerations/grid.length;
    }

    public double getAvgAccelerationXY(){
        double accelerations = 0;
        for (GridSegment gridSegment : grid){
            Vector2d accelerationXY = new Vector2d();
            accelerationXY.x = gridSegment.getAcceleration().x;
            accelerationXY.y = gridSegment.getAcceleration().y;
            accelerations += accelerationXY.length();
        }
        return accelerations/grid.length;
    }

    public double getAvgVelocityXY(){
        double velocities = 0;
        for (GridSegment gridSegment : grid){
            Vector2d velocityXY = new Vector2d();
            velocityXY.x = gridSegment.getVelocity().x;
            velocityXY.y = gridSegment.getVelocity().y;
            velocities += velocityXY.length();
        }
        return velocities/grid.length;
    }

    public int fromGridPosition(int x, int y, int z){
        return x + sizeX*y + sizeX*sizeY*z;
    }

    public int fromGridPosition(GridPosition gridPosition){
        return gridPosition.x + sizeX*gridPosition.y + sizeX*sizeY*gridPosition.z;
    }

    public GridPosition toGridPosition(int xyz){
        GridPosition result = new GridPosition();
        result.z = xyz / (sizeX*sizeY);
        result.y = (xyz % (sizeX*sizeY)) / sizeX;
        result.x = (xyz % (sizeX*sizeY)) % sizeX;

        return result;
    }

    public GridSegment getSegment(int position) {return grid[position].copy();}

    public static class GridPosition {
        public int x;
        public int y;
        public int z;
        public GridPosition(int x, int y, int z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        public GridPosition()
        {
            this(0, 0, 0);
        }
    }
}
