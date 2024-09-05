package me.udnekjupiter.physic.collision;

import me.udnekjupiter.physic.object.RKMObject;
import me.udnekjupiter.util.Positioned;
import org.realityforge.vecmath.Vector3d;

public interface Collidable extends Positioned {
    Collider getCollider();
    boolean isCollisionIgnored(RKMObject object);
}
