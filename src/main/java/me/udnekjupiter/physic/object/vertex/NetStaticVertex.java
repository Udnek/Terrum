package me.udnekjupiter.physic.object.vertex;

import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class NetStaticVertex extends NetVertex {

    public NetStaticVertex() {
        freeze();
    }

    @Override
    public void calculateForces(@NotNull Vector3d position) {}

    @Override
    public void unfreeze(){}

}
