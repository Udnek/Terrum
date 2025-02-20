package me.udnekjupiter.physic.collision;

import me.udnekjupiter.util.utilityinterface.Positioned;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.vector.Vector3d;

import java.util.List;

public interface Collidable extends Positioned {
    @NotNull Collider getCollider();
    boolean isCollisionIgnoredWith(@NotNull Collidable object);
    @NotNull List<Collidable> getCollidingObjects();
    void clearCollidingObjects();
    @NotNull Vector3d getCollisionForce();
}
