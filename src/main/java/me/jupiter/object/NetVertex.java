package me.jupiter.object;

import org.realityforge.vecmath.Vector3d;

public abstract class NetVertex {
    public Vector3d position;
    protected final float springRelaxedLength;
    protected final float springStiffness;

    protected NetVertex[] neighbours;

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
        this.neighbours = neighbours;
    }

    public int getNeighboursAmount(){
        return neighbours.length;
    }
    public NetVertex[] getNeighbours(){
        return this.neighbours;
    }
}
