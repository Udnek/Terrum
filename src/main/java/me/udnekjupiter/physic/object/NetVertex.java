package me.udnekjupiter.physic.object;

import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public abstract class NetVertex extends RKMObject{
    public Vector3d position;


    protected List<NetVertex> neighbors = new ArrayList<>();

    public NetVertex(Vector3d position) {
        super(position);
        setCurrentRKMPhaseVector(new Vector3d[]{position, new Vector3d(0, 0, 0)});
    }

    public Vector3d getPosition() {return position.dup();}
    public void setPosition(Vector3d position) {this.position = position;}

    public void addNeighbors(List<NetVertex> toAddNeighbors){
        for (NetVertex neighbor : toAddNeighbors) {
            if (neighbors.contains(neighbor)) continue;
            neighbors.add(neighbor);
            neighbor.addOneWayNeighbour(this);
        }
    }

    protected void addOneWayNeighbour(NetVertex neighbor){
        if (neighbors.contains(neighbor)) return;
        neighbors.add(neighbor);
    }

    public int getNeighborsAmount(){
        return neighbors.size();
    }
    public List<NetVertex> getNeighbors(){
        return this.neighbors;
    }
}
