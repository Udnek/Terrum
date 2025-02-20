package me.udnekjupiter.physic.collision.shape;

import me.udnekjupiter.physic.collision.Collider;
import me.udnekjupiter.physic.collision.ResponseOnly;
import me.udnekjupiter.physic.object.PlaneObject;
import me.udnekjupiter.util.VectorUtils;

public class PlaneCollider extends Collider implements ResponseOnly {
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
    public boolean collidingWith(Collider collider) {
        if (collider instanceof  SphereCollider sphereCollider) {
            double distanceToPlane = VectorUtils.distanceFromPointToPlane(a, b, c, d, sphereCollider.getCenterPosition().x, sphereCollider.getCenterPosition().y, sphereCollider.getCenterPosition().z);
            return (sphereCollider.radius >= distanceToPlane);
        } else {
            return false;
        }
    }
}
