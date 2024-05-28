package me.jupiter.object;

import org.realityforge.vecmath.Vector3d;

public abstract class NetVertex {
    public Vector3d position;
    protected final float springRelaxedLength;
    protected final float springStiffness;

    protected NetVertex neighbourUp;
    protected NetVertex neighbourRight;
    protected NetVertex neighbourDown;
    protected NetVertex neighbourLeft;

    public NetVertex(Vector3d position, float springRelaxedLength, float springStiffness) {
        this.position = position;
        this.springRelaxedLength = springRelaxedLength;
        this.springStiffness = springStiffness;
    }
    public NetVertex(Vector3d position){
        this(position, 1, 1);
    }

    public Vector3d getPosition() {return position.dup();}
    public void setPosition(Vector3d position) {this.position = position;}

    public void setupNeighbours(NetVertex[] neighbours){
        this.neighbourUp = neighbours[0];
        this.neighbourRight = neighbours[1];
        this.neighbourDown = neighbours[2];
        this.neighbourLeft = neighbours[3];
    }

    public double[][] getNeighboursCoordinates(){
        double[][] result = new double[4][3];
        result[0] = neighbourUp.position.toArray();
        result[1] = neighbourLeft.position.toArray();
        result[2] = neighbourDown.position.toArray();
        result[3] = neighbourLeft.position.toArray();
        return result;
    }
}
