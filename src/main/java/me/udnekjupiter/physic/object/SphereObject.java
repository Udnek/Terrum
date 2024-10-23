package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.collision.Collidable;
import me.udnekjupiter.physic.collision.SphereCollider;
import me.udnekjupiter.physic.engine.PhysicEngine3d;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class SphereObject extends ImplementedCollidablePhysicObject3d {
    public SphereObject(double colliderRadius, double stiffness) {
        collider = new SphereCollider(colliderRadius, stiffness, this);
    }

    public SphereObject(double colliderRadius){
        this(colliderRadius, 10_000);
    }

    @Override
    public void calculateForces(@NotNull Vector3d pos) {
        container.appliedForce.y += PhysicEngine3d.GRAVITATIONAL_ACCELERATION * container.mass;
        container.appliedForce.add(getCollisionForce());
    }

    @Override
    public boolean isCollisionIgnoredWith(@NotNull Collidable object) {return false;}

    @Override
    public @NotNull SphereCollider getCollider() {
        return (SphereCollider) super.getCollider();
    }
}
