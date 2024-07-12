package me.udnekjupiter.physic.collision;

import me.udnekjupiter.physic.object.RKMObject;
import me.udnekjupiter.util.Positioned;

public interface Collidable extends Positioned {
    Collider getCollider();
    boolean isCollisionIgnored(RKMObject object);
}
