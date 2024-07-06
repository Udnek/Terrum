package me.udnekjupiter.physic.collider;

import me.udnekjupiter.physic.object.RKMObject;
import me.udnekjupiter.util.Positioned;
import org.realityforge.vecmath.Vector3d;

import java.util.List;

public interface Collidable extends Positioned {
    Collider getCollider();
    boolean isCollisionIgnored(RKMObject object);
}
