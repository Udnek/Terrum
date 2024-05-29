package me.jupiter.object;

import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class NetVertex {
    public Vector3d position;
    protected final float springRelaxedLength;
    protected final float springStiffness;

    protected List<NetVertex> neighbours = new ArrayList<>();

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

    public void addNeighbours(NetVertex ...neighbours){
        this.neighbours.addAll(Arrays.asList(neighbours));
    }

    public int getNeighboursAmount(){
        return neighbours.size();
    }
    public List<NetVertex> getNeighbours(){
        return this.neighbours;
    }
}
