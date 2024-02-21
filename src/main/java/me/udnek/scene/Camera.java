package me.udnek.scene;

import org.realityforge.vecmath.Vector3d;

public class Camera {

    private Vector3d position;
    private Vector3d direction;

    public Camera(){
        position = new Vector3d(0, 0, 0);
        direction = new Vector3d(0, 0, 1);
    }


    public Vector3d getPosition() {
        return new Vector3d(position);
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }

    public Vector3d getDirection() {
        return new Vector3d(direction);
    }

    public void setDirection(Vector3d direction) {
        this.direction = direction;
    }
}
