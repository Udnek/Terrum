package me.udnekjupiter.physic.collision;

import me.udnekjupiter.util.Positioned;

import java.util.List;

public interface Collidable extends Positioned {
    Collider getCollider();
    boolean isCollisionIgnored(Collidable object);
    List<Collidable> getCollidingObjects();
}
