package me.udnekjupiter.physic.collision;

import me.udnekjupiter.util.vector.VectorUtils;

public abstract class CollisionDetection {
    public static boolean boxAndBoxCollisionCheck(AABoxCollider box1, AABoxCollider box2){
        return ((box1.maxCorner.x >= box2.minCorner.x) &&
                (box1.maxCorner.y >= box2.minCorner.y) &&
                (box1.maxCorner.z >= box2.minCorner.z) &&
                (box1.minCorner.x <= box2.maxCorner.x) &&
                (box1.minCorner.y <= box2.maxCorner.y) &&
                (box1.minCorner.z <= box2.maxCorner.z));

    }
    public static boolean boxAndSphereCollisionCheck(AABoxCollider box, SphereCollider sphere){
        return box.containsPoint(
                sphere.getCenterPosition().add(
                        sphere.getCenterPosition().getNormalizedDirection(box.getPosition()).mul(sphere.radius)
                )
        );
    }
    public static boolean boxAndPlaneCollisionCheck(AABoxCollider box, PlaneCollider plane){
        return false;
    }

    public static boolean sphereAndSphereCollisionCheck(SphereCollider sphere1, SphereCollider sphere2){
        double distanceBetweenColliders = VectorUtils.distance(sphere1.getCenterPosition(), sphere2.getCenterPosition());
        return (sphere2.radius + sphere1.radius >= distanceBetweenColliders);
    }
    public static boolean sphereAndPlaneCollisionCheck(SphereCollider sphere, PlaneCollider plane){
        double distanceToPlane = Math.abs(
                plane.a*sphere.getCenterPosition().x +
                plane.b*sphere.getCenterPosition().y +
                plane.c*sphere.getCenterPosition().z +
                plane.d) / Math.sqrt((plane.a*plane.a) + (plane.b*plane.b) + (plane.c*plane.c));
        return (sphere.radius >= distanceToPlane);
    }

    public static boolean planeAndPlaneCollisionCheck(PlaneCollider plane1, PlaneCollider plane2){
        return false;
    }



}
