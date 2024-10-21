package me.udnekjupiter.physic.collision;

import me.udnekjupiter.util.Positioned;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.util.List;

public interface Collidable extends Positioned {
    @NotNull Collider getCollider();
    boolean isCollisionIgnoredWith(@NotNull Collidable object);
    @NotNull List<Collidable> getCollidingObjects();
    @NotNull Vector3d getCollisionForce();
}
