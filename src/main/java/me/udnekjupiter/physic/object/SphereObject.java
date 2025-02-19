package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.collision.Collidable;
import me.udnekjupiter.physic.collision.SphereCollider;
import me.udnekjupiter.physic.engine.ConstantValues;
import me.udnekjupiter.physic.engine.PhysicEngine3d;
import me.udnekjupiter.util.Utils;
import me.udnekjupiter.util.Vector3d;
import org.jetbrains.annotations.NotNull;

public class SphereObject extends ImplementedCollidablePhysicObject3d implements CollisionInitiator {
    public SphereObject(double colliderRadius, double stiffness) {
        collider = new SphereCollider(colliderRadius, stiffness, this);
    }

    public SphereObject(double colliderRadius){
        this(colliderRadius, 10_000);
    }

    @Override
    public void calculateForces() {
        container.appliedForce.y += ConstantValues.GRAVITATIONAL_ACCELERATION * container.mass;
        container.appliedForce.add(getCollisionForce());
        container.appliedForce.add(Utils.getSphereDragForce(getCollider().radius, container.getVelocity()));
        if (container.appliedForce.containsNaN()){
            container.appliedForce = new Vector3d();
            freeze();
        }
    }

    @Override
    public boolean isCollisionIgnoredWith(@NotNull Collidable object) {return false;}

    @Override
    public @NotNull SphereCollider getCollider() {
        return (SphereCollider) super.getCollider();
    }
}
