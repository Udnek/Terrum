package me.udnekjupiter.physic.object.vertex;

import org.realityforge.vecmath.Vector3d;

public class NetStaticVertex extends NetVertex {

    public NetStaticVertex(Vector3d position) {
        super(position);
        this.frozen = true;
    }

    @Override
    protected Vector3d RKMethodCalculateAcceleration(Vector3d position, Vector3d velocity) {
        return null;
    }

    @Override
    protected Vector3d getAppliedForce(Vector3d position) {
        return null;
    }

    @Override
    protected Vector3d getCollisionForce() {
        return null;
    }

}
