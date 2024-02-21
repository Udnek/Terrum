package me.jupiter;

import org.realityforge.vecmath.Vector3d;

public class Particle {
    private Vector3d position;

    public Vector3d getPosition() {return position.dup();}

    public void setPosition(Vector3d position) {this.position = position;}
}
