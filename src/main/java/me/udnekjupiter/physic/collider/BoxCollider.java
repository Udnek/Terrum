package me.udnekjupiter.physic.collider;

import me.udnekjupiter.physic.object.RKMObject;
import org.realityforge.vecmath.Vector3d;

import java.util.List;

public class BoxCollider extends Collider {
    private double width;
    private double height;
    private double length;
    private Collidable parent;
    private Vector3d center;

    public BoxCollider(double width, double height, double length, Collidable parent)
    {
        this.width = width;
        this.height = height;
        this.length = length;
        this.parent = parent;
        this.center = parent.getPosition();
    }

    @Override
    public List<RKMObject> findIntersections(List<RKMObject> objects) {
        return null;
    }

//    public checkIntersection(Vector3d point){
//
//    }
}
