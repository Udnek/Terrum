package me.udnekjupiter.physic.collision;

import me.udnekjupiter.util.vector.Vector3d;

public class SphereBounder {
    private double radius;
    private Vector3d position;

    public SphereBounder(double radius, Vector3d position)
    {
        this.radius = radius;
        this.position = position;
    }

    public Vector3d getCenterPosition(){
        return position;
    }

    public double getRadius(){
        return radius;
    }
}
