package me.udnekjupiter.physic.object.vertex;

import me.udnekjupiter.physic.collision.Collidable;
import me.udnekjupiter.physic.collision.SphereCollider;
import me.udnekjupiter.physic.object.ImplementedCollidablePhysicObject3d;
import org.jetbrains.annotations.NotNull;

public abstract class NetVertex extends ImplementedCollidablePhysicObject3d {

    public NetVertex() {
        collider = new SphereCollider(0.05, 10_000, this);
    }

    @Override
    public boolean isCollisionIgnoredWith(@NotNull Collidable object) {
        return object instanceof NetVertex;
    }

    @Override
    public @NotNull SphereCollider getCollider() {
        return (SphereCollider) super.getCollider();
    }
}
