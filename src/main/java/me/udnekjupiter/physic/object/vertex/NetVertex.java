package me.udnekjupiter.physic.object.vertex;

import me.udnekjupiter.physic.collision.Collidable;
import me.udnekjupiter.physic.collision.SphereCollider;
import me.udnekjupiter.physic.object.StandardObject3d;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public abstract class NetVertex extends StandardObject3d {
    public Vector3d position;

    protected List<NetVertex> neighbors = new ArrayList<>();

    public NetVertex(Vector3d position) {
        super(position);
        collider = new SphereCollider(0.2, 100_000, this);
    }

    public Vector3d getPosition() {return position.dup();}
    public void setPosition(@NotNull Vector3d position) {this.position = position;}

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

    @Override
    public boolean isCollisionIgnored(Collidable object){
        return object instanceof NetVertex;
    }
}
