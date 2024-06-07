package me.jupiter.object;

import org.realityforge.vecmath.Vector3d;

public abstract class SimulationObject {
    protected Vector3d position;

    public SimulationObject(Vector3d position)
    {
        this.position = position;
    }

    public Vector3d getPosition(){
        return this.position.dup();
    }
    public void setPosition(Vector3d position){
        this.position = position;
    }
}
