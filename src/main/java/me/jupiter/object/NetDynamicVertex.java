package me.jupiter.object;

import org.realityforge.vecmath.Vector3d;

public class NetDynamicVertex extends NetVertex{
    private Vector3d velocity;
    private Vector3d acceleration;
    private Vector3d positionDifferential;

    public NetDynamicVertex(Vector3d position) {
        super(position);
    }

    public Vector3d getVelocity() {return velocity.dup();}
    public void setVelocity(Vector3d velocity) {this.velocity = velocity;}

    public Vector3d getAcceleration() {return acceleration;}
    public void setAcceleration(Vector3d acceleration) {this.acceleration = acceleration;}

    public Vector3d getPositionDifferential() {return positionDifferential.dup();}
    public void setPositionDifferential(Vector3d positionDifferential) {this.positionDifferential = positionDifferential;}

    public void calculatePostitonDifferential() {

    }
}
