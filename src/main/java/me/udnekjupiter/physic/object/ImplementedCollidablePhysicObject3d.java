package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.collision.Collidable;
import me.udnekjupiter.physic.collision.Collider;
import me.udnekjupiter.physic.collision.CollisionCalculator;
import me.udnekjupiter.physic.collision.SphereCollider;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.Vector3d;

import java.util.List;

public abstract class ImplementedCollidablePhysicObject3d extends ImplementedPhysicObject3d implements CollidablePhysicObject3d{
    protected Collider collider;

    @Override
    public @NotNull Collider getCollider() {
        return collider;
    }

    @Override
    public @NotNull List<Collidable> getCollidingObjects() {
        return List.of();
    }

    public void clearCollidingObjects(){
        this.collider.currentCollisions.clear();
    }

    @Override
    public @NotNull Vector3d getCollisionForce() {
        return CollisionCalculator.getHookeCollisionForce(getCollider());
    }
}
