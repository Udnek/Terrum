package me.udnekjupiter.physic.collision;

public abstract class Collider {
    protected Collidable parent;
    public abstract boolean isCollidingWith(Collider collider);
}