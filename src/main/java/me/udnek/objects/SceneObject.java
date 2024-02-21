package me.udnek.objects;

import org.realityforge.vecmath.Vector3d;

public abstract class SceneObject {
    private Vector3d position;

    public SceneObject(Vector3d position){
        this.position = position;
    }

    public Vector3d getPosition() {
        return position.dup();
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }
}
