package me.jupiter.object;

import me.jupiter.object.RigidBody;
import org.realityforge.vecmath.Vector3d;

public class Cube extends RigidBody {
    private double width;
    private double height;
    private double length;

    public Cube(Vector3d position, double width, double height, double length)
    {
        super(position);
        this.width = width;
        this.height = height;
        this.length = length;
    }


}
