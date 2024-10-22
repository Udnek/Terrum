package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.engine.PhysicEngine3d;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class SpringObject extends ImplementedPhysicObject3d {
    private final double relaxedLength;
    private final double stiffness;

    SpringObject(double relaxedLength, double stiffness)
    {
        this.relaxedLength = relaxedLength;
        this.stiffness = stiffness;
    }

    @Override
    public @NotNull Vector3d getAppliedForce(@NotNull Vector3d pos) {
        Vector3d appliedForce = new Vector3d();
        appliedForce.y += PhysicEngine3d.GRAVITATIONAL_ACCELERATION * container.mass;
        return appliedForce;
    }

}
