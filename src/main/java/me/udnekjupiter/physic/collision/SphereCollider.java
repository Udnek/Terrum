package me.udnekjupiter.physic.collision;

import me.udnekjupiter.physic.object.RKMObject;
import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class SphereCollider extends Collider{
    public final double radius;
    public final double stiffness;

    public SphereCollider(double radius, double stiffness, RKMObject parent) {
        this.radius = radius;
        this.stiffness = stiffness;
        this.parent = parent;
    }

    public Vector3d getCenterPosition(){
        return parent.getCurrentRKMPosition();
    }

    @Override
    public boolean isCollidingWith(Collider collider) {
        if (collider instanceof SphereCollider sphereCollider){
            double distanceBetweenColliders = VectorUtils.distance(this.getCenterPosition(), sphereCollider.getCenterPosition());
            return (sphereCollider.radius + this.radius >= distanceBetweenColliders);
        } else {
            System.out.println("BoxCollider intersection checker is not ready");
            return false;
        }
    }
}
