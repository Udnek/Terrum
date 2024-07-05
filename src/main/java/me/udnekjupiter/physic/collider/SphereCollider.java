package me.udnekjupiter.physic.collider;

import me.udnekjupiter.physic.object.RKMObject;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class SphereCollider extends Collider{
    private final double radius;
    public SphereCollider(double radius)
    {
        this.radius = radius;
    }

//    public Vector3d getCollisionForce(Collider object){
//
//    }

    @Override
    public List<RKMObject> findIntersections(List<RKMObject> objects) {
        return null;
    }
}
