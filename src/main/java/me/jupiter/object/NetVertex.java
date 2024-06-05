package me.jupiter.object;

import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public abstract class NetVertex {
    public Vector3d position;

    protected List<NetVertex> neighbours = new ArrayList<>();
    public NetVertex(){}

    public NetVertex(Vector3d position, float springRelaxedLength, float springStiffness) {
        this.position = position;
    }
    public NetVertex(Vector3d position){
        this(position, 1, 1);
    }

    public Vector3d getPosition() {return position.dup();}
    public void setPosition(Vector3d position) {this.position = position;}

    public void addNeighbours(List<NetVertex> toAddNeighbours){
        for (NetVertex neighbour : toAddNeighbours) {
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
