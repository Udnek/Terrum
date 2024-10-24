
package me.udnekjupiter.physic.object.vertex;

import me.udnekjupiter.physic.engine.PhysicEngine3d;

public class NetDynamicVertex extends NetVertex {
    public NetDynamicVertex() {}

    @Override
    public void calculateForces() {
        container.appliedForce.y += PhysicEngine3d.GRAVITATIONAL_ACCELERATION * container.mass;
        container.appliedForce.add(getCollisionForce());
    }
}
