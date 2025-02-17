package me.udnekjupiter.physic.collision;

import me.udnekjupiter.physic.object.CollidablePhysicObject3d;
import me.udnekjupiter.util.VectorUtils;
import me.udnekjupiter.util.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class SphereCollider extends Collider{
    public final double radius;
    public final double stiffness;
    public List<CollidablePhysicObject3d> currentCollisions = new ArrayList<>();

    public SphereCollider(double radius, double stiffness, CollidablePhysicObject3d parent) {
        this.radius = radius;
        this.stiffness = stiffness;
        this.parent = parent;
    }

    public Vector3d getCenterPosition(){
        return parent.getPosition();
    }

    @Override
    public boolean isCollidingWith(Collider collider) {
        if (collider instanceof SphereCollider sphereCollider){
            double distanceBetweenColliders = VectorUtils.distance(this.getCenterPosition(), sphereCollider.getCenterPosition());
            return (sphereCollider.radius + this.radius >= distanceBetweenColliders);
        } else if (collider instanceof  PlaneCollider planeCollider) {
            double distanceToPlane = Math.abs(
                    (planeCollider.a*getCenterPosition().x) +
                    (planeCollider.b*getCenterPosition().y) +
                    (planeCollider.c*getCenterPosition().z) +
                    (planeCollider.d))
                    /
                    Math.sqrt((planeCollider.a*planeCollider.a) +
                            (planeCollider.b*planeCollider.b) +
                            (planeCollider.c*planeCollider.c));
            return (this.radius >= distanceToPlane);
        } else {
            return false;
        }
    }
}
