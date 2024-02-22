package me.jupiter;

import org.realityforge.vecmath.Vector3d;

public class GridSegment {
    GridSegment()
    {
       this.velocity = new Vector3d();
       this.acceleration = new Vector3d();
    }
    GridSegment(GridSegment other)
    {
        this.velocity = other.getVelocity();
        this.acceleration = other.getAcceleration();
    }
    private Vector3d velocity;
    private Vector3d acceleration;

    public Vector3d getVelocity() {return velocity.dup();}
    public void setVelocity(Vector3d velocity) {this.velocity = velocity;}

    public Vector3d getAcceleration() {return acceleration.dup();}
    public void setAcceleration(Vector3d acceleration) {this.acceleration = acceleration;}

    public GridSegment copy(){
        return new GridSegment(this);
    }

}
