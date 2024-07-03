package me.udnekjupiter.physic.collider;

import org.realityforge.vecmath.Vector3d;

public interface Collidable {
    public Collider getCollider();

    public Vector3d getPosition();
}