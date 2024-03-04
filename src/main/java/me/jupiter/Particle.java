package me.jupiter;

import org.realityforge.vecmath.Vector3d;

public class Particle {
    private Vector3d position;
    Particle(double posX, double posY, double posZ)
    {
        this.position = new Vector3d(posX, posY, posZ);
    }
    public Vector3d getPosition() {return position.dup();}

    public void setPosition(Vector3d position) {this.position = position;}

}
