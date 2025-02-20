package me.udnekjupiter.physic.collision;

import me.udnekjupiter.physic.object.CollidablePhysicObject3d;
import me.udnekjupiter.util.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;

public final class SphereCollider extends Collider {
    public final double radius;
    public final double stiffness;
    public final double maxDepth;
    public List<CollidablePhysicObject3d> currentCollisions = new ArrayList<>();

    public SphereCollider(double radius, double stiffness, CollidablePhysicObject3d parent) {
        this.radius = radius;
        this.stiffness = stiffness;
        this.parent = parent;
        this.maxDepth = Math.log10(radius + 1) - 1;
    }

    public Vector3d getCenterPosition(){
        return parent.getPosition();
    }

    @Override
    public boolean collidesWith(Collider collider) {
        return switch (collider) {
            case AABoxCollider boxCollider -> CollisionDetection.boxAndSphereCollisionCheck(boxCollider, this);
            case SphereCollider sphereCollider -> CollisionDetection.sphereAndSphereCollisionCheck(this, sphereCollider);
            case PlaneCollider planeCollider -> CollisionDetection.sphereAndPlaneCollisionCheck(this, planeCollider);
            default -> throw new IllegalStateException("Unexpected collider type: " + collider);
        };
    }
}
