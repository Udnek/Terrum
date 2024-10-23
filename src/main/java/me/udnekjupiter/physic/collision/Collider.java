package me.udnekjupiter.physic.collision;

import me.udnekjupiter.physic.object.CollidablePhysicObject3d;

import java.util.ArrayList;
import java.util.List;

public abstract class Collider {
    protected CollidablePhysicObject3d parent;
    public List<Collider> currentCollisions = new ArrayList<>();
    public abstract boolean isCollidingWith(Collider collider);
    public boolean collidingObjectIsAlreadyListed(CollidablePhysicObject3d object){
        return currentCollisions.contains(object.getCollider());
    }
    public void addCollision(Collider collider){
        currentCollisions.add(collider);
    }
    public List<Collider> getCurrentCollisions(){
        return currentCollisions;
    }

}