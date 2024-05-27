package me.udnek.utils;

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
        setPosition(getPosition().add(position));
    }
}
