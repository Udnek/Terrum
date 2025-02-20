package me.udnekjupiter.physic.collision;

import me.udnekjupiter.physic.object.PlaneObject;

public final class PlaneCollider extends Collider implements ResponseOnly {
    public final double a;
    public final double b;
    public final double c;
    public final double d;
    public final double stiffness;

    public PlaneCollider(double stiffness, PlaneObject parent)
    {
        this.a = parent.a;
        this.b = parent.b;
        this.c = parent.c;
        this.d = parent.d;
        this.stiffness = stiffness;

    }
    @Override
    public boolean collidesWith(Collider collider) {
        return switch (collider) {
            case AABoxCollider boxCollider -> CollisionDetection.boxAndPlaneCollisionCheck(boxCollider, this);
            case SphereCollider sphereCollider -> CollisionDetection.sphereAndPlaneCollisionCheck(sphereCollider, this);
            case PlaneCollider planeCollider -> CollisionDetection.planeAndPlaneCollisionCheck(this, planeCollider);
            default -> throw new IllegalStateException("Unexpected collider type: " + collider);
        };
    }
}
