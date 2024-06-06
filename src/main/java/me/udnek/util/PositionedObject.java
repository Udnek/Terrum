package me.udnek.util;

import org.realityforge.vecmath.Vector3d;

public class PositionedObject {
    protected Vector3d position;

    public PositionedObject(Vector3d position){
        this.position = position;
    }

    public Vector3d getPosition() {
        return position.dup();
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }
    public void move(Vector3d position){
        setPosition(this.position.add(position));
    }
    public void move(double x, double y, double z){
        setPosition(this.position.add(x, y, z));
    }
}
