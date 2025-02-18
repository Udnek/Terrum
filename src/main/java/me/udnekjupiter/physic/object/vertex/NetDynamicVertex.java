
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
        } else if (container.appliedForce.length() > PhysicEngine3d.FORCE_HARD_CAP) {
            container.appliedForce.normalize().mul(PhysicEngine3d.FORCE_SOFT_CAP);
            System.out.println("Warning: object reached force limit");
        }
    }
}
