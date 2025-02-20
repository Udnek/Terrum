package me.udnekjupiter.physic.collision;

import me.udnekjupiter.physic.object.CollidablePhysicObject3d;
import me.udnekjupiter.util.vector.Vector3d;
import org.jetbrains.annotations.NotNull;

public final class AABoxCollider extends Collider{
    public Vector3d minCorner;
    public Vector3d maxCorner;
    public Vector3d dimensions;

    public AABoxCollider()
    {
        this.dimensions = new Vector3d(1, 1, 1);
    }
    public AABoxCollider(Vector3d dimensions, CollidablePhysicObject3d parent)
    {
        this.dimensions = dimensions;
        this.parent = parent;
        updateCorners();
    }

    @NotNull
    public Vector3d getMinCorner(){
        return minCorner;
    }
    @NotNull
    public Vector3d getMaxCorner(){
        return maxCorner;
    }
    @NotNull
    public Vector3d getDimensions(){
        return dimensions;
    }

    @NotNull
    public Vector3d getPosition(){
        return parent.getPosition();
    }

    public void setDimensions(Vector3d newSize){
        this.dimensions = newSize;
        updateCorners();
    }

    public void updateCorners(){
        this.minCorner = new Vector3d(
                getPosition().x - (dimensions.x/2),
                getPosition().y - (dimensions.y/2),
                getPosition().z - (dimensions.z/2)
        );
        this.maxCorner = new Vector3d(
                getPosition().x + (dimensions.x/2),
                getPosition().y + (dimensions.y/2),
                getPosition().z + (dimensions.z/2)
        );
    }

    public boolean containsPoint(Vector3d point){
        return ((minCorner.x <= point.x && minCorner.y <= point.y && minCorner.z <= point.z)
                && (maxCorner.x >= point.x && maxCorner.y >= point.y && maxCorner.z >= point.z));
    }

    @Override
    public boolean collidesWith(Collider collider) {
        return switch (collider) {
            case AABoxCollider boxCollider -> CollisionDetection.boxAndBoxCollisionCheck(this, boxCollider);
            case SphereCollider sphereCollider -> CollisionDetection.boxAndSphereCollisionCheck(this, sphereCollider);
            case PlaneCollider planeCollider -> CollisionDetection.boxAndPlaneCollisionCheck(this, planeCollider);
            default -> throw new IllegalStateException("Unexpected collider type: " + collider);
        };
    }
}
