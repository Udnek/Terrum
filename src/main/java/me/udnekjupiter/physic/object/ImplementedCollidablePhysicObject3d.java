package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.collision.Collidable;
import me.udnekjupiter.physic.collision.Collider;
import me.udnekjupiter.physic.collision.CollisionCalculator;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.util.List;

public abstract class ImplementedCollidablePhysicObject3d extends ImplementedPhysicObject3d implements CollidablePhysicObject3d{
    protected Collider collider;

    @Override
    public @NotNull Collider getCollider() {
        return collider;
    }

    @Override
    public @NotNull List<Collidable> getCollidingObjects() {
        // TODO IMPLEMENT
        return List.of();
    }

    @Override
    public @NotNull Vector3d getCollisionForce() {
        return CollisionCalculator.getHookeCollisionForce(this);
    }
}
