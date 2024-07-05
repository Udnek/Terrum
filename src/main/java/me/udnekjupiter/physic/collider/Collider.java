package me.udnekjupiter.physic.collider;

import me.udnekjupiter.physic.object.RKMObject;

import java.util.List;

public abstract class Collider {
    protected RKMObject parent;
    public abstract boolean isCollidingWith(Collider collider);
}