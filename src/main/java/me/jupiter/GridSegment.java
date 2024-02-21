package me.jupiter;

import org.realityforge.vecmath.Vector3d;

public class GridSegment {
    private Vector3d velocity = new Vector3d();
    private Vector3d acceleration = new Vector3d();

    public Vector3d getVelocity() {return velocity.dup();}
    public void setVelocity(Vector3d velocity) {this.velocity = velocity;}

    public Vector3d getAcceleration() {return acceleration.dup();}
    public void setAcceleration(Vector3d acceleration) {this.acceleration = acceleration;}

}
