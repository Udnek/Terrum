package me.udnekjupiter.physic.collider;

import me.udnekjupiter.util.Positioned;
import org.realityforge.vecmath.Vector3d;

public interface Collidable extends Positioned {
    Collider getCollider();

}
