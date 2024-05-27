package me.jupiter.object;

import org.realityforge.vecmath.Vector3d;

public class NetVertex {
    private Vector3d position;

    public NetVertex()
    {
        this.position = new Vector3d(0, 0, 0);
    }

    public NetVertex(Vector3d position)
    {
        this.position = position;
    }

    public Vector3d getPosition() {return position;}
    public void setPosition(Vector3d position) {this.position = position;}
}
