package me.udnekjupiter.physic.collider;

import me.udnekjupiter.physic.object.RKMObject;
import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.List;

public class SphereCollider extends Collider{
    private final double radius;
    public SphereCollider(double radius, RKMObject parent)
    {
        this.radius = radius;
        this.parent = parent;
    }

    public double getRadius(){
        return this.radius;
    }
    public Vector3d getCenterPosition(){
        return parent.getCurrentRKMPosition();
    }

    @Override
    public boolean isCollidingWith(Collider collider) {
        if (collider instanceof SphereCollider sphereCollider){
            double distanceBetweenColliders = VectorUtils.distance(this.getCenterPosition(), sphereCollider.getCenterPosition());
            return (sphereCollider.getRadius() + this.getRadius() >= distanceBetweenColliders);
        } else {
            System.out.println("BoxCollider intersection checker is not ready");
            return false;
        }
    }
}
