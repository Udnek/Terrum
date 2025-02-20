package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.container.PhysicVariableContainer;
import me.udnekjupiter.util.Utils;
import me.udnekjupiter.util.vector.VectorUtils;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.vector.Vector3d;

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
    public void calculateForces() {
        PhysicVariableContainer containerA = endpointA.getContainer();
        PhysicVariableContainer containerB = endpointB.getContainer();
        Vector3d normalizedDirection = VectorUtils.getNormalizedDirection(containerA.getPosition().dup(), containerB.getPosition().dup());
        double elasticForce = Utils.getHookeForce(stiffness, relaxedLength, VectorUtils.distance(containerA.getPosition().dup(), containerB.getPosition().dup()));
        containerA.appliedForce.add(normalizedDirection.dup().mul(elasticForce));
        containerB.appliedForce.add(normalizedDirection.dup().mul(-elasticForce));
    }

    public @NotNull PhysicObject3d getEndpointA() {return endpointA;}

    public @NotNull PhysicObject3d getEndpointB() {return endpointB;}
}
