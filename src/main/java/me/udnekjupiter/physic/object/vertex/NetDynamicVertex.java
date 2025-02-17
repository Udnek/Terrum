
package me.udnekjupiter.physic.object.vertex;

import me.udnekjupiter.physic.engine.PhysicEngine3d;
import me.udnekjupiter.util.Utils;
import me.udnekjupiter.util.Vector3d;

public class NetDynamicVertex extends NetVertex {
    public NetDynamicVertex() {}

    @Override
    public void calculateForces() {
        container.appliedForce.y += PhysicEngine3d.GRAVITATIONAL_ACCELERATION * container.mass;
        container.appliedForce.add(getCollisionForce());
        container.appliedForce.add(Utils.getSphereDragForce(getCollider().radius, container.getVelocity()));
        if (container.appliedForce.containsNaN()){
            container.appliedForce = new Vector3d();
            freeze();
        }
    }
}
