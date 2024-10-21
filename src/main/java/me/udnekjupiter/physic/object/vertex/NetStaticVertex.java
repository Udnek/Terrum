package me.udnekjupiter.physic.object.vertex;

import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.util.List;

public class NetStaticVertex extends NetVertex {

    protected Vector3d appliedForce = new Vector3d();

    public NetStaticVertex() {
        freeze();
    }

    @Override
    public void addNeighbors(@NotNull List<NetVertex> toAddNeighbors) {
        for (NetVertex neighbor : toAddNeighbors) {
            if (neighbors.contains(neighbor)) continue;
            if (neighbor instanceof NetStaticVertex) continue;
            neighbors.add(neighbor);
            neighbor.addOneWayNeighbour(this);
        }
    }
    
    @Override
    public @NotNull Vector3d getAppliedForce(@NotNull Vector3d position) {
        appliedForce.mul(0);
        return appliedForce;
    }

    @Override
    public void unfreeze(){}

}
