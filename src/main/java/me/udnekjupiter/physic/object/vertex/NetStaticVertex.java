package me.udnekjupiter.physic.object.vertex;

import org.realityforge.vecmath.Vector3d;

public class NetStaticVertex extends NetVertex {

    public NetStaticVertex(Vector3d position) {
        super(position);
        this.frozen = true;
    }

}
