package me.jupiter.object;

import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public abstract class NetVertex {
    public Vector3d position;

    // TODO: 5/29/2024 MOVE TO DYNAMIC
    protected final float springRelaxedLength;
    protected final float springStiffness;

    protected List<NetVertex> neighbours = new ArrayList<>();

    // TODO: 5/29/2024 MOVE TO DYNAMIC
    public NetVertex(){
        springStiffness =1;
        springRelaxedLength =1;
    }

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

    public void addNeighbours(List<NetVertex> toAddNeighbours){
        for (NetVertex neighbour : toAddNeighbours) {
            if (neighbour instanceof NetVoidVertex) continue;
            if (neighbours.contains(neighbour)) continue;
            neighbours.add(neighbour);
            neighbour.addOneWayNeighbour(this);
        }
    }

    protected void addOneWayNeighbour(NetVertex neighbour){
        if (neighbours.contains(neighbour)) return;
        neighbours.add(neighbour);
    }

    public int getNeighboursAmount(){
        return neighbours.size();
    }
    public List<NetVertex> getNeighbours(){
        return this.neighbours;
    }
}
