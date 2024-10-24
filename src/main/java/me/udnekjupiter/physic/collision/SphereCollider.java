package me.udnekjupiter.physic.collision;

import me.udnekjupiter.physic.object.CollidablePhysicObject3d;
import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

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
        return parent.getActualPosition();
    }

    @Override
    public boolean isCollidingWith(Collider collider) {
        if (collider instanceof SphereCollider sphereCollider){
            double distanceBetweenColliders = VectorUtils.distance(this.getCenterPosition(), sphereCollider.getCenterPosition());
            return (sphereCollider.radius + this.radius >= distanceBetweenColliders);
        } else {
            System.out.println("BoxCollider intersection checker is not implemented yet");
            return false;
        }
    }
}
