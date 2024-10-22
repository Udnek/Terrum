package me.udnekjupiter.physic.object;

import me.udnekjupiter.util.VectorUtils;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class SpringObject extends ImplementedPhysicObject3d {
    private PhysicObject3d endpointA;
    private PhysicObject3d endpointB;
    private final double relaxedLength;
    private final double stiffness;

    // TODO THING ABOUT NULLABLE ENDPOINTS, MASS, COLLIDER
    public SpringObject(@NotNull PhysicObject3d endpointA, @NotNull PhysicObject3d endpointB, double relaxedLength, double stiffness)
    {
        this.endpointA = endpointA;
        this.endpointB = endpointB;
        this.relaxedLength = relaxedLength;
        this.stiffness = stiffness;
    }

    @Override
    public void calculateForces(@NotNull Vector3d pos) {
        Vector3d normalizedDirection = VectorUtils.getNormalizedDirection(endpointA.getPosition(), endpointB.getPosition());
        double elasticForce = stiffness * VectorUtils.distance(endpointA.getPosition(), endpointB.getPosition()) - relaxedLength;
        endpointA.getContainer().appliedForce.add(normalizedDirection.mul(elasticForce));
        endpointB.getContainer().appliedForce.add((normalizedDirection.mul(elasticForce)).mul(-1));
    }

    public @NotNull PhysicObject3d getEndpointA() {return endpointA;}

    public @NotNull PhysicObject3d getEndpointB() {return endpointB;}
}
