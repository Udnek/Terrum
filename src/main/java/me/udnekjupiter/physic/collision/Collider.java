package me.udnekjupiter.physic.collision;

import me.udnekjupiter.physic.object.RKMObject;

public abstract class Collider {
    protected Collidable parent;
    public abstract boolean isCollidingWith(Collider collider);
}