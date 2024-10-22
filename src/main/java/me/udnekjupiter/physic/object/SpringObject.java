package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.engine.PhysicEngine3d;
import me.udnekjupiter.util.VectorUtils;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class SpringObject extends ImplementedPhysicObject3d {
    private PhysicObject3d endpoint1;
    private PhysicObject3d endpoint2;
    private final double relaxedLength;
    private final double stiffness;

    SpringObject(PhysicObject3d endpoint1, PhysicObject3d endpoint2, double relaxedLength, double stiffness)
    {
        this.endpoint1 = endpoint1;
        this.endpoint2 = endpoint2;
        this.relaxedLength = relaxedLength;
        this.stiffness = stiffness;
    }

    @Override
    public void calculateForces(@NotNull Vector3d pos) {
        Vector3d normalizedDirection = VectorUtils.getNormalizedDirection(endpoint1.getPosition(), endpoint2.getPosition());
        double elasticForce = stiffness * VectorUtils.distance(endpoint1.getPosition(), endpoint2.getPosition()) - relaxedLength;
        endpoint1.getContainer().appliedForce.add(normalizedDirection.mul(elasticForce));
        endpoint2.getContainer().appliedForce.add((normalizedDirection.mul(elasticForce)).mul(-1));
    }

}
