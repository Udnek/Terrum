package me.udnekjupiter.physic.object.vertex;

import org.realityforge.vecmath.Vector3d;

import java.util.List;

public class NetStaticVertex extends NetVertex {

    public NetStaticVertex(Vector3d position) {
        super(position);
        freeze();
    }

    @Override
    public void addNeighbors(List<NetVertex> toAddNeighbors) {
        for (NetVertex neighbor : toAddNeighbors) {
            if (neighbors.contains(neighbor)) continue;
            if (neighbor instanceof NetStaticVertex) continue;
            neighbors.add(neighbor);
            neighbor.addOneWayNeighbour(this);
        }
    }

    @Override
    protected Vector3d RKMethodCalculateAcceleration(Vector3d position, Vector3d velocity) {
        return new Vector3d();
    }

    @Override
    protected Vector3d getAppliedForce(Vector3d position) {
        return null;
    }

    @Override
    public void unfreeze(){}

}
